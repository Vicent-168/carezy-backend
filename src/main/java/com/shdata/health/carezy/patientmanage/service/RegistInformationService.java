package com.shdata.health.carezy.patientmanage.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.common.utils.DataUtil;
import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.carezy.forms.entity.FormsDetail;
import com.shdata.health.carezy.forms.entity.FormsIndex;
import com.shdata.health.carezy.forms.mapper.DicHljlwsMapper;
import com.shdata.health.carezy.forms.mapper.FormsDetailMapper;
import com.shdata.health.carezy.forms.mapper.FormsIndexMapper;
import com.shdata.health.carezy.forms.service.FormsIndexService;
import com.shdata.health.carezy.forms.service.FormsService;
import com.shdata.health.carezy.forms.vo.*;
import com.shdata.health.carezy.patientmanage.entity.RegistInformation;
import com.shdata.health.carezy.patientmanage.entity.Task;
import com.shdata.health.carezy.patientmanage.mapper.RegistInformationMapper;
import com.shdata.health.carezy.patientmanage.vo.*;
import com.shdata.health.carezy.whiteBoard.entity.PatientRegistrationInformation;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import com.shdata.health.common.mybatis.NameUtil;
import com.shdata.health.common.utils.StringUtils;
import com.shdata.health.his.entity.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 病人登记信息  Service服务
 *
 * @author panwei
 * @date 2024-10-21
 */
@Service
@Transactional
@Slf4j
public class RegistInformationService extends BaseService<RegistInformationMapper, RegistInformation> {

    @Autowired
    private FormsIndexService formsIndexService;
    @Autowired
    private DicHljlwsMapper dictHljlwsMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormsService formsService;
    @Autowired
    private DicHljlwsMapper dicHljlwsMapper;
    @Qualifier("formsDetailMapper")
    @Autowired
    private FormsDetailMapper formsDetailMapper;
    @Autowired
    private FormsIndexMapper formsIndexMapper;


    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(RegistInformationVo vo) {
        validate(vo);
        RegistInformation po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { // 新增
            po = BeanUtil.toBean(vo, RegistInformation.class);
            po.setId(IdUtil.objectId());
            po.init();
            save(po);
            return "保存成功";
        } else {
            BeanUtil.copyProperties(vo, po);
            po.initByUpdate();
            updateById(po);
            return "更新成功";
        }
    }

    /**
     * 验证对象
     *
     * @param vo 提交参数
     */
    private void validate(RegistInformationVo vo) {
        if (vo == null) {
            throw new ParameterException("参数不能为空");
        }

    }

    // 分页方法
    private <E> IPage<E> pageConvert(List<E> res, Long current, Long size, Long total) {
        // 获取所有DoctorCountVo对象

        int c = current.intValue();
        int s = size.intValue();
        // 创建Page对象
        Page<E> page = new Page<>(current, size);

        // 设置总记录数
        page.setTotal(total);

        // 计算总页数
        page.setPages((int) Math.ceil((double) page.getTotal() / page.getSize()));

//        // 计算当前页的起始索引
        int start = (c - 1) * s;

//        // 截取当前页的数据
        List<E> pageRecords = res.subList(start, Math.min(start + s, res.size()));

        // 设置分页结果
        page.setRecords(pageRecords);

        return page;
    }

    /**
     * 查询患者信息（带分页）
     *
     * @param search 分页查询对象
     * @return
     */
    public PageData<RegistInformationListVo> findByPage(RegistInformationSearchVo search) {
        search.setYljgid(UserUtils.getOrganId());
        if (search.getWards() == null || StrUtil.isBlank(search.getWards())) {
            List<String> list = UserUtils.getLoginWards().stream().map(Dept::getId).toList();
            search.setBqList(list);
        }
        if ((search.getWards() == null || search.getWards().isEmpty()) && search.getBqList().isEmpty()){
            throw new ParameterException("未配置病区");
        }
        RegistInformationSearchVo all = new RegistInformationSearchVo();
        BeanUtil.copyProperties(search, all);
        all.setPageSize(Integer.MAX_VALUE);
        all.setCurrent(1);
        IPage<RegistInformationListWithNoneConertVo> byPage;
        if ("00".equals(search.getPatientStatus())) {
            all.setPatientStatus(null);
        }
        if (search.getFlag().equals("0")) {
            byPage = baseMapper.findByPage0(all);
        } else if (search.getFlag().equals("1")) {
            byPage = baseMapper.findByPage1(all);
        } else {
            throw new ParameterException("参数错误");
        }
        List<RegistInformationListWithNoneConertVo> records = byPage.getRecords();
        records.sort((o1, o2) -> {
            // 判断是否为"+数字"的格式
            boolean isPlusNumber1 = (o1.getBch().startsWith("+") || o1.getBch().startsWith("加")) && o1.getBch().length() > 1 && Character.isDigit(o1.getBch().charAt(1));
            boolean isPlusNumber2 = (o2.getBch().startsWith("+") || o2.getBch().startsWith("加")) && o2.getBch().length() > 1 && Character.isDigit(o2.getBch().charAt(1));

            // 如果两个字符串都是"+数字"的格式，则按数字排序
            if (isPlusNumber1 && isPlusNumber2) {
                return Integer.compare(Integer.parseInt(o1.getBch().substring(1)), Integer.parseInt(o2.getBch().substring(1)));
            }

            // 如果两个字符串都不是"+数字"的格式，则按整数排序
            if (!isPlusNumber1 && !isPlusNumber2) {
                //如果能转换为整数，则按整数排序，如果不能则跳过
                try {
                    return Integer.compare(Integer.parseInt(o1.getBch()), Integer.parseInt(o2.getBch()));
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            // 如果o1是"+数字"的格式，而o2不是，则o1应该排在o2前面
            if (isPlusNumber1) {
                return -1;
            }

            // 如果o2是"+数字"的格式，而o1不是，则o1应该排在o2后面
            return 1;
        });

        List<RegistInformationListVo> registInformationListVos = BeanUtil.copyToList(records, RegistInformationListVo.class);
        IPage<RegistInformationListVo> registInformationListVoIPage = pageConvert(registInformationListVos, search.getCurrent(), search.getPageSize(), byPage.getTotal());
        List<RegistInformationListVo> list = registInformationListVoIPage.getRecords();
        List<RegistInformationListVo> list1 = list.stream().peek(item -> {
            item.setRyts(item.getRyts());
            item.setRws(item.getRws());
//            item.setUpdateTimestamp(item.getUpdateTime().getTime());
        }).toList();
        List<RegistInformationListVo> peek = list1.stream().peek(NameUtil::convert).toList();

        registInformationListVoIPage.setRecords(peek);
        return PageData.of(registInformationListVoIPage);
    }


    public RegistInformationVo getInfoByBrid(String brid) {
        RegistInformationVo infoByBrid = baseMapper.getInfoByBrid(brid);
        infoByBrid.setUpdateTimestamp(infoByBrid.getUpdateTime().getTime());
        return infoByBrid;
    }

    public String updatePatientStatus(RegistInformationSearchVo vo) {
        RegistInformationVo po = getInfoByBrid(vo.getBrid());
        if (vo.getUpdateTimestamp() == po.getUpdateTime().getTime()) {// 乐观锁逻辑，更新密钥相同，则继续操作
            Integer i = baseMapper.updatePatientStatus(vo);
            return i > 0 ? "更新成功" : "更新失败";
        } else {// 更新密钥失效，则抛出异常，事务中断掉当前更新操作，一次就够了
            throw new ParameterException("更新失败");
        }

    }

    /**
     * 批量查询患者的生命体征
     */
    /* public List<PatientVitalSignInfosVo> batchRetrievePatientVitalSignsss(VitalSignSearchFiltersVo vo) {
        List<PatientVitalSignInfosVo> results = new ArrayList<>();
        String userId = UserUtils.getUserId();
        String yljgid = UserUtils.getOrganId();
        Date sj = vo.getSj();
        vo.setHsid(userId);
        vo.setYljgid(yljgid);
        if ("00".equals(vo.getHzzt())) {
            vo.setHzzt(null);
        }
        String wsbm = Constants.TZJLD_WSBM;
        // 获取所有的在院患者信息
        List<RegistInformationVo> registInformationVos = this.baseMapper.listRetrievePatients(vo);
        if (CollectionUtil.isNotEmpty(registInformationVos)) {
            for (RegistInformationVo registInformationVo : registInformationVos) {
                PatientVitalSignInfosVo patientVitalSignInfosVo = new PatientVitalSignInfosVo();
                String brid = registInformationVo.getId();
                //给患者的基本信息字段赋值
                patientVitalSignInfosVo.setId(registInformationVo.getId());
                patientVitalSignInfosVo.setBch(registInformationVo.getBch());
                patientVitalSignInfosVo.setXm(registInformationVo.getXm());
                patientVitalSignInfosVo.setZyh(registInformationVo.getZyh());
                patientVitalSignInfosVo.setNl(registInformationVo.getNl());
                patientVitalSignInfosVo.setRysj(registInformationVo.getRysj());
                patientVitalSignInfosVo.setRyts(registInformationVo.getRyts());
                patientVitalSignInfosVo.setXb(registInformationVo.getXb());
                patientVitalSignInfosVo.setYsid(registInformationVo.getYsid());
                patientVitalSignInfosVo.setYsxm(registInformationVo.getYsxm());
                patientVitalSignInfosVo.setXM_TW(null);
                patientVitalSignInfosVo.setXM_MB(null);
                patientVitalSignInfosVo.setXM_XL(null);
                patientVitalSignInfosVo.setXM_HX(null);
                patientVitalSignInfosVo.setXM_ZYZXYANG(null);
                patientVitalSignInfosVo.setXM_WCSSY(null);
                patientVitalSignInfosVo.setXM_WCSZY(null);
                // 获取病人所有体征索引表及对应的项目明细表
                List<FormsSaveVo> formsSaveVos = formsIndexService.getItemDetails(brid, null, sj, wsbm, null);
                boolean recordFound = false;
                if (CollectionUtil.isNotEmpty(formsSaveVos)) {
                    for (FormsSaveVo formsSaveVo : formsSaveVos) {
                        if (formsSaveVo != null) {
                            FormsIndexVo recordIndex = formsSaveVo.getRecordIndex();
                            Map<String, Object> detailMap = formsSaveVo.getDetailMap();
                            // 直接填充体征信息
                            patientVitalSignInfosVo.setXM_TW((String) detailMap.get("XM_TW"));
                            patientVitalSignInfosVo.setXM_MB((String) detailMap.get("XM_MB"));
                            patientVitalSignInfosVo.setXM_XL((String) detailMap.get("XM_XL"));
                            patientVitalSignInfosVo.setXM_HX((String) detailMap.get("XM_HX"));
                            patientVitalSignInfosVo.setXM_ZYZXYANG((String) detailMap.get("XM_ZYZXYANG"));
                            patientVitalSignInfosVo.setXM_WCSSY((String) detailMap.get("XM_WCSSY"));
                            patientVitalSignInfosVo.setXM_WCSZY((String) detailMap.get("XM_WCSZY"));

                            patientVitalSignInfosVo.setRecordIndex(recordIndex);
                            patientVitalSignInfosVo.setDetailMap(detailMap);
                            // 添加到结果集
                            results.add(patientVitalSignInfosVo);
                            break;  // 如果有数据，跳出循环
                        }
                    }

                }
                // 如果没有匹配的体征记录，初始化默认值
                if (results.size() == 0 || !results.contains(patientVitalSignInfosVo)) {
                    patientVitalSignInfosVo.setRecordIndex(new FormsIndexVo());  // 初始化空的 recordIndex
                    patientVitalSignInfosVo.setDetailMap(new HashMap<>());        // 初始化空的 detailMap
                    results.add(patientVitalSignInfosVo);
                }
            }
        }
        return results;

    } */
    /**
     * 批量查询患者的生命体征
     */
    public List<PatientVitalSignInfosVo> batchRetrievePatientVitalSignsss(VitalSignSearchFiltersVo vo) {
        List<PatientVitalSignInfosVo> results = new ArrayList<>();
        String userId = UserUtils.getUserId();
        String yljgid = UserUtils.getOrganId();
        Date sj = vo.getSj();
        vo.setHsid(userId);
        vo.setYljgid(yljgid);
        if ("00".equals(vo.getHzzt())) {
            vo.setHzzt(null);
        }
        String wsbm = Constants.TZJLD_WSBM;
        // 获取所有的在院患者信息
        List<RegistInformationVo> registInformationVos = this.baseMapper.listRetrievePatients(vo);
        if (CollectionUtil.isNotEmpty(registInformationVos)) {
            Set<String> bridSet = registInformationVos.stream()
                    .map(RegistInformationVo::getId)
                    .collect(Collectors.toSet());
            // 批量查询 FormsSaveVo
            List<FormsSaveVoo> formsSaveVoos = formsIndexService.getItemDetailsByBridBatch(bridSet, sj, wsbm);
            Map<String, FormsSaveVoo> bridToFormsSaveVoMap = formsSaveVoos.parallelStream()
                    .collect(Collectors.toMap(
                            formsSaveVoo -> formsSaveVoo.getRecordIndex().getBrid() + "_" + formsSaveVoo.getRecordIndex().getRq(),
                            Function.identity(),
                            (existing, replacement) -> existing // 合并策略
                    ));
            for (RegistInformationVo registInformationVo : registInformationVos) {
                PatientVitalSignInfosVo patientVitalSignInfosVo = new PatientVitalSignInfosVo();
                String brid = registInformationVo.getId();
                //给患者的基本信息字段赋值
                patientVitalSignInfosVo.setId(registInformationVo.getId());
                patientVitalSignInfosVo.setBch(registInformationVo.getBch());
                patientVitalSignInfosVo.setXm(registInformationVo.getXm());
                patientVitalSignInfosVo.setZyh(registInformationVo.getZyh());
                patientVitalSignInfosVo.setNl(registInformationVo.getNl());
                patientVitalSignInfosVo.setRysj(registInformationVo.getRysj());
                patientVitalSignInfosVo.setRyts(registInformationVo.getRyts());
                patientVitalSignInfosVo.setXb(registInformationVo.getXb());
                patientVitalSignInfosVo.setYsid(registInformationVo.getYsid());
                patientVitalSignInfosVo.setYsxm(registInformationVo.getYsxm());
                patientVitalSignInfosVo.setXmTw(null);
                patientVitalSignInfosVo.setXmMb(null);
                patientVitalSignInfosVo.setXmXl(null);
                patientVitalSignInfosVo.setXmHx(null);
                patientVitalSignInfosVo.setXmyzxYang(null);
                patientVitalSignInfosVo.setXmWcssy(null);
                patientVitalSignInfosVo.setXmWcszy(null);
                // 查找该患者对应的体征记录
                FormsSaveVoo formsSaveVoo = bridToFormsSaveVoMap.get(brid+"_"+sj);
                if (formsSaveVoo != null) {
                    FormsIndexVoo recordIndex = formsSaveVoo.getRecordIndex();
                    Map<String, Object> detailMap = formsSaveVoo.getDetailMap();
                    // 直接填充体征信息
                    patientVitalSignInfosVo.setXmTw((String) detailMap.get("XM_TW"));
                    patientVitalSignInfosVo.setXmMb((String) detailMap.get("XM_MB"));
                    patientVitalSignInfosVo.setXmXl((String) detailMap.get("XM_XL"));
                    patientVitalSignInfosVo.setXmHx((String) detailMap.get("XM_HX"));
                    patientVitalSignInfosVo.setXmyzxYang((String) detailMap.get("XM_ZYZXYANG"));
                    patientVitalSignInfosVo.setXmWcssy((String) detailMap.get("XM_WCSSY"));
                    patientVitalSignInfosVo.setXmWcszy((String) detailMap.get("XM_WCSZY"));

                    patientVitalSignInfosVo.setRecordIndex(recordIndex);
                    patientVitalSignInfosVo.setDetailMap(detailMap);
                    // 添加到结果集中
                    results.add(patientVitalSignInfosVo);
                }else {
                    // 如果没有体征记录，则添加默认值
                    patientVitalSignInfosVo.setRecordIndex(new FormsIndexVoo());  // 初始化空的recordIndex
                    patientVitalSignInfosVo.setDetailMap(new HashMap<>());        // 初始化空的detailMap
                    results.add(patientVitalSignInfosVo);
                }
            }
        }
        return results;

    }

    public List<PatientBloodGlucoseDatasVo> batchBloodGlucoseQuerys(PatientBloodGlucoseBatchQueryParamsVo vo) {
        List<PatientBloodGlucoseDatasVo> results = new ArrayList<>();
        String userId = UserUtils.getUserId();
        String yljgid = UserUtils.getOrganId();
        Date sj = vo.getSj();
        // String XM_BB=vo.getXM_BB();
        // 测量时段
        // String XM_CLSD=vo.getXM_CLSD();
        vo.setHsid(userId);
        vo.setYljgid(yljgid);
        // 如果 hzzt 值为 "00"，清空 hzzt 以查询所有状态
        if ("00".equals(vo.getHzzt())) {
            vo.setHzzt(null);
        }
        String wsbm = Constants.XTJCBD_WSBM;
        VitalSignSearchFiltersVo vitalSignSearchFiltersVo = new VitalSignSearchFiltersVo();
        BeanUtils.copyProperties(vo, vitalSignSearchFiltersVo);
        // 获取所有的在院患者信息
        List<RegistInformationVo> registInformationVos = this.baseMapper.listRetrievePatients(vitalSignSearchFiltersVo);
        if (CollectionUtil.isNotEmpty(registInformationVos)) {
            Set<String> bridSet = registInformationVos.stream()
                    .map(RegistInformationVo::getId)
                    .collect(Collectors.toSet());
            // 批量查询 FormsSaveVo
            List<FormsSaveVoo> formsSaveVoos = formsIndexService.getItemDetailsByBridBatch(bridSet, sj, wsbm);
            Map<String, FormsSaveVoo> bridToFormsSaveVoMap = formsSaveVoos.parallelStream()
                    .collect(Collectors.toMap(
                            formsSaveVoo -> formsSaveVoo.getRecordIndex().getBrid() + "_" + formsSaveVoo.getRecordIndex().getRq(),
                            Function.identity(),
                            (existing, replacement) -> existing // 合并策略
                    ));

            for (RegistInformationVo registInformationVo : registInformationVos) {
                PatientBloodGlucoseDatasVo patientBloodGlucoseDatasVo = new PatientBloodGlucoseDatasVo();
                String brid = registInformationVo.getId();
                // 给患者的基本信息字段赋值
                patientBloodGlucoseDatasVo.setId(brid);
                patientBloodGlucoseDatasVo.setBch(registInformationVo.getBch());
                patientBloodGlucoseDatasVo.setXm(registInformationVo.getXm());
                patientBloodGlucoseDatasVo.setZyh(registInformationVo.getZyh());
                patientBloodGlucoseDatasVo.setNl(registInformationVo.getNl());
                patientBloodGlucoseDatasVo.setRysj(registInformationVo.getRysj());
                patientBloodGlucoseDatasVo.setRyts(registInformationVo.getRyts());
                patientBloodGlucoseDatasVo.setXb(registInformationVo.getXb());
                patientBloodGlucoseDatasVo.setYsid(registInformationVo.getYsid());
                patientBloodGlucoseDatasVo.setYsxm(registInformationVo.getYsxm());
                patientBloodGlucoseDatasVo.setXM_CLZ(null);
                // 查找该患者对应的体征记录
                FormsSaveVoo formsSaveVoo = bridToFormsSaveVoMap.get(brid + "_" + sj);
                if (formsSaveVoo != null) {
                    patientBloodGlucoseDatasVo.setRecordIndex(formsSaveVoo.getRecordIndex());
                    patientBloodGlucoseDatasVo.setDetailMap(formsSaveVoo.getDetailMap());
                } else {
                    patientBloodGlucoseDatasVo.setRecordIndex(new FormsIndexVoo()); // 初始化空的 recordIndex
                    patientBloodGlucoseDatasVo.setDetailMap(new HashMap<>()); // 初始化空的 detailMap
                }
                results.add(patientBloodGlucoseDatasVo);
            }
        }
        return results;
    }





    /**
     * 批量保存患者的生命体征数据
     */
    @Transactional
    public FormsSaveTipsVo batchSavePatientVitalSigns(VitalSignsVo vo) {
        Date xmClsj = vo.getXmClsj();
        String xmSjjd = vo.getXmSjjd();
        String yljgid = UserUtils.getOrganId();
        String ksid =  vo.getBqid();
        if (StringUtils.isEmpty(ksid)) {
            throw new RuntimeException("请选择病区");
        }
        String xmCltwbw=vo.getXmCltwbw();
        /** 文书分类代码 */
        String fldm = null;
        /**文书编码**/
        String wsid = null;
        /**文书编码**/
        String wsbm = Constants.TZJLD_WSBM;
        WsbmSearchVo wsbmSearchVo = new WsbmSearchVo();
        wsbmSearchVo.setWsbm(wsbm);
        wsbmSearchVo.setYljgid(UserUtils.getOrganId());
        List<DicHljlwsVo> dicHljlwsVos = formsService.getWs(wsbmSearchVo);
        if (CollectionUtil.isNotEmpty(dicHljlwsVos)) {
            for (DicHljlwsVo dicHljlwsVo : dicHljlwsVos) {
                if (dicHljlwsVo == null) {
                    continue;
                }

                fldm = dicHljlwsVo.getFldm();

                wsid = dicHljlwsVo.getWsid();
            }
        }
        List<PatientVitalSignInfosVo> patientVitalSignInfosVos = vo.getPatientVitalSignInfosVos();
        if (CollectionUtil.isNotEmpty(patientVitalSignInfosVos)) {
            for (PatientVitalSignInfosVo patientVitalSignInfosVo : patientVitalSignInfosVos) {
                //RegistInformationVo registInformationVo = patientVitalSignInfosVo.getRegistInformationVo();
                FormsIndexVoo recordIndex = patientVitalSignInfosVo.getRecordIndex();
                recordIndex.setRq(xmClsj);
                recordIndex.setBrid(patientVitalSignInfosVo.getId());
                recordIndex.setWsbm(wsbm);
                recordIndex.setWsid(wsid);
                recordIndex.setWsfl(fldm);
                recordIndex.setYljgid(yljgid);
                recordIndex.setKsid(ksid);
                Map<String, Object> detailMap = patientVitalSignInfosVo.getDetailMap();
                // 将上述的XM_SJJD字段封装到detailMap中
                detailMap.put("XM_TW", patientVitalSignInfosVo.getXmTw());
                detailMap.put("XM_MB", patientVitalSignInfosVo.getXmMb());
                detailMap.put("XM_CLSJ", TimeFormat(xmClsj));
                detailMap.put("XM_CLTWBW", xmCltwbw);
                detailMap.put("XM_XL",  patientVitalSignInfosVo.getXmXl());
                detailMap.put("XM_HX", patientVitalSignInfosVo.getXmHx());
                detailMap.put("XM_ZYZXYANG", patientVitalSignInfosVo.getXmyzxYang());
                detailMap.put("XM_WCSSY", patientVitalSignInfosVo.getXmWcssy());
                detailMap.put("XM_WCSZY", patientVitalSignInfosVo.getXmWcszy());
                detailMap.put("XM_SJJD", xmSjjd);
                FormsSaveVoo formsSaveVoo = new FormsSaveVoo();
                formsSaveVoo.setRecordIndex(recordIndex);
                formsSaveVoo.setDetailMap(detailMap);

                FormsSaveVo formsSaveVo = new FormsSaveVo();
                FormsIndexVo formsIndexVo = new FormsIndexVo();
                BeanUtil.copyProperties(recordIndex, formsIndexVo);
                formsSaveVo.setRecordIndex(formsIndexVo);
                formsSaveVo.setDetailMap(detailMap);
                formsService.formsSave(formsSaveVo);
            }
        }
        return null;

    }

    /**
     * 批量保存患者的血糖数据
     */
    @Transactional
    public FormsSaveTipsVo batchSaveBloodGlucoseQuery(BloodSugarMeasurementVo vo) {
        String xmSjjd = vo.getXmSjjd();
        /** 测量时间 */
        Date xmClsj = vo.getXmClsj();
        String xmBb = vo.getXmBb();
        /** 测量时段 */
        String xmClsd = vo.getXmClsd();
        String yljgid = UserUtils.getOrganId();
        String ksid =  vo.getBqid();
        if (StringUtils.isEmpty(ksid)) {
            throw new RuntimeException("请选择病区");
        }
        /** 文书分类代码 */
        String fldm = null;
        /**文书编码**/
        String wsid = null;
        String wsbm = Constants.XTJCBD_WSBM;
        WsbmSearchVo wsbmSearchVo = new WsbmSearchVo();
        wsbmSearchVo.setWsbm(wsbm);
        wsbmSearchVo.setYljgid(UserUtils.getOrganId());
        List<DicHljlwsVo> dicHljlwsVos = formsService.getWs(wsbmSearchVo);
        // 确保字典信息非空，并且获取字段信息
        if (CollectionUtil.isNotEmpty(dicHljlwsVos)) {
            DicHljlwsVo dicHljlwsVo = dicHljlwsVos.get(0);
            if (dicHljlwsVo != null) {
                fldm = dicHljlwsVo.getFldm();
                wsid = dicHljlwsVo.getWsid();
            }
        }
        List<BloodSugarRecordVo> bloodSugarRecordVos = vo.getBloodSugarRecordVos();
        if (CollectionUtil.isNotEmpty(bloodSugarRecordVos)) {
            for (BloodSugarRecordVo bloodSugarRecordVo : bloodSugarRecordVos) {
                if (bloodSugarRecordVo == null) {
                    continue;
                }
                //RegistInformationVo registInformationVo = patientVitalSignInfosVo.getRegistInformationVo();
                FormsIndexVoo recordIndex = bloodSugarRecordVo.getRecordIndex();
                Map<String, Object> detailMap = bloodSugarRecordVo.getDetailMap();
                //血糖索引表的测量时间字段维护
                recordIndex.setRq(xmClsj);
                recordIndex.setBrid(bloodSugarRecordVo.getId());
                recordIndex.setWsbm(wsbm);
                recordIndex.setWsid(wsid);
                recordIndex.setWsfl(fldm);
                recordIndex.setYljgid(yljgid);
                recordIndex.setKsid(ksid);
                // 处理血糖项目明细
                // if ("1".equals(XM_BB)) {
                //     // 血标本测量值
                //     detailMap.put("XM_CLZ", bloodSugarRecordVo.getXM_CLZ());
                //
                // } else if ("2".equals(XM_BB)) {
                //     // 尿标本测量值
                //     detailMap.put("XM_NCLZ", bloodSugarRecordVo.getXM_CLZ());
                // }
               /*  if (("1").equals(XM_BB)) {
                    // 血标本测量值
                    detailMap.put("XM_CLZ", bloodSugarRecordVo.getXM_CLZ());
                    detailMap.put("XM_BB", XM_BB);


                } else if ("2".equals(XM_BB)) {
                    // 尿标本测量值
                    detailMap.put("XM_NCLZ", bloodSugarRecordVo.getXM_CLZ());
                    detailMap.put("XM_NBB", XM_BB);
                } */
                detailMap.put("XM_CLZ", bloodSugarRecordVo.getXmClz());
                detailMap.put("XM_BB", xmBb);
                detailMap.put("XM_SJJD", xmSjjd);
                detailMap.put("XM_CLSD", xmClsd);
                detailMap.put("XM_CLSJ", TimeFormat(xmClsj));
                FormsSaveVo formsSaveVo = new FormsSaveVo();
                FormsIndexVo formsIndexVo = new FormsIndexVo();
                BeanUtil.copyProperties(recordIndex, formsIndexVo);
                formsSaveVo.setRecordIndex(formsIndexVo);
                formsSaveVo.setDetailMap(detailMap);
                formsService.formsSave(formsSaveVo);
            }
        }
        // 返回提示信息对象
        FormsSaveTipsVo formsSaveTipsVo = new FormsSaveTipsVo();
        formsSaveTipsVo.setMsg("保存成功");
        return formsSaveTipsVo;
    }

    /**
     * 时间格式映射
     * @param
     */
    public String TimeFormat(Date originalDate) {
        // 定义目标日期格式，只保留年月日
        SimpleDateFormat targetDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        targetDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00")); // 设置中国标准时间（CST）

        // 格式化原始日期为目标格式
        String formattedDateString = targetDateFormat.format(originalDate);

        return formattedDateString;
    }


    /**
     * 护理告知列表
     */
    public PageData<NursingNoticeVo> CareNotificationList(NursingNoticeSearchVo vo) {
        vo.setYljgid(UserUtils.getOrganId());
        // 如果 hzzt 值为 "00"，清空 hzzt 以查询所有状态
        if ("00".equals(vo.getHzzt())) {
            vo.setHzzt(null);
        }
        List<Dept> loginWards = UserUtils.getLoginWards();
        //获取loginWards中的主键ids
        List<String> bqList = loginWards.stream().map(Dept::getId).collect(Collectors.toList());
        vo.setBqList(bqList);
        IPage<NursingNoticeVo> iPage = this.baseMapper.CareNotificationList(vo);
        if (CollectionUtil.isNotEmpty(iPage.getRecords())) {
            for (NursingNoticeVo record : iPage.getRecords()) {
                record.getRyts();
                //添加文书分类对护理告知业务数据进行过滤
                List<FormsSaveVo> formsSaveVoS = formsIndexService.getItemDetails(record.getId(), "", null, "", Constants.RYGZ_WSFL);
                //List<FormsSaveVo> formsSaveVoS = formsIndexService.getItemDetails(record.getId(), "", null,"", "");
                if (CollectionUtil.isNotEmpty(formsSaveVoS)) {
                    record.setHlgzvos(buildHlgz(formsSaveVoS));
                }
            }
        }

        return PageData.of(iPage);
    }

    private List<HlgzVo> buildHlgz(List<FormsSaveVo> formsSaveVoS) {
        String yljgid = UserUtils.getOrganId();
        List<HlgzVo> hlgzVos = new ArrayList<>();
        for (FormsSaveVo formsSaveVo : formsSaveVoS) {
            FormsIndexVo recordIndex = formsSaveVo.getRecordIndex();
            // 过滤出护理风险措施告知书和住院患者告知书
            if (recordIndex != null) {
                //if (recordIndex != null && recordIndex.getWsid() != null && (Constants.HLCSFXGZS_WSBM.equals(recordIndex.getWsbm()) || Constants.ZYHZGZS_WSBM.equals(recordIndex.getWsbm()))) {
                HlgzVo hlgzVo = new HlgzVo();
                String wsbm = recordIndex.getWsbm();
                String wsid = recordIndex.getWsid();
                String id = recordIndex.getId();
                String wsfl = recordIndex.getWsfl();
                String mc = dictHljlwsMapper.getMcBywsbm(wsbm,yljgid);
                String rq = new SimpleDateFormat("yyyy-MM-dd").format(recordIndex.getRq());
                hlgzVo.setRq(rq);
                hlgzVo.setId(id);
                hlgzVo.setWsfl(wsfl);
                hlgzVo.setWsbm(wsbm);
                hlgzVo.setWsid(wsid);
                hlgzVo.setHlgzmc(mc);
                hlgzVos.add(hlgzVo);
            }
        }

        return hlgzVos;
    }

    /**
     * 有创治疗列表
     */
    public PageData<InvasiveTreatmentVo> getInvasiveTreatmentList(InvasiveTreatmentSearchVo vo) {
        List<Dept> loginWards = UserUtils.getLoginWards();
        //获取loginWards中的主键ids
        List<String> bqList = loginWards.stream().map(Dept::getId).collect(Collectors.toList());
        vo.setBqList(bqList);
        vo.setYljgid(UserUtils.getOrganId());
        // 如果 hzzt 值为 "00"，清空 hzzt 以查询所有状态
        if ("00".equals(vo.getHzzt())) {
            vo.setHzzt(null);
        }
        IPage<InvasiveTreatmentVo> iPage = this.baseMapper.findInvasiveTreatmentList(vo);
        if (CollectionUtil.isNotEmpty(iPage.getRecords())) {
            for (InvasiveTreatmentVo record : iPage.getRecords()) {
                List<DqdgVo> dqdgVos = new ArrayList<>();
                List<LsdgVo> lsdgVos = new ArrayList<>();
                record.getRyts();
                //针对性的对改患者PICC索表及患者明细数据进行过滤
                List<FormsSaveVo> formsSaveVoS = formsIndexService.getItemDetails(record.getId(), "", null,"", Constants.YCZL_FLDM);
                // 如果 formsSaveVoS 为空，跳过该记录的导管信息处理
                if (CollectionUtil.isEmpty(formsSaveVoS)) {
                    continue;
                }
                for (FormsSaveVo formsSaveVo : formsSaveVoS) {
                    FormsIndexVo formsIndexVo = formsSaveVo.getRecordIndex();
                    DqdgVo dqdgVo = new DqdgVo();
                    LsdgVo lsdgVo = new LsdgVo();
                    if (formsIndexVo == null) {
                        continue; // 如果 formsIndexVo 为 null，跳过该记录
                    }
                    // 通过文书编码获取文书名称，即导管名称
                    //String dgmc = dictHljlwsMapper.getMcBywsbm(formsIndexVo.getWsbm());
                    // 判断 bgsj 是否为空，决定是当前导管还是历史导管
                    if (formsIndexVo.getBgsj() == null) {
                        // 当前导管
                        dqdgVo.setWsfl(formsIndexVo.getWsfl());
                        dqdgVo.setDglx(formsIndexVo.getDglx());
                        dqdgVo.setId(formsIndexVo.getId());
                        dqdgVo.setWsbm(formsIndexVo.getWsbm());
                        dqdgVo.setWsid(formsIndexVo.getWsid());
                        dqdgVo.setRq(formsIndexVo.getRq());
                        dqdgVos.add(dqdgVo);
                    } else {
                        // 历史导管
                        lsdgVo.setWsfl(formsIndexVo.getWsfl());
                        lsdgVo.setId(formsIndexVo.getId());
                        lsdgVo.setDglx(formsIndexVo.getDglx());
                        lsdgVo.setWsbm(formsIndexVo.getWsbm());
                        lsdgVo.setWsid(formsIndexVo.getWsid());
                        lsdgVo.setRq(formsIndexVo.getRq());
                        lsdgVos.add(lsdgVo);
                    }

                }
                // 设置当前导管和历史导管
                record.setDqdgvos(dqdgVos);
                record.setLsdgVos(lsdgVos);
            }

        }

        return PageData.of(iPage);
    }

    /**
     * 护理任务列表
     */
    public PageData<NursingTaskVo> getNursingTaskList(NursingTaskSearchVo vo) {
        String userId = UserUtils.getUserId();
        List<Dept> loginWards = UserUtils.getLoginWards();
        //获取loginWards中的主键ids
        List<String> bqList = loginWards.stream().map(Dept::getId).collect(Collectors.toList());
        vo.setBqList(bqList);
        vo.setYljgid(UserUtils.getOrganId());
        // 如果 hzzt 值为 "00"，清空 hzzt 以查询所有状态
        if ("00".equals(vo.getHzzt())) {
            vo.setHzzt(null);
        }
        IPage<NursingTaskVo> iPage = this.baseMapper.findNursingTaskList(vo);
        if (CollectionUtil.isNotEmpty(iPage.getRecords())) {
            for (NursingTaskVo record : iPage.getRecords()) {
                record.getRyts();
                // 获取当前患者的任务数
                List<TaskVo> taskVos = taskService.getTodoTaskByBrid(record.getId());
                record.setHlrws(String.valueOf(taskVos.size()));
                List<HlrwVo> hlrwVos = new ArrayList<>();
                // 初始化护理任务
                if (CollectionUtil.isNotEmpty(taskVos)) {
                    for (TaskVo taskVo : taskVos) {
                        HlrwVo hlrwVo = new HlrwVo();
                        hlrwVo.setId(taskVo.getId());
                        hlrwVo.setWsid(taskVo.getWsid());
                        // 检查 wsid 是否为空
                        String wsid = taskVo.getWsid();
                        if (wsid != null && !wsid.isEmpty()) {
                            DicHljlwsVo dicHljlwsVo = dictHljlwsMapper.getwsflBywsid(wsid);
                            hlrwVo.setWsfl(dicHljlwsVo.getFldm());
                            hlrwVo.setWsbm(dicHljlwsVo.getWsbm());
                        }
                        hlrwVo.setRwmc(taskVo.getRwmc());
                        hlrwVos.add(hlrwVo);
                    }
                }
                // 护理任务属性赋值
                record.setHlrwVos(hlrwVos);
            }
        }
        return PageData.of(iPage);

    }

    public List<Dept> findWardListByUserId() {
        return DataUtil.findWardListByUserId(UserUtils.getUserId());
    }

    public List<DicHljlwsVo> getWsList(String organId,String brid) {
        List<DicHljlwsVo> wsList = baseMapper.getWsList(organId);
        if (CollectionUtil.isNotEmpty(wsList)){
            List<String> ids = wsList.stream().map(DicHljlwsVo::getId).collect(Collectors.toList());
            List<FormsIndexVo> indexList = formsIndexMapper.getAllIndex(ids,brid,organId);
            for(DicHljlwsVo wsvo : wsList){
                for(FormsIndexVo index : indexList){
                    if(wsvo.getId().equals(index.getWsid())){
                        wsvo.setIsDo("1");
                        break;
                    }
                }
            }
        }
        // 先把fldm转为int类型，再根据fldm排序再根据wsbm排序，返回List<DicHljlwsVo>
        // 使用List.sort()方法和Comparator进行排序
        wsList.sort(Comparator.comparingInt(DicHljlwsVo::getFldmAsInt)
                .thenComparing(DicHljlwsVo::getWsbm));
        return wsList;
    }

    /**
     * 健康宣教查询
     */
    public HealthEducationVo getHealthEducationQuery(HealthEducationSearchVo vo) {
        HealthEducationVo healthEducationVo = new HealthEducationVo();
        String userId = UserUtils.getUserId();
        String yljgid = UserUtils.getOrganId();
        vo.setHsid(userId);
        vo.setYljgid(yljgid);
        List<InstructionHistoryVo> instructionHistoryList = new ArrayList<>();
        // 获取患者的基本信息
        PatientRegistrationInformation patientRegistrationInformation = this.baseMapper.getInfoByHealthEducationSearchVo(vo);
        healthEducationVo.setXm(patientRegistrationInformation.getXm());
        healthEducationVo.setGm(patientRegistrationInformation.getGm());
        healthEducationVo.setYzdid(patientRegistrationInformation.getZdid());
        String brid = patientRegistrationInformation.getId();
        // 获取患者所有的文书索引记录
        List<FormsSaveVo> formsSaveVoS = formsIndexService.getItemDetails(brid, "", null, "", "");
        boolean admissionEducationFound = false;
        boolean inpatientEducationFound = false;
        boolean hygieneEducationFound = false;
        boolean dischargeEducationFound = false;
        if (CollectionUtil.isNotEmpty(formsSaveVoS)) {
            for (FormsSaveVo formsSaveVo : formsSaveVoS) {


                FormsIndexVo formsIndexVo = formsSaveVo.getRecordIndex();
                Map<String, Object> detailMap = formsSaveVo.getDetailMap();
                // 如果 formsIndexVo 为空，跳过该条记录
                if (formsIndexVo == null) {
                    continue;
                }
                // 构建宣教历史记录
                InstructionHistoryVo instructionHistoryVo = new InstructionHistoryVo();
                List<MissionPropagatorVo> missionPropagatorVos = new ArrayList<>();
                MissionPropagatorVo missionPropagatorVo = new MissionPropagatorVo();
                // 如果文书编码不属于宣教记录，跳过该条记录
                if (!formsIndexVo.getWsbm().equals(Constants.RYXJ_WSBM) && !formsIndexVo.getWsbm().equals(Constants.ZYXJ_WSBM) && !formsIndexVo.getWsbm().equals(Constants.WSXJ_WSBM)) {
                    continue;
                }
                // 根据文书编码处理不同类型的宣教记录
                switch (formsIndexVo.getWsbm()) {
                    case Constants.RYXJ_WSBM:
                        // 入院宣教
                        PatientAdmissionGuidanceVo patientAdmissionGuidanceVo = new PatientAdmissionGuidanceVo();
                        fillPatientAdmissionGuidance(detailMap, patientAdmissionGuidanceVo);
                        healthEducationVo.setPatientAdmissionGuidanceVo(patientAdmissionGuidanceVo);
                        break;
                    case Constants.ZYXJ_WSBM:
                        // 住院宣教
                        InpatientEducationVo inpatientEducationVo = new InpatientEducationVo();
                        fillInpatientEducation(detailMap, inpatientEducationVo);
                        healthEducationVo.setInpatientEducationVo(inpatientEducationVo);
                        break;
                    case Constants.WSXJ_WSBM:
                        // 卫生宣教
                        HygieneEducationVo hygieneEducationVo = new HygieneEducationVo();
                        fillHygieneEducation(detailMap, hygieneEducationVo);
                        healthEducationVo.setHygieneEducationVo(hygieneEducationVo);
                        break;
                    case Constants.CYXJ_WSBM:
                        // 出院宣教
                        DischargeEducationVo dischargeEducationVo = new DischargeEducationVo();
                        fillDischargeEducation(detailMap, dischargeEducationVo);
                        healthEducationVo.setDischargeEducationVo(dischargeEducationVo);
                        break;
                    default:
                        break;
                }
                missionPropagatorVo.setWsid(formsIndexVo.getWsid());
                missionPropagatorVo.setWsmc(dictHljlwsMapper.getMcBywsbm(formsIndexVo.getWsbm(),yljgid));
                instructionHistoryVo.setRq(String.valueOf(formsIndexVo.getRq()));
                instructionHistoryVo.setHsid(userId);
                // 将该条记录的宣教项目添加到任务传播者列表
                missionPropagatorVos.add(missionPropagatorVo);
                instructionHistoryVo.setMissionPropagatorList(missionPropagatorVos);
                instructionHistoryVo.setHsid(userId); // 评估人
                instructionHistoryList.add(instructionHistoryVo);
            }
        }
        // 如果没有找到对应的宣教记录，初始化相关字段
        if (!admissionEducationFound) {
            healthEducationVo.setPatientAdmissionGuidanceVo(new PatientAdmissionGuidanceVo());
        }
        if (!inpatientEducationFound) {
            healthEducationVo.setInpatientEducationVo(new InpatientEducationVo());
        }
        if (!hygieneEducationFound) {
            healthEducationVo.setHygieneEducationVo(new HygieneEducationVo());
        }
        if (!dischargeEducationFound) {
            healthEducationVo.setDischargeEducationVo(new DischargeEducationVo());
        }
        healthEducationVo.setInstructionHistoryList(instructionHistoryList);
        return healthEducationVo;
    }

    // 填充入院宣教信息
    private void fillPatientAdmissionGuidance(Map<String, Object> detailMap, PatientAdmissionGuidanceVo vo) {
        vo.setXM_XJSJ(detailMap.get("XM_XJSJ").toString());
        vo.setXM_SJJD(detailMap.get("XM_XJJD").toString());
        vo.setXM_RQJD_XJFS(detailMap.get("XM_RQJD_XJFS").toString());
        vo.setXM_RQJD_XGPJ(detailMap.get("XM_RQJD_XGPJ").toString());
        vo.setXM_YYGZZD_XJFS(detailMap.get("XM_YYGZZD_XJFS").toString());
        vo.setXM_YYGZZD_XGPJ(detailMap.get("XM_YYGZZD_XGPJ").toString());
        vo.setXM_PKZD_XJFS(detailMap.get("XM_PKZD_XJFS").toString());
        vo.setXM_PKZD_XGPJ(detailMap.get("XM_PKZD_XGPJ").toString());
        vo.setXM_PQLXFS_XJFS(detailMap.get("XM_PQLXFS_XJFS").toString());
        vo.setXM_PQLXFS_XGPJ(detailMap.get("XM_PQLXFS_XGPJ").toString());
        vo.setXM_HSQZ(detailMap.get("XM_HSQZ").toString());
        vo.setXM_HZQZ(detailMap.get("XM_HZQZ").toString());
    }

    // 填充住院宣教信息
    private void fillInpatientEducation(Map<String, Object> detailMap, InpatientEducationVo vo) {
        vo.setXM_XJSJ(detailMap.get("XM_XJSJ").toString());
        vo.setXM_SJJD(detailMap.get("XM_XJJD").toString());
        vo.setXM_HSQZ(detailMap.get("XM_HSQZ").toString());
        vo.setXM_HZQZ(detailMap.get("XM_HZQZ").toString());
        vo.setXM_HLJHJL_XJFS(detailMap.get("XM_HLJHJL_XJFS").toString());
        vo.setXM_HLJHJL_XGPJ(detailMap.get("XM_HLJHJL_XGPJ").toString());
        vo.setXM_JBZL_XJFS(detailMap.get("XM_JBZL_XJFS").toString());
        vo.setXM_JBZL_XGPJ(detailMap.get("XM_JBZL_XGPJ").toString());
        vo.setXM_PKZD_XJFS(detailMap.get("XM_PKZD_XJFS").toString());
        vo.setXM_PKZD_XGPJ(detailMap.get("XM_PKZD_XGPJ").toString());
        vo.setXM_BQLXFS_XJFS(detailMap.get("XM_BQLXFS_XJFS").toString());
        vo.setXM_BQLXFS_XGPJ(detailMap.get("XM_BQLXFS_XGPJ").toString());
    }

    // 填充卫生宣教信息
    private void fillHygieneEducation(Map<String, Object> detailMap, HygieneEducationVo vo) {
        vo.setXM_XJSJ(detailMap.get("XM_XJSJ").toString());
        vo.setXM_SJJD(detailMap.get("XM_XJJD").toString());
        vo.setXM_HSQZ(detailMap.get("XM_HSQZ").toString());
        vo.setXM_HZQZ(detailMap.get("XM_HZQZ").toString());
        vo.setXM_XLWSZS_XJFS(detailMap.get("XM_XLWSZS_XJFS").toString());
        vo.setXM_XLWSZS_XGPJ(detailMap.get("XM_XLWSZS_XGPJ").toString());
        vo.setXM_JBBJZS_XJFS(detailMap.get("XM_JBBJZS_XJFS").toString());
        vo.setXM_JBBJZS_XGPJ(detailMap.get("XM_JBBJZS_XGPJ").toString());
        vo.setXM_YSFSMDZYSX_XJFS(detailMap.get("XM_YSFSMDZYSX_XJFS").toString());
        vo.setXM_YSFSMDZYSX_XGPJ(detailMap.get("XM_YSFSMDZYSX_XGPJ").toString());
        vo.setXM_GNDL_XJFS(detailMap.get("XM_GNDL_XJFS").toString());
        vo.setXM_GNDL_XGPJ(detailMap.get("XM_GNDL_XGPJ").toString());
    }

    // 填充出院宣教信息
    private void fillDischargeEducation(Map<String, Object> detailMap, DischargeEducationVo vo) {
        vo.setXM_XJSJ(detailMap.get("XM_XJSJ").toString());
        vo.setXM_SJJD(detailMap.get("XM_XJJD").toString());
        vo.setXM_HSQZ(detailMap.get("XM_HSQZ").toString());
        vo.setXM_HZQZ(detailMap.get("XM_HZQZ").toString());
        vo.setXM_JBBJZS_XJFS(detailMap.get("XM_JBBJZS_XJFS").toString());
        vo.setXM_JBBJZS_XGPJ(detailMap.get("XM_JBBJZS_XGPJ").toString());
        vo.setXM_HDXXKFZS_XJFS(detailMap.get("XM_HDXXKFZS_XJFS").toString());
        vo.setXM_HDXXKFZS_XGPJ(detailMap.get("XM_HDXXKFZS_XGPJ").toString());
        vo.setXM_ZQYYZS_XJFS(detailMap.get("XM_ZQYYZS_XJFS").toString());
        vo.setXM_ZQYYZS_XGPJ(detailMap.get("XM_ZQYYZS_XGPJ").toString());
        vo.setXM_YSYYZS_XJFS(detailMap.get("XM_YSYYZS_XJFS").toString());
        vo.setXM_YSYYZS_XGPJ(detailMap.get("XM_YSYYZS_XGPJ").toString());
        vo.setXM_DQFCSFZS_XJFS(detailMap.get("XM_DQFCSFZS_XJFS").toString());
        vo.setXM_DQFCSFZS_XGPJ(detailMap.get("XM_DQFCSFZS_XGPJ").toString());
    }


    public RegistInformationBchVo findAll() {
        List<RegistInformationBchListVo> vo = baseMapper.findAllBchList(UserUtils.getOrganId());
        //病区，病床号映射

        Map<String, StringBuilder> bchMap = new HashMap<>();
        for (RegistInformationBchListVo registInformationVo : vo) {
            String bqid = registInformationVo.getBqid();
            StringBuilder bch = new StringBuilder(registInformationVo.getBch());
            if (bchMap.containsKey(bqid)) {
                bchMap.put(bqid, bchMap.get(bqid).append(",").append(bch));
            } else {
                bchMap.put(bqid, bch);
            }
        }
        Map<String, List<String>> finalbchMap = new HashMap<>();
        for (Map.Entry<String, StringBuilder> entry : bchMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            List<String> list = Arrays.asList(value.split(","));
            //对list进行排序，如果String可以转换为int，则按int排序，如果String不能转换为int，且String为"+1","加2"这种类型，则按+号后面的数字排序
            list.sort((o1, o2) -> {
                // 判断是否为"+数字"的格式
                boolean isPlusNumber1 = (o1.startsWith("+") || o1.startsWith("加")) && o1.length() > 1 && Character.isDigit(o1.charAt(1));
                boolean isPlusNumber2 = (o2.startsWith("+") || o2.startsWith("加")) && o2.length() > 1 && Character.isDigit(o2.charAt(1));

                // 如果两个字符串都是"+数字"的格式，则按数字排序
                if (isPlusNumber1 && isPlusNumber2) {
                    return Integer.compare(Integer.parseInt(o1.substring(1)), Integer.parseInt(o2.substring(1)));
                }

                // 如果两个字符串都不是"+数字"的格式，则按整数排序
                if (!isPlusNumber1 && !isPlusNumber2) {
                    //如果能转换为整数，则按整数排序，如果不能则跳过
                    try {
                        return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }

                // 如果o1是"+数字"的格式，而o2不是，则o1应该排在o2前面
                if (isPlusNumber1) {
                    return -1;
                }

                // 如果o2是"+数字"的格式，而o1不是，则o1应该排在o2后面
                return 1;
            });
            finalbchMap.put(key, list);
        }
        RegistInformationBchVo registInformationBchVo = new RegistInformationBchVo();
        registInformationBchVo.setBch(finalbchMap);
        return registInformationBchVo;
    }

    public RegistInformationBchVo findBybchList(String bch, String bqid) {
        RegistInformationBchVo vo = baseMapper.findBybchList(bch, bqid, UserUtils.getOrganId());
        if (vo.getYszd() == null) {
            if (vo.getMzzd() == null) {
                vo.setMzzd("");
            }
            vo.setYszd(vo.getMzzd());
        }
        return vo;
    }


    /**
     * 风险汇总
     *
     * @param vo
     * @return
     */
    public PageData<RiskBaseInfo> riskSummary(RegistInformationSearchVo vo) throws IllegalAccessException {
        String organId = UserUtils.getOrganId();
        vo.setYljgid(organId);
        if (vo.getWards() == null || StrUtil.isBlank(vo.getWards())) {
            List<String> list = UserUtils.getLoginWards().stream().map(Dept::getId).toList();
            vo.setBqList(list);
        }
        if ((vo.getWards() == null || vo.getWards().isEmpty()) && vo.getBqList().isEmpty()){
            throw new ParameterException("未配置病区");
        }
        List<DicHljlwsVo> riskFormDictList = dicHljlwsMapper.getriskFormDictList(Constants.WSFL_FXPG, organId);
        RegistInformationSearchNoPageVo vo1 = new RegistInformationSearchNoPageVo();
        BeanUtil.copyProperties(vo, vo1);
        List<String> riskFormList = formsIndexService.getRiskFormList(vo1);
        //保留riskFormList和riskFormDictList.getId()的交集，返回List<DicHljlwsVo>
        riskFormList.retainAll(riskFormDictList.stream().map(DicHljlwsVo::getId).toList());
        vo.setRiskFormList(riskFormDictList);

//        //压疮wsid
//        String ycwsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.YCFXBD_WSBM, organId);
//        //跌倒wsid
//        String ddwsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.DDZCBD_WSBM, organId);
//        //导管wsid
//        String dgwsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.DGFXBD_WSBM, organId);
//        //风险发生wsid
//        String fswsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.FXFSBD_WSBM, organId);
//        vo.setYcwsid(ycwsid);
//        vo.setDdwsid(ddwsid);
//        vo.setDgwsid(dgwsid);
//        vo.setFswsid(fswsid);
        RegistInformationSearchVo all = new RegistInformationSearchVo();
        BeanUtil.copyProperties(vo, all);
        all.setPageSize(Integer.MAX_VALUE);
        all.setCurrent(1);
        IPage<RiskBaseInfo> riskBaseInfoIPage = baseMapper.riskSummary(all);
        List<RiskBaseInfo> records = riskBaseInfoIPage.getRecords();
        records.sort((o1, o2) -> {
            // 判断是否为"+数字"的格式
            boolean isPlusNumber1 = (o1.getBch().startsWith("+") || o1.getBch().startsWith("加")) && o1.getBch().length() > 1 && Character.isDigit(o1.getBch().charAt(1));
            boolean isPlusNumber2 = (o2.getBch().startsWith("+") || o2.getBch().startsWith("加")) && o2.getBch().length() > 1 && Character.isDigit(o2.getBch().charAt(1));

            // 如果两个字符串都是"+数字"的格式，则按数字排序
            if (isPlusNumber1 && isPlusNumber2) {
                return Integer.compare(Integer.parseInt(o1.getBch().substring(1)), Integer.parseInt(o2.getBch().substring(1)));
            }

            // 如果两个字符串都不是"+数字"的格式，则按整数排序
            if (!isPlusNumber1 && !isPlusNumber2) {
                //如果能转换为整数，则按整数排序，如果不能则跳过
                try {
                    return Integer.compare(Integer.parseInt(o1.getBch()), Integer.parseInt(o2.getBch()));
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            // 如果o1是"+数字"的格式，而o2不是，则o1应该排在o2前面
            if (isPlusNumber1) {
                return -1;
            }

            // 如果o2是"+数字"的格式，而o1不是，则o1应该排在o2后面
            return 1;
        });

        IPage<RiskBaseInfo> riskBaseInfoIPage1 = pageConvert(records, vo.getCurrent(), vo.getPageSize(), riskBaseInfoIPage.getTotal());
        List<RiskBaseInfo> records1 = riskBaseInfoIPage1.getRecords();
        for (RiskBaseInfo riskBaseInfo : records1) {
            NameUtil.convert(riskBaseInfo);
        }
        riskBaseInfoIPage1.setRecords(records1);
        return PageData.of(riskBaseInfoIPage1);
//        RiskVo riskCount = baseMapper.riskCount(ycwsid, ddwsid, dgwsid, vo.getPatientStatus(), vo.getWards(), vo.getBqList());
//        RiskVo happenCount = baseMapper.happenCount(fswsid, vo.getPatientStatus(), vo.getWards(), vo.getBqList());
//
//        happenCount.setCatheterRisk(riskCount.getCatheterRisk());
//        happenCount.setFallRisk(riskCount.getFallRisk());
//        happenCount.setPressRisk(riskCount.getPressRisk());
    }

//    public PageData<RegistInformationVo> riskSummaryDetail(RegistInformationSearchVo vo) {
//        String organId = UserUtils.getOrganId();
//        vo.setYljgid(organId);
//        if (vo.getWards() == null || StrUtil.isBlank(vo.getWards())) {
//            List<String> list = UserUtils.getLoginWards().stream().map(Dept::getId).toList();
//            vo.setBqList(list);
//        }
//        if ((vo.getWards() == null || vo.getWards().isEmpty()) && vo.getBqList().isEmpty()){
//            throw new ParameterException("未配置病区");
//        }
//        if (vo.getRiskType().isEmpty()) {
//            throw new ParameterException("风险类型不能为空");
//        }
//        String wsid = null;
//        if (vo.getRiskType().equals("1")) {
//            wsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.DDZCBD_WSBM, organId);
//        }
//        if (vo.getRiskType().equals("2")) {
//            wsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.YCFXBD_WSBM, organId);
//        }
//        if (vo.getRiskType().equals("3")) {
//            wsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.DGFXBD_WSBM, organId);
//        }
////        vo.setYcwsid(wsid);
//        return PageData.of(baseMapper.riskSummaryPress(vo));
//    }

    public List<RiskResVo> riskSummaryriskSummaryCounts(RegistInformationSearchNoPageVo vo) {
        String organId = UserUtils.getOrganId();
        vo.setYljgid(organId);
        if (vo.getWards() == null || StrUtil.isBlank(vo.getWards())) {
            List<String> list = UserUtils.getLoginWards().stream().map(Dept::getId).toList();
            vo.setBqList(list);
        }
        if ((vo.getWards() == null || vo.getWards().isEmpty()) && vo.getBqList().isEmpty()){
            throw new ParameterException("未配置病区");
        }
        List<DicHljlwsVo> riskFormDictList = dicHljlwsMapper.getriskFormDictList(Constants.WSFL_FXPG, organId);
        List<String> riskFormList = formsIndexService.getRiskFormList(vo);
        //保留riskFormList和riskFormDictList.getId()的交集，返回List<DicHljlwsVo>
        riskFormList.retainAll(riskFormDictList.stream().map(DicHljlwsVo::getId).toList());
        vo.setRiskFormList(riskFormDictList);
//        //压疮wsid
//        String ycwsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.YCFXBD_WSBM, organId);
//        //跌倒wsid
//        String ddwsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.DDZCBD_WSBM, organId);
//        //导管wsid
//        String dgwsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.DGFXBD_WSBM, organId);
        //风险发生wsid
        String fswsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.FXFSBD_WSBM, organId);
        RiskVo riskCount = baseMapper.riskCount(vo);
        RiskVo happenCount = baseMapper.happenCount(organId, fswsid, vo.getPatientStatus(), vo.getWards(), vo.getBqList());
//
//        happenCount.setR02(riskCount.getR02());
//        happenCount.setR03(riskCount.getR03());
//        happenCount.setR05(riskCount.getR05());
//        happenCount.setR07(riskCount.getR07());
//        happenCount.setR31(riskCount.getR31());
//        happenCount.setR32(riskCount.getR32());
//        happenCount.setR33(riskCount.getR33());
//        happenCount.setR04(riskCount.getR04());
//        happenCount.setR28(riskCount.getR28());
//        happenCount.setR06(riskCount.getR06());
//        happenCount.setR27(riskCount.getR27());
//        happenCount.setR30(riskCount.getR30());

        List<RiskResVo> risk2Vos = new ArrayList<>();
        RiskResVo r02Res = new RiskResVo();
        Risk2Vo r02 = new Risk2Vo();
        Happen2Vo r02Happen = new Happen2Vo();
        r02.setRiskBm("r02");
        r02.setRiskMc(FormCodeNameEnum.getNameByCode("02"));
        r02.setRiskCount(riskCount.getR02());
        r02Happen.setHappenBm("r02Happen");
        r02Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r02Happen"));
        r02Happen.setHappenCount(happenCount.getR02Happen());
        r02Res.setRisk2Vo(r02);
        r02Res.setHappen2Vo(r02Happen);
        risk2Vos.add(r02Res);

        RiskResVo r03Res = new RiskResVo();
        Risk2Vo r03 = new Risk2Vo();
        Happen2Vo r03Happen = new Happen2Vo();
        r03.setRiskBm("r03");
        r03.setRiskMc(FormCodeNameEnum.getNameByCode("03"));
        r03.setRiskCount(riskCount.getR03());
        r03Happen.setHappenBm("r03Happen");
        r03Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r03Happen"));
        r03Happen.setHappenCount(happenCount.getR03Happen());
        r03Res.setRisk2Vo(r03);
        r03Res.setHappen2Vo(r03Happen);
        risk2Vos.add(r03Res);

        RiskResVo r05Res = new RiskResVo();
        Risk2Vo r05 = new Risk2Vo();
        Happen2Vo r05Happen = new Happen2Vo();
        r05.setRiskBm("r05");
        r05.setRiskMc(FormCodeNameEnum.getNameByCode("05"));
        r05.setRiskCount(riskCount.getR05());
        r05Happen.setHappenBm("r05Happen");
        r05Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r05Happen"));
        r05Happen.setHappenCount(happenCount.getR05Happen());
        r05Res.setRisk2Vo(r05);
        r05Res.setHappen2Vo(r05Happen);
        risk2Vos.add(r05Res);

        RiskResVo r07Res = new RiskResVo();
        Risk2Vo r07 = new Risk2Vo();
        Happen2Vo r07Happen = new Happen2Vo();
        r07.setRiskBm("r07");
        r07.setRiskMc(FormCodeNameEnum.getNameByCode("07"));
        r07.setRiskCount(riskCount.getR07());
        r07Happen.setHappenBm("r07Happen");
        r07Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r07Happen"));
        r07Happen.setHappenCount(happenCount.getR07Happen());
        r07Res.setRisk2Vo(r07);
        r07Res.setHappen2Vo(r07Happen);
        risk2Vos.add(r07Res);

        RiskResVo r31Res = new RiskResVo();
        Risk2Vo r31 = new Risk2Vo();
        Happen2Vo r31Happen = new Happen2Vo();
        r31.setRiskBm("r31");
        r31.setRiskMc(FormCodeNameEnum.getNameByCode("31"));
        r31.setRiskCount(riskCount.getR31());
        r31Happen.setHappenBm("r31Happen");
        r31Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r31Happen"));
        r31Happen.setHappenCount(happenCount.getR31Happen());
        r31Res.setRisk2Vo(r31);
        r31Res.setHappen2Vo(r31Happen);
        risk2Vos.add(r31Res);

        RiskResVo r32Res = new RiskResVo();
        Risk2Vo r32 = new Risk2Vo();
        Happen2Vo r32Happen = new Happen2Vo();
        r32.setRiskBm("r32");
        r32.setRiskMc(FormCodeNameEnum.getNameByCode("32"));
        r32.setRiskCount(riskCount.getR32());
        r32Happen.setHappenBm("r32Happen");
        r32Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r32Happen"));
        r32Happen.setHappenCount(happenCount.getR32Happen());
        r32Res.setRisk2Vo(r32);
        r32Res.setHappen2Vo(r32Happen);
        risk2Vos.add(r32Res);

        RiskResVo r33Res = new RiskResVo();
        Risk2Vo r33 = new Risk2Vo();
        Happen2Vo r33Happen = new Happen2Vo();
        r33.setRiskBm("r33");
        r33.setRiskMc(FormCodeNameEnum.getNameByCode("33"));
        r33.setRiskCount(riskCount.getR33());
        r33Happen.setHappenBm("r33Happen");
        r33Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r33Happen"));
        r33Happen.setHappenCount(happenCount.getR33Happen());
        r33Res.setRisk2Vo(r33);
        r33Res.setHappen2Vo(r33Happen);
        risk2Vos.add(r33Res);

        RiskResVo r04Res = new RiskResVo();
        Risk2Vo r04 = new Risk2Vo();
        Happen2Vo r04Happen = new Happen2Vo();
        r04.setRiskBm("r04");
        r04.setRiskMc(FormCodeNameEnum.getNameByCode("04"));
        r04.setRiskCount(riskCount.getR04());
        r04Happen.setHappenBm("r04Happen");
        r04Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r04Happen"));
        r04Happen.setHappenCount(happenCount.getR04Happen());
        r04Res.setRisk2Vo(r04);
        r04Res.setHappen2Vo(r04Happen);
        risk2Vos.add(r04Res);

        RiskResVo r28Res = new RiskResVo();
        Risk2Vo r28 = new Risk2Vo();
        Happen2Vo r28Happen = new Happen2Vo();
        r28.setRiskBm("r28");
        r28.setRiskMc(FormCodeNameEnum.getNameByCode("28"));
        r28.setRiskCount(riskCount.getR28());
        r28Happen.setHappenBm("r28Happen");
        r28Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r28Happen"));
        r28Happen.setHappenCount(happenCount.getR28Happen());
        r28Res.setRisk2Vo(r28);
        r28Res.setHappen2Vo(r28Happen);
        risk2Vos.add(r28Res);

        RiskResVo r06Res = new RiskResVo();
        Risk2Vo r06 = new Risk2Vo();
        Happen2Vo r06Happen = new Happen2Vo();
        r06.setRiskBm("r06");
        r06.setRiskMc(FormCodeNameEnum.getNameByCode("06"));
        r06.setRiskCount(riskCount.getR06());
        r06Happen.setHappenBm("r06Happen");
        r06Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r06Happen"));
        r06Happen.setHappenCount(happenCount.getR06Happen());
        r06Res.setRisk2Vo(r06);
        r06Res.setHappen2Vo(r06Happen);
        risk2Vos.add(r06Res);

        RiskResVo r27Res = new RiskResVo();
        Risk2Vo r27 = new Risk2Vo();
        Happen2Vo r27Happen = new Happen2Vo();
        r27.setRiskBm("r27");
        r27.setRiskMc(FormCodeNameEnum.getNameByCode("27"));
        r27.setRiskCount(riskCount.getR27());
        r27Happen.setHappenBm("r27Happen");
        r27Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r27Happen"));
        r27Happen.setHappenCount(happenCount.getR27Happen());
        r27Res.setRisk2Vo(r27);
        r27Res.setHappen2Vo(r27Happen);
        risk2Vos.add(r27Res);

        RiskResVo r30Res = new RiskResVo();
        Risk2Vo r30 = new Risk2Vo();
        Happen2Vo r30Happen = new Happen2Vo();
        r30.setRiskBm("r30");
        r30.setRiskMc(FormCodeNameEnum.getNameByCode("30"));
        r30.setRiskCount(riskCount.getR30());
        r30Happen.setHappenBm("r30Happen");
        r30Happen.setHappenMc(FormCodeNameEnum.getNameByCode("r30Happen"));
        r30Happen.setHappenCount(happenCount.getR30Happen());
        r30Res.setRisk2Vo(r30);
        r30Res.setHappen2Vo(r30Happen);
        risk2Vos.add(r30Res);
        return risk2Vos;



    }

    @Transactional
    public String sync() {
        String organId = UserUtils.getOrganId();
        //入院告知wsid
        String rywsid = dicHljlwsMapper.getwsidDictBywsbm(Constants.ZYHZGZS_WSBM, organId);
        //护理措施风险告知wsid
        String xjwsid = dicHljlwsMapper.getwsidDictBywsbm(Constants.HLCSFXGZS_WSBM, organId);
        //入院评估wsid
        String pgwsid = dicHljlwsMapper.getwsidDictBywsbm(Constants.RYPG_WSBM, organId);

        //体征图章wsid
        String tztzwsid = dicHljlwsMapper.getwsidDictBywsbm(Constants.TZTZJLD_WSBM, organId);
        List<Task> taskList = new ArrayList<>();
        List<FormsIndex> indexList = new ArrayList<>();
        List<FormsDetail> details = new ArrayList<>();
        //todo 远程拉取同步最新病人信息
        QueryWrapper<RegistInformation> wrapper = new QueryWrapper<>();
        wrapper.eq("yljgid", organId);
        List<RegistInformation> syncList = baseMapper.selectList(wrapper);
        for (RegistInformation registInformation : syncList) {
            //如果rysj不超过1天，则setHzzt为01
            if (DateUtil.between(registInformation.getRysj(), DateUtil.date(), DateUnit.SECOND) < 3600 * 24) {
                if (registInformation.getHzzt().isEmpty()){
                    registInformation.setHzzt("01");
                }else {
                    if (registInformation.getHzzt().contains("01")) {
                        registInformation.setHzzt(registInformation.getHzzt() + ",01");
                    }
                }
                //生成自动任务待办
                List<Task> tasks = generateNewInTaskForm(registInformation.getId(), rywsid, xjwsid, pgwsid);
                taskList.addAll(tasks);

                //生成图章记录
                /**
                 * {
                 *   "recordIndex": {
                 *     "wsid": "165434545907B311FB8CF4F7D1334053",
                 *     "brid": "6736aa53c8068f88905abe1a",
                 *     "wsbm": "13",
                 *     "wsfl": "5",
                 *     "rq": "2024-12-05 02:00:00"
                 *   },
                 *   "detailMap": {
                 *     "XM_CLSJD": "02:00",
                 *     "XM_TZMC": "入院于",
                 *     "XM_CLRQ": "2024-12-05",
                 *     "XM_CZR": "管理员"
                 *   }
                 * }
                 */

            }
            FormsIndex index = generateInTZForm(registInformation.getId(), tztzwsid, organId, registInformation.getDjksid(), registInformation.getRysj());
            if (index != null){
                indexList.add(index);
                List<FormsDetail> details1 = generateInTZDetail(index.getId(), index.getRq());
                details.addAll(details1);
            }
        }
        //更新新入院的患者状态
        saveOrUpdateBatch(syncList);
        taskService.saveOrUpdateBatch(taskList);
        formsIndexService.saveOrUpdateBatch(indexList);
        formsService.saveOrUpdateBatch2(details);

        return "同步成功";
    }

    private List<FormsDetail> generateInTZDetail(String indexId, Date rq) {
        //将rq拆分成两个String,第一个为日期，第二个为时间
        String dateStr = DateUtil.format(rq, "yyyy-MM-dd");
        String timeStr = DateUtil.format(rq, "HH:mm:ss");
        String[][] detailsData = {
                {"XM_CLSJD", timeStr, "测量时间段"},
                {"XM_TZMC", "入院于","图章名称"},
                {"XM_CLRQ", dateStr,"测量日期"},
                {"XM_CZR", "系统录入","操作人"}
        };


// 创建FormsDetail对象数组
        FormsDetail[] details = new FormsDetail[detailsData.length];
        List<FormsDetail> details1 = new ArrayList<>();
// 使用循环为每个对象设置属性值
        for (int i = 0; i < detailsData.length; i++) {
            details[i] = new FormsDetail();
            details[i].setId(IdUtil.objectId());
            details[i].setBlwsid(indexId);
            details[i].setXmdm(detailsData[i][0]);
            details[i].setXmz(detailsData[i][1]);
            details[i].setXmmc(detailsData[i][2]);
            details1.add(details[i]);
        }
        return details1;
    }

    @Transactional(readOnly = true)
    protected FormsIndex generateInTZForm(String brid, String tztzwsid, String organId, String ksid, Date rysj) {
        QueryWrapper<FormsIndex> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("brid", brid)
                .eq("wsid", tztzwsid)
                .eq("wsbm", Constants.TZTZJLD_WSBM)
                .eq("del_flag", "0");
        Long l = formsIndexService.getBaseMapper().selectCount(queryWrapper1);
        if (l == 0L) {
            FormsIndex recordIndex = new FormsIndex();
            recordIndex.setId(IdUtil.objectId());
            recordIndex.setBrid(brid);
            recordIndex.setWsid(tztzwsid);
            recordIndex.setWsbm(Constants.TZTZJLD_WSBM);
            recordIndex.setWsfl("5");
            recordIndex.setRq(rysj);
            recordIndex.setYljgid(organId);
            recordIndex.setKsid(ksid);
            recordIndex.init();
            return recordIndex;
        }
        return null;
    }

    @Transactional(readOnly = true)
    protected List<Task> generateNewInTaskForm(String brid, String rywsid, String xjwsid, String pgwsid) {
        List<Task> list = new ArrayList<>();
        QueryWrapper<Task> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("brid", brid).eq("wsid", rywsid).eq("rwlx", "1").eq("del_flag", "0");

        long one = taskService.getBaseMapper().selectCount(queryWrapper1);
        if (one == 0L) {
            Task task1 = new Task();
            task1.setRwdm(TaskEnum.TASK_ZYHZGZS.getCode());
            task1.setRwmc(TaskEnum.TASK_ZYHZGZS.getName());
            task1.setBrid(brid);
            task1.setWsid(rywsid);
            task1.setId(IdUtil.objectId());
            task1.setRwjhkssj(DateUtil.date());
            task1.setRwlx("1");
            task1.setRwzt("2");
            list.add(task1);
        }

        QueryWrapper<Task> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("brid", brid).eq("wsid", xjwsid).eq("rwlx", "1").eq("del_flag", "0");
        long one1 = taskService.getBaseMapper().selectCount(queryWrapper2);
        if (one1 == 0L) {
            Task task2 = new Task();
            task2.setRwdm(TaskEnum.TASK_HLCSFXGZS.getCode());
            task2.setRwmc(TaskEnum.TASK_HLCSFXGZS.getName());
            task2.setBrid(brid);
            task2.setWsid(xjwsid);
            task2.setId(IdUtil.objectId());
            task2.setRwjhkssj(DateUtil.date());
            task2.setRwlx("1");
            task2.setRwzt("2");
            list.add(task2);
        }

        QueryWrapper<Task> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("brid", brid).eq("wsid", pgwsid).eq("rwlx", "1").eq("del_flag", "0");
        long one2 = taskService.getBaseMapper().selectCount(queryWrapper3);
        if (one2 == 0L) {
            Task task3 = new Task();
            task3.setRwdm(TaskEnum.TASK_RYPG.getCode());
            task3.setRwmc(TaskEnum.TASK_RYPG.getName());
            task3.setBrid(brid);
            task3.setWsid(pgwsid);
            task3.setId(IdUtil.objectId());
            task3.setRwjhkssj(DateUtil.date());
            task3.setRwlx("1");
            task3.setRwzt("2");
            list.add(task3);
        }
        return list;

    }

    public RegistInformationVo findById2(String id) {
        return baseMapper.findById2(id);
    }

    public String saveYszdById(RegistInformationVo vo) {
        Integer i = baseMapper.saveYszdById(vo);
        return i > 0 ? "保存成功" : "保存失败";
    }
    /**
     * 批量查询患者的信息
     */
    public List<RegistInformationListVo> batchRetrievePatients(PatientSearchVo vo) {
        vo.setYljgid(UserUtils.getOrganId());
        List<Dept> loginWards = UserUtils.getLoginWards();
        //获取loginWards中的主键ids
        List<String> bqList = loginWards.stream().map(Dept::getId).collect(Collectors.toList());
        vo.setBqList(bqList);
        if ("00".equals(vo.getHzzt())) {
            vo.setHzzt(null);
        }
        //String wsbm = Constants.TZJLD_WSBM;

        return this.baseMapper.listRetrievePatientss(vo);
    }
    /**
     * 批量查询患者的生命体征信息2
     */
    public List<PatientVitalSignVo> batchRetrievePatientVitalSignInfos(PatientVitalSignSearchVo vo) {
        List<PatientVitalSignVo> patientVitalSignVos = new ArrayList<>();
        Date sj = vo.getSj();
        String wsbm = Constants.TZJLD_WSBM;
        // 创建一个空集合
        Set<String> brids = new HashSet<>();
        // 批量查询 FormsSaveVo
        List<FormsSaveVoo> formsSaveVoos = formsIndexService.getItemDetailsByBridBatch(brids, sj, wsbm);
        if (formsSaveVoos.isEmpty()) {
            PatientVitalSignVo patientVitalSignVo = new PatientVitalSignVo();
            patientVitalSignVo.setRecordIndex(new FormsIndexVoo());
            patientVitalSignVo.setDetailMap(new HashMap<>());
            patientVitalSignVos.add(patientVitalSignVo);
            return patientVitalSignVos;
        } else {
            patientVitalSignVos = formsSaveVoos.stream().map(formsSaveVoo -> {
                PatientVitalSignVo patientVitalSignVo = new PatientVitalSignVo();
                patientVitalSignVo.setRecordIndex(formsSaveVoo.getRecordIndex());
                patientVitalSignVo.setDetailMap(formsSaveVoo.getDetailMap());
                patientVitalSignVo.setBrid(formsSaveVoo.getRecordIndex().getBrid());
                patientVitalSignVo.setSj(formsSaveVoo.getRecordIndex().getRq());
                patientVitalSignVo.setXmMb((String) formsSaveVoo.getDetailMap().get("XM_MB"));
                patientVitalSignVo.setXmHx((String) formsSaveVoo.getDetailMap().get("XM_HX"));
                patientVitalSignVo.setXmWcssy((String) formsSaveVoo.getDetailMap().get("XM_WCSSY"));
                patientVitalSignVo.setXmWcszy((String) formsSaveVoo.getDetailMap().get("XM_WCSZY"));
                patientVitalSignVo.setXmyzxYang((String) formsSaveVoo.getDetailMap().get("XM_ZYZXYANG"));
                patientVitalSignVo.setXmTw((String) formsSaveVoo.getDetailMap().get("XM_TW"));
                patientVitalSignVo.setXmXl((String) formsSaveVoo.getDetailMap().get("XM_XL"));
                return patientVitalSignVo;
            }).collect(Collectors.toList());
        }

        return patientVitalSignVos;
    }
    /**
     * 批量查询患者的血糖信息2
     */
    public List<PatientBloodGlucoseVo> batchRetrievePatientBloodGlucose(PatientBloodGlucoseSearchVo vo) {
        List<PatientBloodGlucoseVo> patientBloodGlucoseVos = new ArrayList<>();
        Date sj = vo.getSj();
        String wsbm = Constants.XTJCBD_WSBM;
        // 创建一个空集合
        Set<String> brids = new HashSet<>();
        // 批量查询 FormsSaveVo
        List<FormsSaveVoo> formsSaveVoos = formsIndexService.getItemDetailsByBridBatch(brids, sj, wsbm);
        if (formsSaveVoos.isEmpty()) {
            PatientBloodGlucoseVo patientBloodGlucoseVo = new PatientBloodGlucoseVo();
            patientBloodGlucoseVo.setRecordIndex(new FormsIndexVoo());
            patientBloodGlucoseVo.setDetailMap(new HashMap<>());
            patientBloodGlucoseVos.add(patientBloodGlucoseVo);
            return patientBloodGlucoseVos;
        } else {
            for (FormsSaveVoo formsSaveVoo : formsSaveVoos) {
                PatientBloodGlucoseVo patientBloodGlucoseVo = new PatientBloodGlucoseVo();
                patientBloodGlucoseVo.setBrid(formsSaveVoo.getRecordIndex().getBrid());
                patientBloodGlucoseVo.setSj(formsSaveVoo.getRecordIndex().getRq());
                patientBloodGlucoseVo.setXmClz((String) formsSaveVoo.getDetailMap().get("XM_CLZ"));
                patientBloodGlucoseVo.setRecordIndex(formsSaveVoo.getRecordIndex());
                patientBloodGlucoseVo.setDetailMap(formsSaveVoo.getDetailMap());
                patientBloodGlucoseVos.add(patientBloodGlucoseVo);
            }

        }
        return patientBloodGlucoseVos;
    }
}




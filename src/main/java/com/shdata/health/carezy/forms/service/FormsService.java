package com.shdata.health.carezy.forms.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.carezy.forms.entity.*;
import com.shdata.health.carezy.forms.mapper.*;
import com.shdata.health.carezy.forms.service.assess.ScalesAssess;
import com.shdata.health.carezy.forms.vo.*;
import com.shdata.health.carezy.patientmanage.mapper.TaskMapper;
import com.shdata.health.carezy.patientmanage.vo.TaskVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.exception.ParameterException;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * 病人护理记录单明细项表  Service服务
 *
 * @author myy
 * @date 2024-10-21
 */
@Service
@Transactional(readOnly = true)
public class FormsService extends BaseService<FormsDetailMapper,FormsDetail> {

    private final FormsIndexMapper formsIndexMapper;
    private final FormsDetailMapper formsDetailMapper;
    private final DicHljlwsbdMapper dicHljlwsbdMapper;
    private final DicHljlwsxmMapper dictHljlwsxmMapper;
    private final DicHljlwsMapper dictHljlwsMapper;
    private final FormsIndexService formsIndexService;
    private final TaskMapper taskMapper;
    private final AdmissionFormMapper admissionFormMapper;


    public FormsService(FormsIndexMapper formsIndexMapper, FormsDetailMapper formsDetailMapper, DicHljlwsbdMapper dicHljlwsbdMapper, DicHljlwsxmMapper dictHljlwsxmMapper, DicHljlwsMapper dictHljlwsMapper, FormsIndexService formsIndexService, TaskMapper taskMapper, AdmissionFormMapper admissionFormMapper) {
        this.formsIndexMapper = formsIndexMapper;
        this.formsDetailMapper = formsDetailMapper;
        this.dicHljlwsbdMapper = dicHljlwsbdMapper;
        this.dictHljlwsxmMapper = dictHljlwsxmMapper;
        this.dictHljlwsMapper = dictHljlwsMapper;
        this.formsIndexService = formsIndexService;
        this.taskMapper = taskMapper;
        this.admissionFormMapper = admissionFormMapper;
    }

    /**
     * 保存表单
     * @return
     * @throws Exception
     */
    @Transactional
    public FormsSaveTipsVo formsSave(FormsSaveVo vo){
        FormsIndexVo indexByFront = vo.getRecordIndex();         //前端传过来的Index[wsid、brid等]
        setIndexVal(indexByFront,vo.getDetailMap());             //设置字典编码
        //格式映射
        String zbId = IdUtil.objectId(); //生成子表的主键ID，同步项目明细表中的xmz
        FormsVo formsVo = mapToList(indexByFront.getWsid(), vo.getDetailMap(),zbId);//前端传过来的Details
        List<FormsDetailVo> detailsByFront = formsVo.getDetailList();//前端传过来的Details
        FormsIndex po = null;
        if (StrUtil.isNotBlank(indexByFront.getId())){
            po = formsIndexMapper.selectById(indexByFront.getId());//更新需要
        }
        //并发控制：新增or更新
        if(indexByFront.getRq() != null){
            String rq = DateUtil.format(indexByFront.getRq(), "yyyy-MM-dd HH:mm");
            FormsIndexVo indexVo = formsIndexMapper.getInfo(indexByFront.getBrid(),indexByFront.getWsid(),rq,UserUtils.getOrganId());  //同一分钟至多有一条
            if(indexVo != null){
                String userid = UserUtils.getUserId();
                String qmysid = indexVo.getQmysid();
                if(!userid.equals(qmysid)){  //当前分钟内有记录  并且  上次不是自己操作的，
                    throw new ParameterException(StrUtil.format("{}病人在当前分钟内已被修改，无需重复提交！",indexVo.getXm()));
                }
            }
        }else{
            throw new ParameterException("请选择日期！");
        }
        String id = IdUtil.objectId();//主表ID
        //Table类型的子表操作
        admissionForm(po,formsVo,id, indexByFront.getId(),zbId);//入院告知子表
        if (po == null){
            //1.入库T_HL_YW_HLJLWSSYB
            FormsIndex index = new FormsIndex();
            BeanUtil.copyProperties(indexByFront, index);
            index.setId(id);
            index.setYljgid(UserUtils.getOrganId());
            index.setKsid(UserUtils.getUserInfo().getDeptId());
            index.setQmysid(UserUtils.getUserId());
            if(index.getRq() == null){
                index.setRq(new Date());
            }
            DicHljlws dictHljlws = dictHljlwsMapper.selectById(indexByFront.getWsid());
            String bean = null;
            if(dictHljlws != null){
                bean = dictHljlws.getBean();
            }
            ScalesAssess scalesAssess = null;
            if (StrUtil.isNotBlank(bean)){
                scalesAssess = SpringUtil.getBean(bean);
                String score = scalesAssess.compute(detailsByFront,indexByFront);
                String result = scalesAssess.assess(score,indexByFront);
                if(NumberUtil.isNumber(score)){
                    index.setZf(Integer.parseInt(score));
                }
                index.setJl(result);
            }
            index.setJkzt(indexByFront.getJkzt());
            index.setFxdj(indexByFront.getFxdj());
            formsIndexMapper.insert(index);
            // 2.入库T_HL_YW_HLJLMX
            if (CollUtil.isNotEmpty(detailsByFront)){
                List<FormsDetail> list = detailsByFront.stream().map(detailVo -> {
                    FormsDetail detail = new FormsDetail();
                    BeanUtil.copyProperties(detailVo, detail);
                    detail.setId(IdUtil.objectId());
                    detail.setBlwsid(id);
                    return detail;
                }).toList();
                //是否必传、是否超长
                if(isTrueFormat(indexByFront.getWsid(),vo.getDetailMap())){
                    baseMapper.insertDetails(list);
                }
            }
            //3.任务单[只有新增存在，更新blwsid、RWZT、rwwcsj]
            if(indexByFront.getWsfl().equals(Constants.WSFL_RYGZ) || indexByFront.getWsfl().equals(Constants.WSFL_RYPG)){
                //只有分类是 入院评估、入院告知  两类
                List<TaskVo> taskList = taskMapper.getTaskByBridAndWsid(indexByFront.getBrid(),indexByFront.getWsid(),Constants.DB_TASK);
                taskMapper.deleteByBridAndWsid(indexByFront.getBrid(),indexByFront.getWsid());
                if(CollUtil.isNotEmpty(taskList)){
                    for(TaskVo task : taskList){
                        task.setBlwsid(id);
                        task.setRwzt(Constants.complete); //1完成,2未完成
                        task.setRwwcsj(new Date());
                    }
                    taskMapper.insertTasks(taskList);
                }
            }
            //返回提示
            FormsSaveTipsVo formsSaveTipsVo = new FormsSaveTipsVo();
            formsSaveTipsVo.setId(index.getId());
            formsSaveTipsVo.setMsg(StrUtil.isNotBlank(bean) ? "量表评估成功" : "表单保存成功");
            return formsSaveTipsVo;
        }else{
            //情况1：记录单/评估单更新，索引表无需更新。 情况2：量表更新，索引表需更新[fz、结论][计算]
            //1.明细单[T_HL_YW_HLJLMX]
            List<FormsDetailVo> detailList = getDetailList(formsVo,indexByFront);//前端传过来的项目明细
            formsDetailMapper.deleteDetail(indexByFront.getId());//删除项目明细中的原有数据
            if (CollUtil.isNotEmpty(detailList)){
                List<FormsDetail> list = detailList.stream().map(detailVo -> {
                    FormsDetail detail = new FormsDetail();
                    BeanUtil.copyProperties(detailVo, detail);
                    return detail;
                }).toList();
                //是否必传、是否超长
                if(isTrueFormat(indexByFront.getWsid(),vo.getDetailMap())){
                    baseMapper.insertDetails(list);
                }
            }
            //2.索引单[T_HL_YW_HLJLWSSYB]
            po.setBgsj(indexByFront.getBgsj());//拔管时间
            po.setFxfslb(indexByFront.getFxfslb());//风险发生类别
            po.setRq(indexByFront.getRq() == null ? new Date() : indexByFront.getRq());
            po.setDglx(indexByFront.getDglx());
            //科室+医生会变，机构不会变
            po.setKsid(UserUtils.getUserInfo().getDeptId());
            po.setQmysid(UserUtils.getUserId());
            String bean = dictHljlwsMapper.selectById(indexByFront.getWsid()).getBean();
            ScalesAssess scalesAssess = null;
            if (StrUtil.isNotBlank(bean)){
                scalesAssess = SpringUtil.getBean(bean);
                String jkzt = indexByFront.getJkzt();  //老的监控状态
                String score = scalesAssess.compute(detailList,indexByFront);
                String result = scalesAssess.assess(score,indexByFront);
                if(NumberUtil.isNumber(score)){
                    po.setZf(Integer.parseInt(score));
                }
                po.setJl(result);
                if(!indexByFront.getJkzt().equals(Constants.wxjk)){
                    indexByFront.setJkzt(jkzt);//老的赋值过来，更新业务层不主动改状态。只更新结果、分值、风险等级
                }
                if(indexByFront.getJkzt().equals(Constants.wjk) || indexByFront.getJkzt().equals(Constants.zrhsyjk) || indexByFront.getJkzt().equals(Constants.hscyjk)){
                    changeStatus(indexByFront,vo,po);//监控状态切换
                }
                //这里更新时，jkzt由1、4、5转3时，是没有赋值到po的
                if(indexByFront.getJkzt().equals(Constants.wxjk)){
                    po.setJkzt(indexByFront.getJkzt()); //监控状态
                }
            }
            po.setFxdj(indexByFront.getFxdj());//风险等级
            po.initByUpdate();
            formsIndexService.saveOrUpdate(po);
            FormsSaveTipsVo formsSaveTipsVo = new FormsSaveTipsVo();
            formsSaveTipsVo.setId(po.getId());
            formsSaveTipsVo.setMsg("更新成功");
            return formsSaveTipsVo;
        }
    }

    /**
     * 索引表赋值
     * @param indexVo
     * @param map
     */
    private void setIndexVal(FormsIndexVo indexVo,Map<String,Object> map){
        if (map!=null){
            //日期RQ
            String dateStr1 = (String)map.get("XM_RQ");
            indexVo.setRq(dateStr1!=null && !dateStr1.trim().isEmpty() ? DateUtil.parse(dateStr1,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            String dateStr2 = (String)map.get("XM_PGRQ");
            indexVo.setRq(dateStr2!=null && !dateStr2.trim().isEmpty() ? DateUtil.parse(dateStr2,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            String dateStr3 = (String)map.get("XM_JLSJ");
            indexVo.setRq(dateStr3!=null && !dateStr3.trim().isEmpty() ? DateUtil.parse(dateStr3,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            String dateStr4 = (String)map.get("XM_PGSJ");
            indexVo.setRq(dateStr4!=null && !dateStr4.trim().isEmpty() ? DateUtil.parse(dateStr4,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            //体温单、出入量
            String dateStr_5_1 = (String) map.get("XM_CLSJ");
            String dateStr_5_2 = (String) map.get("XM_SJJD");
            indexVo.setRq(StrUtil.isNotBlank(dateStr_5_1) && StrUtil.isNotBlank(dateStr_5_2) ? DateUtil.parse(dateStr_5_1 + " " + dateStr_5_2,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            //图章表、过敏表
            String dateStr_6_1 = (String) map.get("XM_CLRQ");
            String dateStr_6_2 = (String) map.get("XM_CLSJD");
            indexVo.setRq(StrUtil.isNotBlank(dateStr_6_1) && StrUtil.isNotBlank(dateStr_6_2) ? DateUtil.parse(dateStr_6_1 + " " + dateStr_6_2,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            String dateStr7 = (String)map.get("XM_XJSJ");
            indexVo.setRq(dateStr7!=null && !dateStr7.trim().isEmpty() ? DateUtil.parse(dateStr7,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            String dateStr8 = (String)map.get("XM_ZGSJ");
            indexVo.setRq(dateStr8!=null && !dateStr8.trim().isEmpty() ? DateUtil.parse(dateStr8,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            String dateStr9 = (String)map.get("XM_ZYRQ");
            indexVo.setRq(dateStr9!=null && !dateStr9.trim().isEmpty() ? DateUtil.parse(dateStr9,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            String dateStr10 = (String)map.get("XM_FSSJ");
            indexVo.setRq(dateStr10!=null && !dateStr10.trim().isEmpty() ? DateUtil.parse(dateStr10,"yyyy-MM-dd HH:mm") : indexVo.getRq());
            //风险发生类别[事件发生表]
            indexVo.setFxfslb(indexVo.getFxfslb()==null&&map.get("XM_FXFSLB")!=null? (String) map.get("XM_FXFSLB"): indexVo.getFxfslb());
            //拔管时间只在index表中，map中没有
            //导管类型
            indexVo.setDglx(indexVo.getDglx()==null&&map.get("XM_DGLX")!=null? (String) map.get("XM_DGLX"): indexVo.getDglx());
        }
    }
    /**
     * 监控状态切换规则
     */
    public void changeStatus(FormsIndexVo indexByFront, FormsSaveVo vo, FormsIndex index){
        //[未监控、责任护士已监控、护士长已监控]需要更新状态。 总护士长已监控不需要更新状态
        Map<String,Object> map = vo.getDetailMap();
        //未监控状态
        if(indexByFront.getJkzt().equals(Constants.wjk)){
            if (StrUtil.isNotBlank(String.valueOf(map.get(Constants.DutyNurse))) && map.get(Constants.DutyNurse).equals(Constants.YLS) && (StrUtil.isBlank(String.valueOf(map.get(Constants.HeadNurse))) || map.get(Constants.HeadNurse).equals(Constants.WLS)) && (StrUtil.isBlank(String.valueOf(map.get(Constants.TotalHeadNurse))) || map.get(Constants.TotalHeadNurse).equals(Constants.WLS))){
                index.setJkzt(Constants.zrhsyjk);
            } else if (StrUtil.isNotBlank(String.valueOf(map.get(Constants.DutyNurse))) && map.get(Constants.DutyNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.HeadNurse))) && map.get(Constants.HeadNurse).equals(Constants.YLS) && (StrUtil.isBlank(String.valueOf(map.get(Constants.TotalHeadNurse))) || map.get(Constants.TotalHeadNurse).equals(Constants.WLS))){
                index.setJkzt(Constants.hscyjk);
            }else if (StrUtil.isNotBlank(String.valueOf(map.get(Constants.DutyNurse))) && map.get(Constants.DutyNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.HeadNurse)))  && map.get(Constants.HeadNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.TotalHeadNurse))) && map.get(Constants.TotalHeadNurse).equals(Constants.YLS)){
                index.setJkzt(Constants.zhscyjk);
            }
        }
        //责任护士状态
        if (indexByFront.getJkzt().equals(Constants.zrhsyjk)){
            if(map.get(Constants.DutyNurse).equals(Constants.WLS)){
                index.setJkzt(Constants.wjk);
            }
            if(StrUtil.isNotBlank(String.valueOf(map.get(Constants.DutyNurse))) && map.get(Constants.DutyNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.HeadNurse))) && map.get(Constants.HeadNurse).equals(Constants.YLS) && (StrUtil.isBlank(String.valueOf(map.get(Constants.TotalHeadNurse))) || map.get(Constants.TotalHeadNurse).equals(Constants.WLS))){
                index.setJkzt(Constants.hscyjk);
            }else if(StrUtil.isNotBlank(String.valueOf(map.get(Constants.DutyNurse))) && map.get(Constants.DutyNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.HeadNurse)))  && map.get(Constants.HeadNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.TotalHeadNurse))) && map.get(Constants.TotalHeadNurse).equals(Constants.YLS)){
                index.setJkzt(Constants.zhscyjk);
            }
        }
        //护士长状态
        if (indexByFront.getJkzt().equals(Constants.hscyjk)){
            if(map.get(Constants.DutyNurse).equals(Constants.WLS)){
                index.setJkzt(Constants.wjk);
            }
            if(map.get(Constants.DutyNurse).equals(Constants.YLS) && map.get(Constants.HeadNurse).equals(Constants.WLS)){
                index.setJkzt(Constants.zrhsyjk);
            }
            if (StrUtil.isNotBlank(String.valueOf(map.get(Constants.DutyNurse))) && map.get(Constants.DutyNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.HeadNurse)))  && map.get(Constants.HeadNurse).equals(Constants.YLS) && StrUtil.isNotBlank(String.valueOf(map.get(Constants.TotalHeadNurse))) && map.get(Constants.TotalHeadNurse).equals(Constants.YLS)){
                index.setJkzt(Constants.zhscyjk);
            }
        }
        //总护士长状态
        if (indexByFront.getJkzt().equals(Constants.zhscyjk)){
            if(map.get(Constants.DutyNurse).equals(Constants.WLS)){
                index.setJkzt(Constants.wjk);
            }
            if(map.get(Constants.DutyNurse).equals(Constants.YLS) && map.get(Constants.HeadNurse).equals(Constants.WLS)){
                index.setJkzt(Constants.zrhsyjk);
            }
            if(map.get(Constants.DutyNurse).equals(Constants.YLS) && map.get(Constants.HeadNurse).equals(Constants.YLS) && map.get(Constants.TotalHeadNurse).equals(Constants.WLS)){
                index.setJkzt(Constants.hscyjk);
            }
        }
    }

    /**
     * 是否必传、是否超长
     * @param wsid
     * @param detailMap
     * @return
     */
    public boolean isTrueFormat(String wsid, Map<String, Object> detailMap){
        List<DicHljlwsbdVo> dicHljlwsbdList = dicHljlwsbdMapper.findByWsid(wsid,"");
        if (CollUtil.isNotEmpty(dicHljlwsbdList)){
            //是否必传
            List<DicHljlwsbdVo> btList = new ArrayList<>();//必传项目
            btList = dicHljlwsbdList.stream().filter(item->{
                //0:是； 1:否
                return item.getSfbt().equals(Constants.bc);
            }).toList();
            if (CollUtil.isNotEmpty(btList)) {
                for (DicHljlwsbdVo dic : btList) {
                    if (!detailMap.containsKey(dic.getXmdm()) || StrUtil.isBlank(String.valueOf(detailMap.get(dic.getXmdm())))) {
                        throw new ParameterException("项目：" + dic.getXmmc() + "未填");
                        //return false;
                    }
                }
            }
            //是否超长
//            for (DicHljlwsbdVo dic : dicHljlwsbdList) {
//                if (detailMap.containsKey(dic.getXmdm())) {
//                    if (StrUtil.length(detailMap.get(dic.getXmdm()).toString()) > dic.getXmcd()) {
//                        throw new ParameterException("项目：" + dic.getXmmc() + "超出固定内容长度");
//                        //return false;
//                    }
//                }
//            }
        }
        return true;
    }


    /**
     * 项目明细由map转为list
     * @param wsid        同一张表单
     * @param detailMap： key[项目代码]，value[文本：填写内容。字典：项目值[ID]]
     */
    public FormsVo mapToList(String wsid, Map<String, Object> detailMap,String zbId){
        List<DicHljlwsbdVo> dicHljlwsbdList = dicHljlwsbdMapper.findByWsid(wsid,"");//配置表[key]
        List<DicHljlwsxmVo> projectList = dictHljlwsxmMapper.getProject(wsid,""); //项目明细
        FormsVo formsVo = new FormsVo();
        List<FormsDetailVo> formsDetailList = new ArrayList<>();
        if (CollUtil.isNotEmpty(dicHljlwsbdList)){
            dicHljlwsbdList.forEach(dic -> {
                if (detailMap.containsKey(dic.getXmdm())) {     //map中存在当前xmdm[key]
                    FormsDetailVo vo = new FormsDetailVo();
                    vo.setXmdm(dic.getXmdm());                  //项目代码[map的key]
                    vo.setXmmc(dic.getXmmc());                  //项目名称
                    //2.DICT类型
                    if (CollUtil.isNotEmpty(projectList)) {
                        String[] split = String.valueOf(detailMap.get(dic.getXmdm())).split(",");
                        if (split.length > 0) {
                            StringBuilder zddms = new StringBuilder();
                            Long fz = 0L;
                            for (String s : split) {
                                for (DicHljlwsxmVo project : projectList) {
                                    if ("DICT".equals(dic.getXmlx()) && project.getZdlx().equals(dic.getXmzd())) {
                                        if (project.getZddm().equals(s)) {
                                            if (zddms.length() > 0) {
                                                zddms.append(",");
                                            }
                                            zddms.append(project.getZdmc());
                                            if (StrUtil.isNotBlank(String.valueOf(project.getFz()))) {
                                                fz += project.getFz();
                                            }
                                        }
                                    }
                                }
                            }
                            vo.setXmzsm(zddms.toString());
                            vo.setFz(fz);
                        }
                    }
                    //4.TABLE类型
                    Object value = detailMap.get(dic.getXmdm()); // LinkedHashMap
                    // 1.入院告知表：入院告知内容子表
                    processTableData(dic.getXmlx(), dic.getXmzd(), "TABLE", "T_HL_YW_RYGZNR", value,
                            AdmissionFormVo.class, formsVo::setAdmissionFormList);
                    //1.TEXT类型[输入内容]+3.FILE类型[接口返回的ids]+2.DICT类型[编码]+4.TABLE类型[array]。其中，TEXT类型、FILE类型的value不需要找字典的。只是FILE类型的value是接口返回的id拼接
                    if(detailMap.get(dic.getXmdm()) != null && detailMap.get(dic.getXmdm()) != "") {   //Object
                        if (dic.getXmlx().equals(Constants.TABLE_TYPE)){//Table类型[array]
                            vo.setXmz(zbId);
                        } else {//dic、text、file
                            vo.setXmz(detailMap.get(dic.getXmdm()).toString());
                        }
                        formsDetailList.add(vo);
                    }
                    formsVo.setDetailList(formsDetailList);
                }
            });
        }
        return formsVo;
    }

    /**
     * TABLE类型处理
     */
    private <T> void processTableData(String dicXmlx, String dicXmzd, String expectedXmlx, String expectedXmzd,
                                      Object value, Class<T> clazz, Consumer<List<T>> setter) {
        if(value instanceof List){
            List<Map<String, String>> list = (List<Map<String, String>>) value;//
            if (CollUtil.isNotEmpty(list)){//非空
                if (dicXmlx.equals(expectedXmlx) && dicXmzd.equals(expectedXmzd)) {
                    List<T> resultList = new ArrayList<>();
                    list.stream().forEach(map -> {
                        try {
                            T obj = clazz.getDeclaredConstructor().newInstance(); // T obj = BeanUtil.newInstance(clazz);   //clazz,  DiabetesMedicationVo.class
                            BeanUtil.copyProperties(map, obj);
                            resultList.add(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    setter.accept(resultList);
                }
            }
        }
    }

    /**
     * 重新获取每个项目的blwsid和id用于更新
     */
    private List<FormsDetailVo> getDetailList(FormsVo formsVo,FormsIndexVo indexByFront){
        List<FormsDetailVo> detailByFront = formsVo.getDetailList();
        //更新时，detailByFront和detailByEnd的数量可能不一致
        if(CollUtil.isNotEmpty(detailByFront)){
            detailByFront.forEach(detail -> {
                detail.setBlwsid(indexByFront.getId());
                detail.setId(UUID.randomUUID().toString().replace("-",""));  //detailByFront和detailByEnd的数量可能不一致,而且id不在需要，重新生成
            });
        }
        return detailByFront;
    }

    /**
     * 评估预览
     * key[项目代码]，value[文本：填写内容。字典：项目值[ID]]
     */
    public PreviewResultVo assessPreview(PreviewAssessVo previewAssessVo) throws Exception {
        String scoreByFront = previewAssessVo.getScore();  //前端计算的分值
        //1.动态对象
        String bean = dictHljlwsMapper.selectById(previewAssessVo.getWsid()).getBean();
        if (StrUtil.isBlank(bean)){
            throw new Exception("请输入正确量表文书");
        }
        ScalesAssess scalesAssess = SpringUtil.getBean(bean);
        //2.量表计算
        List<FormsDetailVo> scales = mapToList(previewAssessVo.getWsid(), previewAssessVo.getScaleItem(),"").getDetailList(); //前端传过来的Details
        String scoreByEnd = scalesAssess.compute(scales);
        if (!scoreByFront.equals(scoreByEnd)){
            return new PreviewResultVo("计算结果不一致", previewAssessVo.getScaleItem(), scoreByEnd, "");
        }
        //3.量表评估
        String result = scalesAssess.assess(scoreByEnd);
        return new PreviewResultVo("成功", previewAssessVo.getScaleItem(), scoreByEnd, result);
    }

    /**
     * 入院告知子表
     */
    public void admissionForm(FormsIndex po, FormsVo vo,String addId, String updateId,String zbId){
        List<AdmissionFormVo> admissionFormList = vo.getAdmissionFormList();
        if (po == null){
            if (CollUtil.isNotEmpty(admissionFormList)){
                List<AdmissionFormVo> admissionFormListByFilter = admissionFormList.stream().filter(item -> StrUtil.isNotBlank(item.getRygz())).toList();
                if(CollUtil.isNotEmpty(admissionFormListByFilter)){
                    List<AdmissionForm> list = admissionFormListByFilter.stream().map(admissionFormVo -> {
                        AdmissionForm admissionForm = new AdmissionForm();
                        BeanUtil.copyProperties(admissionFormVo, admissionForm);
                        admissionForm.setId(zbId);
                        admissionForm.setBlwsid(addId);
                        return admissionForm;
                    }).collect(Collectors.toList());
                    admissionFormMapper.insertAdmissionFormList(list);
                }
            }
        }else {
            List<String> oldIds = admissionFormMapper.getAdmissionForm(po.getId()).stream().map(admissionFormVo -> admissionFormVo.getId()).toList();//获取原来的ids
            if (CollUtil.isNotEmpty(oldIds)){
                admissionFormMapper.deleteAdmissionForm(oldIds);//删除原有数据
            }
            if (CollUtil.isNotEmpty(admissionFormList)){
                List<AdmissionFormVo> admissionFormListByFilter = admissionFormList.stream().filter(item -> StrUtil.isNotBlank(item.getRygz())).toList();
                if(CollUtil.isNotEmpty(admissionFormListByFilter)){
                    List<AdmissionForm> list = admissionFormListByFilter.stream().map(admissionFormVo -> {
                        AdmissionForm admissionForm = new AdmissionForm();
                        BeanUtil.copyProperties(admissionFormVo, admissionForm);
                        admissionForm.setId(zbId);
                        admissionForm.setBlwsid(updateId);
                        return admissionForm;
                    }).toList();
                    admissionFormMapper.insertAdmissionFormList(list);
                }
            }
        }
    }


    public List<DicHljlwsVo> getWs(WsbmSearchVo vo) {
        vo.setYljgid(UserUtils.getOrganId());
        return formsIndexMapper.getWs(vo);
    }

    public List<NurseVo> getNurse(String bqid) {
        return formsIndexMapper.getNurse(bqid);
    }

    public List<DicHljlwsVo> getWsByWsfl(String fldm) {
        String yljgid = UserUtils.getOrganId();
        return dictHljlwsMapper.getWsByWsfl(fldm,yljgid);
    }

    public void saveOrUpdateBatch2(List<FormsDetail> details) {
        formsDetailMapper.insertDetails(details);
    }


}

package com.shdata.health.carezy.forms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.carezy.forms.entity.FormsIndex;
import com.shdata.health.carezy.forms.mapper.*;
import com.shdata.health.carezy.forms.vo.*;
import com.shdata.health.carezy.patientmanage.mapper.RegistInformationMapper;
import com.shdata.health.carezy.patientmanage.mapper.TaskMapper;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationSearchNoPageVo;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationVo;
import com.shdata.health.carezy.patientmanage.vo.TaskVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 病人护理记录单文书索引表  Service服务
 *
 * @author myy
 * @date 2024-10-21
 */
@Service
@Transactional(readOnly = true)
public class FormsIndexService extends BaseService<FormsIndexMapper,FormsIndex> {
    private final DicHljlwsMapper dicHljlwsMapper;
    private final FormsDetailMapper formsDetailMapper;
    private final RegistInformationMapper registInformationMapper;
    private final TaskMapper taskMapper;
    private final AdmissionFormMapper admissionFormMapper;


    public FormsIndexService(DicHljlwsMapper dicHljlwsMapper, FormsDetailMapper formsDetailMapper, RegistInformationMapper registInformationMapper, TaskMapper taskMapper, AdmissionFormMapper admissionFormMapper) {
        this.dicHljlwsMapper = dicHljlwsMapper;
        this.formsDetailMapper = formsDetailMapper;
        this.registInformationMapper = registInformationMapper;
        this.taskMapper = taskMapper;
        this.admissionFormMapper = admissionFormMapper;
    }


    /**
     *  根据brid获取历史索引列表
     */
    public List<FormsIndexVo> getIndex(String brid, String wsid, String rq, String wsbm, String wsfl) {
        String yljgid = UserUtils.getOrganId();
        List<FormsIndexVo> indexList = baseMapper.getList(brid, wsid, rq, wsbm, wsfl,yljgid);
        //停报时间
        if(CollUtil.isNotEmpty(indexList)){
            int n = indexList.size();
            for (int i=0; i<n-1; i++) { //indexList是降序
                //【i】最新的[低]        【i+1】老记录[高]
                if(StrUtil.isNotBlank(indexList.get(i).getFxdj()) && StrUtil.isNotBlank(indexList.get(i+1).getFxdj()) && (indexList.get(i).getFxdj().equals(Constants.wfxdj) || indexList.get(i).getFxdj().equals(Constants.dfxdj) || indexList.get(i).getFxdj().equals(Constants.zfxdj)) && (indexList.get(i+1).getFxdj().equals(Constants.gfxdj)|| indexList.get(i).getFxdj().equals(Constants.jgfxdj))){
                    //将低风险的rq时间作为停报时间，并写入高风险记录中
                    Date rq1 = indexList.get(i).getRq();
                    indexList.get(i+1).setTbsj(rq1);
                }
            }
        }
        return indexList;
    }
    /**
     * 根据主键ID获取历史索引+历史详情
     */
    public FormsSaveVo getInfoById(String id, String brid, String wsid, String wsfl) throws Exception {
        String yljgid = UserUtils.getOrganId();
        FormsSaveVo fillVo = new FormsSaveVo();
        Map<String,Object> detailMap = new HashMap<>();    //表单项目明细
        //1.索引表
        FormsIndexVo indexVo = baseMapper.getIndex(id,brid, wsid, "",wsfl,yljgid);
        if (indexVo == null) {
            return fillVo;
        }
        fillVo.setRecordIndex(indexVo);
        //2.明细表
        List<FormsDetailVo> detailList = formsDetailMapper.getDetail(indexVo.getId());
        if(CollUtil.isNotEmpty(detailList)){ //list转为map，并将数组修改回字符串数组
            detailMap = detailList.stream().map(item -> {
                if(item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]") ){
                    if(item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]") ){
                        item.setXmz(item.getXmz());//table没有table类型，用原值
                    }else{
                        String[] numbers = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                        item = numbers[0] == "" ? item.setXmz(Arrays.asList()) : item.setXmz(numbers);
                    }
                }else{//单选或多选[非数组、非对象]
                    String str = String.valueOf(item.getXmz());
                    String regex = "^[0-9,]+$";//数字+英文逗号
                    if(str.matches(regex)){
                        String[] strs = str.split(",");
                        Arrays.sort(strs);//多选情况，需排序
                        String result=String.join(",", strs);
                        item.setXmz(result);
                    }else {
                        item.setXmz(str);
                    }
                }
                return item;
            }).collect(Collectors.toMap(FormsDetailVo::getXmdm,FormsDetailVo::getXmz));
        }
        fillVo.setDetailMap(detailMap);
        return fillVo;
    }

    /**
     * 根据brid获取历史索引+历史记录
     */
    public List<FormsSaveVo> getList(String brid, String wsid,String rq, String wsbm,String wsfl) {
        String yljgid = UserUtils.getOrganId();
        List<FormsSaveVo> list = new ArrayList<>();
        //获取全部额主键IDs
        List<FormsIndexVo> indexList = baseMapper.getList(brid, wsid, rq, wsbm, wsfl,yljgid);
        //停报时间
        if(CollUtil.isNotEmpty(indexList)){
            int n = indexList.size();
            for (int i=0; i<n-1; i++) { //indexList是降序
                //【i】最新的[低]        【i+1】老记录[高]
                if(StrUtil.isNotBlank(indexList.get(i).getFxdj()) && StrUtil.isNotBlank(indexList.get(i+1).getFxdj()) && (indexList.get(i).getFxdj().equals(Constants.wfxdj) || indexList.get(i).getFxdj().equals(Constants.dfxdj)  || indexList.get(i).getFxdj().equals(Constants.zfxdj)) && (indexList.get(i+1).getFxdj().equals(Constants.gfxdj)|| indexList.get(i).getFxdj().equals(Constants.jgfxdj))){
                    //将低风险的rq时间作为停报时间，并写入高风险记录中
                    Date rq1 = indexList.get(i).getRq();
                    indexList.get(i+1).setTbsj(rq1);
                }
            }
        }
        List<String> ids = new ArrayList<>();
        if (CollUtil.isNotEmpty(indexList)) {
            ids = indexList.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
        }else {
            return list;
        }
        //获取全部的项目详情
        List<FormsDetailVo> detailList = formsDetailMapper.getDetailByBatch(ids);
        for (FormsIndexVo index : indexList) {
            FormsSaveVo fillVo = new FormsSaveVo();
            //1.保存索引表
            fillVo.setRecordIndex(index);
            //2.完成配对[0至1个]
            List<FormsDetailVo> detailListByMatch = detailList.stream().filter(item -> item.getBlwsid().equals(index.getId())).collect(Collectors.toList());
            Map<String, Object> detailMap = new HashMap<>();    //表单项目明细
            if (CollUtil.isNotEmpty(detailListByMatch)) { //list转为map，并将数组修改回字符串数组
                detailMap = detailListByMatch.stream().map(item -> {
                    if (item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]")) {
                        if (item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]")) {
                            item.setXmz(item.getXmz());//table没有table类型，用原值
                        } else {
                            String[] numbers = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                            item = numbers[0] == "" ? item.setXmz(Arrays.asList()) : item.setXmz(numbers);
                        }
                    }else{//单选或多选[非数组、非对象]
                        String str = String.valueOf(item.getXmz());
                        String regex = "^[0-9,]+$";//数字+英文逗号
                        if(str.matches(regex)){
                            String[] strs = str.split(",");
                            Arrays.sort(strs);//多选情况，需排序
                            String result=String.join(",", strs);
                            item.setXmz(result);
                        }else {
                            item.setXmz(str);
                        }
                    }
                    return item;
                }).collect(Collectors.toMap(FormsDetailVo::getXmdm, FormsDetailVo::getXmz));
            }
            //3.保存明细表
            fillVo.setDetailMap(detailMap);
            list.add(fillVo);
        }
        return list;
    }
    /**
     * 根据brid获取历史索引+历史记录[分页][护理列表]
     */
    public PageData<FormsSaveVo> getListPage(FormsDetailSearchVo vo) {
        String yljgid = UserUtils.getOrganId();
        List<FormsSaveVo> list = new ArrayList<>();
        //1.索引表
        List<FormsIndexVo> indexList = baseMapper.getList(vo.getBrid(), vo.getWsid(),null,vo.getWsbm(), vo.getWsfl(),yljgid);
        List<String> ids = new ArrayList<>();
        if (CollUtil.isNotEmpty(indexList)) {
            ids = indexList.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
        }else {
            return PageData.of(new Page<>(vo.getPageNum(), vo.getPageSize()));
        }
        //获取全部的项目详情
        List<FormsDetailVo> detailList = formsDetailMapper.getDetailByBatch(ids);
        for (FormsIndexVo index : indexList) {
            FormsSaveVo fillVo = new FormsSaveVo();
            //1.保存索引表
            fillVo.setRecordIndex(index);
            //2.完成配对[0至1个]
            List<FormsDetailVo> detailListByMatch = detailList.stream().filter(item -> item.getBlwsid().equals(index.getId())).collect(Collectors.toList());
            Map<String, Object> detailMap = new HashMap<>();    //表单项目明细
            if (CollUtil.isNotEmpty(detailListByMatch)) { //list转为map，并将数组修改回字符串数组
                detailMap = detailListByMatch.stream().map(item -> {
                    if (item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]")) {
                        if (item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]")) {
                            //实现反序列化
                            ObjectMapper mapper = new ObjectMapper();
                            JSONArray jsonArray;
                            try {
                                jsonArray = mapper.readValue(item.getXmz().toString(), JSONArray.class);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            item.setXmz(jsonArray);
                        } else {
                            String[] numbers = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                            item = numbers[0] == "" ? item.setXmz(Arrays.asList()) : item.setXmz(numbers);
                        }
                    }else{//单选或多选[非数组、非对象]
                        String str = String.valueOf(item.getXmz());
                        String regex = "^[0-9,]+$";//数字+英文逗号
                        if(str.matches(regex)){
                            String[] strs = str.split(",");
                            Arrays.sort(strs);//多选情况，需排序
                            String result=String.join(",", strs);
                            item.setXmz(result);
                        }else {
                            item.setXmz(str);
                        }
                    }
                    return item;
                }).collect(Collectors.toMap(FormsDetailVo::getXmdm,FormsDetailVo::getXmz));
            }
            //3.保存明细表
            fillVo.setDetailMap(detailMap);
            list.add(fillVo);
        }
        //5.自定义分页逻辑
        int pageSize = vo.getPageSize() != 0 ? vo.getPageSize() : 10;
        int pageNum = vo.getPageNum() != 0 ? vo.getPageNum() : 1;
        int totalSize =  list.size();
        int start = Math.min((pageNum - 1) * pageSize, totalSize);//避免start > end
        int end = Math.min(start + pageSize , totalSize);
        List<FormsSaveVo> pageData =  list.subList(start, end);
        Page<FormsSaveVo> page = new Page<>(pageNum,pageSize, totalSize,true);
        page.setRecords(pageData);
        return PageData.of(page);
    }

    /**根据brid生命体征报告查询*/
    public VitalSignsFromsVo getVitalSigns(String brid, String wsid, String wsbm, String wsfl,String startTime,String endTime) {
        String yljgid = UserUtils.getOrganId();
        if(StrUtil.isBlank(brid)){
            throw new RuntimeException("请获取并输入brid！");
        }
        RegistInformationVo registInformationVo = registInformationMapper.findById2(brid);
        Date start = registInformationVo.getRysj();
        Date end = new Date();
        //全部数据
        List<FormsIndexVo> indexList = baseMapper.getVitalSignsOfIndex(brid, Constants.SMTZ_WSFL, wsid, wsbm,DateUtil.formatDate(start),DateUtil.formatDate(end),yljgid);
        List<FormsIndexVo> tzIndexList = new ArrayList<>();
        List<FormsIndexVo> crlIndexList = new ArrayList<>();
        List<FormsIndexVo> tuzhangIndexList = new ArrayList<>();
        List<FormsIndexVo> gmIndexList = new ArrayList<>();
        if(CollUtil.isNotEmpty(indexList)){
            tzIndexList = indexList.stream().filter(item -> Constants.TIZHENG_WSBM.equals(item.getWsbm())).collect(Collectors.toList());
            crlIndexList = indexList.stream().filter(item -> Constants.CRL_WSBM.equals(item.getWsbm())).collect(Collectors.toList());
            tuzhangIndexList = indexList.stream().filter(item -> Constants.TUZHENG_WSBM.equals(item.getWsbm())).collect(Collectors.toList());
            gmIndexList = indexList.stream().filter(item -> Constants.GM_WSBM.equals(item.getWsbm())).collect(Collectors.toList());
        }
        List<String> gmIds = new ArrayList<>();
        if(CollUtil.isNotEmpty(gmIndexList)){
            //过敏表数据，只保留当前最新的数据
            Map<String,FormsIndexVo> gmIndexMap = new HashMap<>();
            for (FormsIndexVo gm : gmIndexList) {
                String key = DateUtil.format(gm.getRq(),"yyyy-MM-dd");
                if(!gmIndexMap.containsKey(key)){
                    gmIndexMap.put(key, gm);
                }else{
                    FormsIndexVo oldItem = gmIndexMap.get(key);
                    if (gm.getCreateTime().after(oldItem.getCreateTime())) {
                        gmIndexMap.put(key, gm);
                    }
                }
            }
            gmIds = gmIndexMap.values().stream().map(item -> {return item.getId();}).collect(Collectors.toList());
        }
        //数据1：体征数据
        List<PhysicalSignsFormVo> physicalSignsFormList = new ArrayList<>();
        if(CollUtil.isNotEmpty(tzIndexList)){
            List<String> tzIds = tzIndexList.stream().map(item -> {return item.getId();}).collect(Collectors.toList());
            List<FormsDetailVo> TZList = formsDetailMapper.getDetailByBatch(tzIds);
            Map<String,List<FormsDetailVo>> TZMap = new HashMap<>();
            if (CollUtil.isNotEmpty(TZList)) {
                TZMap = TZList.stream().collect(Collectors.groupingBy(FormsDetailVo::getBlwsid));
            }
            Map<String, String> physicalSignsFormMap = new HashMap<>();
            Map<String, String> temp = Map.of(
                    "XM_CLSJ", "rq",
                    "XM_SJJD", "time",
                    "XM_CLTWBW", "bw",
                    "XM_TW", "tw",
                    "XM_MB", "mb",
                    "XM_HX", "hx",
                    "XM_FCTT", "fctt",
                    "XM_FCTW", "fctw",
                    "XM_XL", "xl",
                    "XM_WCSSY", "wcssy"
            );
            physicalSignsFormMap.putAll(temp);
            physicalSignsFormMap.put("XM_WCSZY","wcszy");
            physicalSignsFormMap.put("XM_ZYZXYANG","");//无需显示
            physicalSignsFormMap.put("XM_ZYZXYA","");//无需显示
            physicalSignsFormMap.put("XM_CZR","");//无需显示
            for (Map.Entry<String, List<FormsDetailVo>> entry : TZMap.entrySet()) {
                if(CollUtil.isNotEmpty(entry.getValue())){
                    PhysicalSignsFormVo physicalSignsFormVo = new PhysicalSignsFormVo();
                    for (FormsDetailVo detail : entry.getValue()) {
                        setDynamicValue(detail, physicalSignsFormMap, physicalSignsFormVo);
                    }
                    physicalSignsFormList.add(physicalSignsFormVo);
                }
            }
        }
        //数据2：出入量数据
        List<InAndOutFormsVo> inAndOutFormsList = new ArrayList<>();
        if(CollUtil.isNotEmpty(crlIndexList)){
            List<String> crlIds = crlIndexList.stream().map(item -> {return item.getId();}).collect(Collectors.toList());
            List<FormsDetailVo> CRLList = formsDetailMapper.getDetailByBatch(crlIds);
            Map<String,List<FormsDetailVo>> CRLMap = new HashMap<>();
            if (CollUtil.isNotEmpty(CRLList)) {
                CRLMap = CRLList.stream().collect(Collectors.groupingBy(FormsDetailVo::getBlwsid));
            }
            Map<String, String> inAndOutFormsMap = Map.of(
                    "XM_CLSJ", "rq",
                    "XM_SJJD", "time",
                    "XM_DBCS", "dbcs",
                    "XM_XB", "xb",
                    "XM_CL", "cl",
                    "XM_RL", "rl",
                    "XM_TZ", "tz",
                    "XM_XY", "xy",
                    "XM_CZR", "" //无需显示
            );
            for (Map.Entry<String, List<FormsDetailVo>> entry : CRLMap.entrySet()) {
                if(CollUtil.isNotEmpty(entry.getValue())){
                    InAndOutFormsVo inAndOutFormsVo = new InAndOutFormsVo();
                    for (FormsDetailVo detail : entry.getValue()) {
                        setDynamicValue(detail, inAndOutFormsMap, inAndOutFormsVo);
                    }
                    inAndOutFormsList.add(inAndOutFormsVo);
                }
            }
        }
        //
//        Map<Object, List<InAndOutFormsVo>> inAndOutMap = new HashMap<>();
//        if(CollUtil.isNotEmpty(inAndOutFormsList)) {
//            inAndOutMap = inAndOutFormsList.stream().collect(Collectors.groupingBy(InAndOutFormsVo::getRq));
//        }
//        List<InAndOutFormsVo> inAndOutFormsList1 = new ArrayList<>();
//        for (Map.Entry<Object, List<InAndOutFormsVo>> entry : inAndOutMap.entrySet()) {
//            InAndOutFormsVo Vo = new InAndOutFormsVo();
//            List<InAndOutFormsVo> list = entry.getValue();
//            if (CollUtil.isNotEmpty(list)) {
//                List<InAndOutFormsVo> sorted = list.stream().sorted(customTimeComparator).collect(Collectors.toList());  //根据时间降序排序
//                if (CollUtil.isNotEmpty(sorted)) {
//                    sorted.stream().forEach(item -> {
//                        Vo.setRq(item.getRq());
//                        Vo.setXM_CL(item.getXM_CL());
//                        Vo.setXM_DBCS(item.getXM_DBCS());
//                        Vo.setXM_TZ(item.getXM_TZ());
//                        Vo.setXM_RL(item.getXM_RL());
//                        Vo.setXM_XB(item.getXM_XB());
//                        Vo.setXM_XY(item.getXM_XY());
//                    });
//                }
//            }
//            inAndOutFormsList1.add(Vo);
//        }
        //
        //数据3：图章数据
        List<TuzhangFormsVo> tuzhangFormsList = new ArrayList<>();
        if(CollUtil.isNotEmpty(tuzhangIndexList)) {
            List<String> tuzhangIds = tuzhangIndexList.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
            List<FormsDetailVo> TuZhangList = formsDetailMapper.getDetailByBatch(tuzhangIds);
            Map<String,List<FormsDetailVo>> TuZhangMap = new HashMap<>();
            if (CollUtil.isNotEmpty(TuZhangList)) {
                TuZhangMap = TuZhangList.stream().collect(Collectors.groupingBy(FormsDetailVo::getBlwsid));
            }
            Map<String, String> tuzhangFormsMap = Map.of(
                    "XM_CLRQ", "rq",
                    "XM_CLSJD", "time",
                    "XM_TZMC", "tzName",
                    "XM_CZR", "" //无需显示
            );
            for (Map.Entry<String, List<FormsDetailVo>> entry : TuZhangMap.entrySet()) {
                if(CollUtil.isNotEmpty(entry.getValue())){
                    TuzhangFormsVo tuzhangFormsVo = new TuzhangFormsVo();
                    for (FormsDetailVo detail : entry.getValue()) {
                        setDynamicValue(detail, tuzhangFormsMap, tuzhangFormsVo);
                    }
                    tuzhangFormsList.add(tuzhangFormsVo);
                }
            }
        }
        //数据4：过敏源
        List<AllergyFormsVo> allergyFormsVos = new ArrayList<>();
        if(CollUtil.isNotEmpty(gmIndexList)) {
            List<FormsDetailVo> gmList = formsDetailMapper.getDetailByBatch(gmIds);
            Map<String,List<FormsDetailVo>> gmMap = new HashMap<>();
            if (CollUtil.isNotEmpty(gmList)) {
                gmMap = gmList.stream().collect(Collectors.groupingBy(FormsDetailVo::getBlwsid));
            }
            Map<String, String> allergyFormsMap = Map.of(
                    "XM_CLRQ", "rq",
                    "XM_CLSJD", "time",
                    "XM_GMLX", "gm",
                    "XM_YYX", ""//无需显示
            );
            for (Map.Entry<String, List<FormsDetailVo>> entry : gmMap.entrySet()) {
                if(CollUtil.isNotEmpty(entry.getValue())){
                    AllergyFormsVo allergyFormsVo = new AllergyFormsVo();
                    for (FormsDetailVo detail : entry.getValue()) {
                        setDynamicValue(detail, allergyFormsMap, allergyFormsVo);
                    }
                    allergyFormsVos.add(allergyFormsVo);
                }
            }
        }
        //数据5：住院天数、手术天数的相关数据
        List<DayCountVo> dayCountVos = new ArrayList<>();
        Long days = DateUtil.between(start, end, DateUnit.DAY);
        if(days != 0){
            Date start1 = DateUtil.offsetDay(start, -1);
            for (int i = 0; i <= days.intValue()+1; i++) {
                DayCountVo dayCountVo = new DayCountVo();
                start1 = DateUtil.offsetDay(start1, 1);
                dayCountVo.setRq(start1);
                dayCountVos.add(dayCountVo);
            }
        }
        if(CollUtil.isNotEmpty(tuzhangFormsList)){
            List<TuzhangFormsVo> sorted = tuzhangFormsList.stream().sorted(customTimeComparator1).collect(Collectors.toList());  //根据时间升序
            Date ry = start;
            dayCountVos.stream().forEach(item -> {
                Date date1 = null;
                if(item.getRq() != null){
                    date1 = DateUtil.parse(item.getRq().toString(),"yyyy-MM-dd");
                }
                if(date1 != null){;
                    Long ryts = DateUtil.between(DateUtil.parse(DateUtil.format(ry,"yyyy-MM-dd"),"yyyy-MM-dd"), date1, DateUnit.DAY,false);        //存在问题[不足一天，它没计算]
                    if(ryts > 0){
                        item.setRyts(ryts);
                    }
                }
            });
            //手术日
            List<Object> sssjList = new ArrayList<>();//手术日集合
            if(CollUtil.isNotEmpty(sorted)){
                for(TuzhangFormsVo tuzhangFormsVo : sorted){
                    if(tuzhangFormsVo.getTzName().equals("手术日")){
                        sssjList.add(tuzhangFormsVo.getRq());
                    }
                }
            }
            for(DayCountVo item : dayCountVos){
                List<Object> sstsList = new ArrayList<>();
                Date date1 = null;
                if(item.getRq() != null){
                    date1 = DateUtil.parse(item.getRq().toString(),"yyyy-MM-dd");
                }
                if(date1 != null){
                    if(CollUtil.isNotEmpty(sssjList)){
                        for(Object sssj : sssjList){
                            Date ss = DateUtil.parse(sssj.toString(),"yyyy-MM-dd");
                            Long ssts = ss != null ? DateUtil.between(ss, date1, DateUnit.DAY,false) : null;
                            if(ssts != null && ssts > 0){
                                sstsList.add(ssts);
                            }
                        }
                    }
                }
                item.setSsts(sstsList);
            }
        }
        //最终汇总
        VitalSignsFromsVo vo = new VitalSignsFromsVo();
        vo.setPhysicalSignsFormList(physicalSignsFormList);
        vo.setInAndOutFormsList(inAndOutFormsList);
        vo.setAllergyFormsList(allergyFormsVos);
        vo.setDayCountList(dayCountVos);
//        vo.setInAndOutFormsList(inAndOutFormsList1);
        vo.setTuzhangFormsList(tuzhangFormsList);
        return vo;
    }

    // 使用反射来设置值的方法
    public static void setDynamicValue(FormsDetailVo detail, Map<String, String> map, Object targetObject) {
        if (detail == null) {
            return;
        }
        String key = detail.getXmdm();
        String propertyName = map.get(key);
        Object propertyValue = detail.getXmz();
        if (StrUtil.isNotBlank(propertyName)) {
            try {
                Class<?> targetClass = targetObject.getClass();
                String setterMethodName = "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
                Method setterMethod = targetClass.getMethod(setterMethodName, Object.class); // setter方法接受Object
                setterMethod.invoke(targetObject, propertyValue);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    /**根据brid获取生命体征列表*/
    public List<VitalSignsListVo> getVitalSignsList(String brid, String rq, String wsid, String wsfl) {
        String yljgid = UserUtils.getOrganId();
        List<VitalSignsListVo> list = new ArrayList<>();
        //获取全部额主键IDs
        List<FormsIndexVo> indexList = baseMapper.getList(brid, wsid, rq, "", "5", yljgid);
        //过滤掉图章记录单
        if(CollUtil.isNotEmpty(indexList)){
            indexList = indexList.stream().filter(item -> !Constants.TUZHENG_WSBM.equals(item.getWsbm())).collect(Collectors.toList());
        }
        List<String> ids = new ArrayList<>();
        if (CollUtil.isNotEmpty(indexList)) {
            ids = indexList.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
        } else {
            return list;
        }
        //获取全部的项目详情
        List<FormsDetailVo> detailList = formsDetailMapper.getDetailByBatch(ids);
        Map<String, List<FormsDetailVo>> detailMap = new HashMap<>();
        if (CollUtil.isNotEmpty(detailList)) {
            detailMap = detailList.stream().collect(Collectors.groupingBy(FormsDetailVo::getBlwsid));
        }
        for (Map.Entry<String, List<FormsDetailVo>> entry : detailMap.entrySet()) {
            if(CollUtil.isEmpty(entry.getValue())){
                continue;
            }
            Object rqStr = entry.getValue().stream().filter(item -> item.getXmdm().equals("XM_CLSJ")).map(FormsDetailVo::getXmz).findFirst().orElseGet(() -> null);
            if(rqStr == null) {
                continue;
            }
            Object sjStr = entry.getValue().stream().filter(item -> item.getXmdm().equals("XM_SJJD")).map(FormsDetailVo::getXmz).findFirst().orElseGet(() -> null);
            Object czrStr = entry.getValue().stream().filter(item -> item.getXmdm().equals("XM_CZR")).map(FormsDetailVo::getXmz).findFirst().orElseGet(() -> null);
            //只要一条
            FormsDetailVo detail = entry.getValue().stream().filter(item -> item.getXmz() != null && !"".equals(item.getXmz())  && !item.getXmdm().equals("XM_CLSJ") && !item.getXmdm().equals("XM_SJJD")).findFirst().orElseGet(() -> null);
            VitalSignsListVo vo = new VitalSignsListVo();
            if (detail != null) {
                vo.setId(entry.getKey());//索引表主键
                FormsIndexVo index = indexList.stream().filter(item -> item.getId().equals(entry.getKey())).findFirst().orElseGet(() -> null);
                if (index != null){
                    vo.setWsid(index.getWsid());
                }
                vo.setRq(rqStr);
                vo.setSj(sjStr);
                vo.setMc(detail.getXmmc());
                vo.setXmz(detail.getXmz());
                vo.setCzr(czrStr);
            }else{
                continue;
            }
            list.add(vo);
        }
        if(CollUtil.isNotEmpty(list)){
            list = list.stream().sorted(customTimeComparator2).collect(Collectors.toList());//根据时间升序
        }
        return list;
    }


    // 自定义比较器
    Comparator<InAndOutFormsVo> customTimeComparator = (o1, o2) -> {
        String timeStr1 = (String) o1.getTime();
        String timeStr2 = (String) o2.getTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(timeStr1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(timeStr2, formatter);
        return dateTime1.compareTo(dateTime2); // 升序排序
    };
    Comparator<TuzhangFormsVo> customTimeComparator1 = (o1, o2) -> {
        String timeStr1 = (String) o1.getRq();
        String timeStr2 = (String) o2.getRq();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime1 = LocalDate.parse(timeStr1, formatter).atStartOfDay();
        LocalDateTime dateTime2 = LocalDate.parse(timeStr2, formatter).atStartOfDay();
        return dateTime1.compareTo(dateTime2); // 升序排序
    };
    Comparator<VitalSignsListVo> customTimeComparator2 = (o1, o2) -> {
        String timeStr1 = (String) o1.getRq();
        String timeStr2 = (String) o2.getRq();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime1 = LocalDate.parse(timeStr1, formatter).atStartOfDay();
        LocalDateTime dateTime2 = LocalDate.parse(timeStr2, formatter).atStartOfDay();
        return dateTime2.compareTo(dateTime1); // 降序排序
    };

    /**
     * 根据主键ID删除历史记录
     */
    @Transactional
    public String delete(String id) {
        if (StrUtil.isNotBlank(id)) {
            //索引表
            FormsIndex index = baseMapper.selectById(id);
            if(index == null){
                throw new ParameterException("无效的ID");
            }
            baseMapper.deleteById(id);
            //任务表
            if(index.getWsfl().equals(Constants.WSFL_RYGZ) || index.getWsfl().equals(Constants.WSFL_RYPG)){
                List<FormsIndexVo> indexList = baseMapper.getList(index.getBrid(),index.getWsid(),"","","",UserUtils.getOrganId());
                if(CollUtil.isEmpty(indexList)){
                    //没有已做的记录，更新任务表
                    List<TaskVo> taskList = taskMapper.getTaskByBridAndWsid(index.getBrid(),index.getWsid(),Constants.WC_TASK);
                    if(CollUtil.isNotEmpty(taskList)){
                        taskList.forEach(item -> {
                            item.setRwzt(Constants.DB_TASK);
                            item.setBlwsid("");
                            item.setRwwcsj(null);
                        });
                        taskMapper.updateBatch(taskList);
                    }
                }
            }
            return "删除成功";
        }else {
            throw new ParameterException("ID不能为空");
        }
    }
    /**
     * 删除某天的表单记录
     */
    public String deleteByDate(String brid, String wsid, String date, String wsfl) {
        String yljgid = UserUtils.getOrganId();
        if(StrUtil.isNotBlank(brid) && StrUtil.isNotBlank(wsid) && StrUtil.isNotBlank(date)){
            baseMapper.deleteByDate(brid, wsid, date, wsfl,yljgid);
            return "删除成功";
        } else {
            return "请输入正确参数";
        }
    }

    /**
     * 风险评估列表[全部]
     */
    public Map<String,List<RiskListVo>> getRiskList(String brid) {
        Map<String,List<RiskListVo>> map = new HashMap<>();
        String yljgid = UserUtils.getOrganId();
        List<RiskListVo> list = baseMapper.getRiskList(brid,Constants.FXPG_WSFL,yljgid);
        if(CollUtil.isNotEmpty(list)){
            map = list.stream().collect(Collectors.groupingBy(RiskListVo::getWsid));
        }
        return map;
    }

    /**
     * 风险评估列表[最新]
     */
    public List<RiskListVo> getRiskListNew(String brid) {
        String yljgid = UserUtils.getOrganId();
        List<RiskListVo> list = baseMapper.getRiskList(brid,Constants.FXPG_WSFL,yljgid);
        if (CollUtil.isEmpty(list)){
            return new ArrayList<>();
        }
        return list.stream().collect(Collectors.groupingBy(RiskListVo::getWsid)).entrySet()
                .stream().map(entry -> entry.getValue().stream().filter(item -> StrUtil.isNotEmpty(item.getTime())).max(Comparator.comparing(RiskListVo::getTime)))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    /**
     * 风险评估列表[今日]
     */
    public List<RiskListVo> getRiskListToday(String brid) {
        String yljgid = UserUtils.getOrganId();
        List<RiskListVo> list = baseMapper.getRiskList(brid,Constants.FXPG_WSFL,yljgid);
        if (CollUtil.isEmpty(list)){
            return new ArrayList<>();
        }
        return list.stream().filter(item -> DateUtil.parse(item.getTime(),"yyyy-MM-dd").equals(DateUtil.parse(DateUtil.today(),"yyyy-MM-dd"))).collect(Collectors.toList());
    }

    /**
     * 风险评估右侧列表[已监控[共4种]、未监控]
     */
    public List<FormsIndexVo> getRiskListRight(String brid) {
        String yljgid = UserUtils.getOrganId();
        List<FormsIndexVo> indexList = baseMapper.getList(brid, "", "", "", Constants.FXPG_WSFL,yljgid);
        List<FormsIndexVo> ans = new ArrayList<>();
        if(CollUtil.isNotEmpty(indexList)){
            List<FormsIndexVo> indexListByFilter = indexList.stream().filter(item -> StrUtil.isNotBlank(item.getJkzt())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(indexListByFilter)){
                ans = indexListByFilter.stream().filter(item -> {
                    String jkzt = item.getJkzt();
                    return jkzt != null && (jkzt.equals(Constants.wjk) || jkzt.equals(Constants.yjk));
                }).collect(Collectors.toList());
            }
        }
        return ans;
    }

    /**
     * 健康宣教是否已做
     */
    public MarkedVo getHealthExplain(String brid) {
        String yljgid = UserUtils.getOrganId();
        List<FormsIndexVo> indexList = baseMapper.getList(brid, "", "", "", Constants.JKXJ_WSFL,yljgid);
        MarkedVo markedVo = new MarkedVo();
        Map<String,List<FormsIndexVo>> map = new HashMap<>();
        if(CollUtil.isNotEmpty(indexList)){
            map = indexList.stream().collect(Collectors.groupingBy(FormsIndexVo::getWsbm));
            if(CollUtil.isNotEmpty(map)){
                if(CollUtil.isNotEmpty(map.get(Constants.RYXJ_WSBM))){
                    markedVo.setAdmissionEducation(Constants.complete);
                }else{
                    markedVo.setAdmissionEducation(Constants.uncomplete);
                }
                if(CollUtil.isNotEmpty(map.get(Constants.ZYXJ_WSBM))){
                    markedVo.setHospitalEducation(Constants.complete);
                }else {
                    markedVo.setHospitalEducation(Constants.uncomplete);
                }
                if(CollUtil.isNotEmpty(map.get(Constants.WSXJ_WSBM))){
                    markedVo.setHealthEducation(Constants.complete);
                }else {
                    markedVo.setHealthEducation(Constants.uncomplete);
                }
                if(CollUtil.isNotEmpty(map.get(Constants.CYXJ_WSBM))){
                    markedVo.setDischargeEducation(Constants.complete);
                }else{
                    markedVo.setDischargeEducation(Constants.uncomplete);
                }
            }
        }
        return markedVo;
    }

    /**
     * 是否停报
     * @param brid
     * @param wsid
     * @return
     */
    public String getStopTime(String brid,String wsid) {
        String yljgid = UserUtils.getOrganId();
        List<FormsIndexVo> indexList = baseMapper.getList(brid, wsid, "", "", "",yljgid);
        if(CollUtil.isNotEmpty(indexList)){
            int n = indexList.size();
            for (int i=0; i<n-1; i++) { //indexList是降序
                //【i】最新的[低]        【i+1】老记录[高]
                if(StrUtil.isNotBlank(indexList.get(i).getFxdj()) && StrUtil.isNotBlank(indexList.get(i+1).getFxdj()) && (indexList.get(i).getFxdj().equals(Constants.wfxdj) || indexList.get(i).getFxdj().equals(Constants.dfxdj)  || indexList.get(i).getFxdj().equals(Constants.zfxdj)) && (indexList.get(i+1).getFxdj().equals(Constants.gfxdj)|| indexList.get(i).getFxdj().equals(Constants.jgfxdj))){
                    //将低风险的rq时间作为停报时间，并写入高风险记录中
                    Date rq1 = indexList.get(i).getRq();
                    indexList.get(i+1).setTbsj(rq1);
                }
            }
        }
        return "停报对比完毕";
    }

    /**
     * 获取当前病人的全部表单信息
     * @param  brid+wsfl[一级菜单]；brid+wsfl+wsid[二级菜单]
     * @return List<FormsSaveVo>
     */
    public List<FormsSaveLineVo> getList2(String brid, String wsid, String wsfl) {
        String yljgid = UserUtils.getOrganId();
        List<FormsSaveLineVo> list = new ArrayList<>();
        //1.索引表
        List<FormsIndexVo> indexList = baseMapper.getList2(brid, wsid,null,null, wsfl,yljgid);
        List<String> ids = new ArrayList<>();
        if (CollUtil.isNotEmpty(indexList)) {
            ids = indexList.stream().map(FormsIndexVo::getId).toList();
        }
        for (String id : ids) {
            FormsSaveLineVo fillVo = new FormsSaveLineVo();
            //1.索引表
            FormsIndexVo indexVo = baseMapper.getIndex(id,brid, wsid,null, wsfl,yljgid);
            fillVo.setRq(indexVo.getRq());
            //2.明细表
            List<FormsDetailVo> detailList = formsDetailMapper.getDetail(indexVo.getId());
            Map<String,Object> detailMap = new HashMap<>();    //表单项目明细
            if(CollUtil.isNotEmpty(detailList)){ //list转为map，并将数组修改回字符串数组
                detailMap = detailList.stream().map(item -> {
                    if(item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]") ){
                        if(item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]") ){
                            item.setXmz(item.getXmz());//table没有table类型，用原值
                        }else{
                            String[] numbers = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                            item = Objects.equals(numbers[0], "") ? item.setXmz(List.of()) : item.setXmz(numbers);
                        }
                    }else{//单选或多选[非数组、非对象]
                        String str = String.valueOf(item.getXmz());
                        String regex = "^[0-9,]+$";//数字+英文逗号
                        if(str.matches(regex)){
                            String[] strs = str.split(",");
                            Arrays.sort(strs);//多选情况，需排序
                            String result=String.join(",", strs);
                            item.setXmz(result);
                        }else {
                            item.setXmz(str);
                        }
                    }
                    return item;
                }).collect(Collectors.toMap(FormsDetailVo::getXmdm,FormsDetailVo::getXmz));
            }
            fillVo.setDetailMap(detailMap);
            //生成list集合
            list.add(fillVo);
        }
        return list;
    }

    /**
     * 全景护理-简要表单信息
     * @param brid
     * @param wsfl
     * @return
     */
    public List<FormsIndexVo> getIndexInfo(String brid, String wsfl){
        return baseMapper.getList(brid, null, null,null,wsfl, UserUtils.getOrganId());
    }
    public String getWsidByylgjAndwsbm(String brid, String wsbm, String yljgid) {
        return baseMapper.getWsidByylgjAndwsbm(brid, wsbm, yljgid);
    }

    public String getWsidByylgjAndwsbm2(String wsbm, String yljgid) {
        return baseMapper.getWsidByylgjAndwsbm2(wsbm, yljgid);
    }

    /**
     * 批量获取病人所有体征索引表及对应的项目明细表
     * @param bridList 病人ID列表
     * @param wsbm 体征编号
     * @param sj 查询时间或其他条件
     * @return List<FormsSaveVo> 返回包含所有病人表单信息的列表
     */
    public List<FormsSaveVo> getBatchList(List<String> bridList, String wsbm, Date sj) {
        String yljgid = UserUtils.getOrganId();  // 获取医院机构ID
        List<FormsSaveVo> resultList = new ArrayList<>();

        // 批量查询病人的索引表
        List<FormsIndexVo> indexList = baseMapper.getBatchList(bridList, wsbm, sj, yljgid);
        List<String> ids = new ArrayList<>();

        // 获取所有索引表的ID
        if (CollUtil.isNotEmpty(indexList)) {
            ids = indexList.stream().map(FormsIndexVo::getId).collect(Collectors.toList());
        }

        // 根据索引表的ID，查询每个病人的表单明细
        for (String id : ids) {
            FormsSaveVo formsSaveVo = new FormsSaveVo();
            // 获取对应的索引表信息
            FormsIndexVo indexVo = baseMapper.getIndexList(id, bridList, wsbm, sj, yljgid);
            formsSaveVo.setRecordIndex(indexVo);
            // 获取对应的明细表信息
            List<FormsDetailVo> detailList = formsDetailMapper.getDetail(indexVo.getId());
            Map<String, Object> detailMap = new HashMap<>();  // 存放表单项目信息
            if (CollUtil.isNotEmpty(detailList)) {
                // 处理明细表，将list转换为map，并修改数组为字符串数组
                detailMap = detailList.stream().map(item -> {
                    if (item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]")) {
                        if (item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]")) {
                            item.setXmz(item.getXmz());//table没有table类型，用原值
                        } else {
                            String[] numbers = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                            item.setXmz(numbers[0].isEmpty() ? Arrays.asList() : Arrays.asList(numbers));
                        }
                    }else{//单选或多选[非数组、非对象]
                        String str = String.valueOf(item.getXmz());
                        String regex = "^[0-9,]+$";//数字+英文逗号
                        if(str.matches(regex)){
                            String[] strs = str.split(",");
                            Arrays.sort(strs);//多选情况，需排序
                            String result=String.join(",", strs);
                            item.setXmz(result);
                        }else {
                            item.setXmz(str);
                        }
                    }
                    return item;
                }).collect(Collectors.toMap(FormsDetailVo::getXmdm, FormsDetailVo::getXmz));
            }
            formsSaveVo.setDetailMap(detailMap);
            resultList.add(formsSaveVo);
        }

        return resultList;
    }

    /**
     * 根据brid获取历史索引+历史记录
     */
    public List<FormsSaveVo> getItemDetails(String brid, String wsid,Date rq, String wsbm,String wsfl) {
        String yljgid = UserUtils.getOrganId();
        List<FormsSaveVo> list = new ArrayList<>();
        //获取全部额主键IDs
        List<FormsIndexVo> indexList = baseMapper.getIndexFormList(brid, wsid, rq, wsbm, wsfl,yljgid);
        List<String> ids = new ArrayList<>();
        if (CollUtil.isNotEmpty(indexList)) {
            ids = indexList.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
        }else {
            return list;
        }
        //获取全部的项目详情
        List<FormsDetailVo> detailList = formsDetailMapper.getDetailByBatch(ids);
        for (FormsIndexVo index : indexList) {
            FormsSaveVo fillVo = new FormsSaveVo();
            //1.保存索引表
            fillVo.setRecordIndex(index);
            //2.完成配对[0至1个]
            List<FormsDetailVo> detailListByMatch = detailList.stream().filter(item -> item.getBlwsid().equals(index.getId())).collect(Collectors.toList());
            Map<String, Object> detailMap = new HashMap<>();    //表单项目明细
            if (CollUtil.isNotEmpty(detailListByMatch)) { //list转为map，并将数组修改回字符串数组
                detailMap = detailListByMatch.stream().map(item -> {
                    if (item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]")) {
                        if (item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]")) {
                            item.setXmz(item.getXmz());//table没有table类型，用原值
                        } else {
                            String[] numbers = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                            item = numbers[0] == "" ? item.setXmz(Arrays.asList()) : item.setXmz(numbers);
                        }
                    }else{//单选或多选[非数组、非对象]
                        String str = String.valueOf(item.getXmz());
                        String regex = "^[0-9,]+$";//数字+英文逗号
                        if(str.matches(regex)){
                            String[] strs = str.split(",");
                            Arrays.sort(strs);//多选情况，需排序
                            String result=String.join(",", strs);
                            item.setXmz(result);
                        }else {
                            item.setXmz(str);
                        }
                    }
                    return item;
                }).collect(Collectors.toMap(FormsDetailVo::getXmdm, FormsDetailVo::getXmz));
            }
            //3.保存明细表
            fillVo.setDetailMap(detailMap);
            list.add(fillVo);
        }
        return list;
    }

    public List<String> getRiskFormList(RegistInformationSearchNoPageVo vo) {
        return baseMapper.getRiskFormList(vo);
    }
    public List<FormsSaveVoo> getItemDetailsByBridBatch(Set<String> bridSet, Date sj, String wsbm) {
        String yljgid = UserUtils.getOrganId();
        List<FormsSaveVoo> resultList = new ArrayList<>();
        /* if (CollUtil.isEmpty(bridSet)) {
            return resultList;  // 如果没有病人ID，返回空
        } */
        // 1. 获取所有索引表数据 (批量查询)
        List<FormsIndexVoo> indexList = baseMapper.getIndexFormListByBridBatch(bridSet, sj, wsbm, yljgid);
        if (CollUtil.isEmpty(indexList)) {
            return resultList;  // 如果没有找到相关的索引记录，直接返回空
        }
        // 2. 获取所有项目明细数据 (批量查询)
        List<String> indexIds  = indexList.stream().map(FormsIndexVoo::getId).collect(Collectors.toList());
        List<FormsDetailVo> detailList = formsDetailMapper.getDetailByBatch(indexIds);
        //将项目明细按照索引ID分组
        Map<String, List<FormsDetailVo>> detailMapByIndexId = detailList.stream().collect(Collectors.groupingBy(FormsDetailVo::getBlwsid));
        // 4. 遍历每个索引，生成 FormsSaveVo
        for (FormsIndexVoo index : indexList) {
            FormsSaveVoo formsSaveVoo = new FormsSaveVoo();
            // 1. 设置索引表信息
            formsSaveVoo.setRecordIndex(index);
            //获取对应的项目明细
            Map<String, Object> detailMap = new HashMap<>();
            List<FormsDetailVo> matchingDetails   = detailMapByIndexId.get(index.getId());
            if (CollUtil.isNotEmpty(matchingDetails)) {
                detailMap = matchingDetails.stream().map(item -> {
                    // 处理 xmz 字段数据的解析
                    if (item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]")) {
                        if (item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]")) {
                            // 处理 JSON 格式的数据
                            String jsonData = item.getXmz().toString().replace("=", ":")
                                    .replaceAll("([a-zA-Z]+):", "\"$1\":")
                                    .replaceAll(":([,\\}])", ":\"\"$1")  // 空值处理，确保空的值替换为 ""
                                    .replace("，", ",");  // 将中文逗号替换为英文逗号
                            JSONArray jsonArray = new JSONArray(jsonData);
                            item.setXmz(jsonArray);
                        } else {
                            // 处理数组格式的数据
                            String[] numbers = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                            item = numbers[0] == "" ? item.setXmz(Arrays.asList()) : item.setXmz(numbers);
                        }
                    } else { // 单选或多选[非数组、非对象]
                        String str = String.valueOf(item.getXmz());
                        String regex = "^[0-9,]+$";//数字+英文逗号
                        if(str.matches(regex)){
                            String[] strs = str.split(",");
                            Arrays.sort(strs); // 多选情况，需排序
                            String result = String.join(",", strs);
                            item.setXmz(result);
                        } else {
                            item.setXmz(str);
                        }
                    }
                    return item;
                }).collect(Collectors.toMap(FormsDetailVo::getXmdm, FormsDetailVo::getXmz));
            }
            // 设置项目明细
            formsSaveVoo.setDetailMap(detailMap);
            resultList.add(formsSaveVoo);

        }
        return resultList;
    }

    /**
     * 是否已做导管
     */
    public String getDoDuct(String brid) {
        String yljgid = UserUtils.getOrganId();
        List<FormsIndexVo> list = baseMapper.getDoDuct(brid,Constants.DG_FLDM,yljgid);
        return CollUtil.isEmpty(list) ? Constants.uncomplete : Constants.complete;
    }
    /**
     * 已做过表单的表单名称和代码[去重]
     */
    public List<DoFormsVo> getDoForm(String brid) {
        String yljgid = UserUtils.getOrganId();
        List<DoFormsVo> DoFormsList = baseMapper.getDoForm(brid,yljgid);
        if(CollUtil.isNotEmpty(DoFormsList)){
            return DoFormsList;
        }else{
            List<DoFormsVo> list = new ArrayList<>();
            DoFormsVo doFormsVo = new DoFormsVo();
            list.add(doFormsVo);
            return list;
        }
    }

    /**
     * 入院评估单补充部分
     */
    public List<AdditionalInfoVo> getInHospital(String brid) {
        String yljgid = UserUtils.getOrganId();
        List<DicHljlwsVo> wsList = dicHljlwsMapper.getWsByWsfl(Constants.FXPG_WSFL,yljgid);
        List<FormsIndexVo> indexList = baseMapper.getList(brid, "", "", "", Constants.FXPG_WSFL,yljgid);
        Map<String, FormsIndexVo> indexMap = new HashMap<>();
        if(CollUtil.isNotEmpty(indexList)){
            for (FormsIndexVo item : indexList) {
                if (!indexMap.containsKey(item.getWsmc())) {
                    indexMap.put(item.getWsmc(), item);
                } else {
                    FormsIndexVo oldItem = indexMap.get(item.getWsmc());
                    if (oldItem.getRq().after(item.getRq())) {
                        indexMap.put(item.getWsmc(), item);
                    }
                }
            }
        }
        List<AdditionalInfoVo> list = new ArrayList<>();
        if(CollUtil.isNotEmpty(wsList)){
            for (DicHljlwsVo item : wsList){
                String key = item.getMc();
                AdditionalInfoVo additionalInfoVo = new AdditionalInfoVo();
                additionalInfoVo.setName(key);
                if(indexMap.containsKey(key)){
                    String jl = StrUtil.isNotBlank(indexMap.get(key).getJl()) ? indexMap.get(key).getJl() : "无结论";
                    additionalInfoVo.setResult(indexMap.get(key).getZf()+"分，"+jl);
                }
                list.add(additionalInfoVo);
            }
        }
        return list;
    }


    /**
     * 根据id获取入院告知信息
     * @param id
     * @return
     */
    public AdmissionFormVo getAdmissInfoById(String id) {
        if(StrUtil.isNotBlank(id)){
            return admissionFormMapper.getAdmissInfoById(id);
        }else {
            throw new ParameterException("入院告知单id不能为空");
        }
    }

}

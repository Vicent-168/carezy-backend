package com.shdata.health.carezy.panoramic.controller;

import cn.hutool.core.collection.CollUtil;
import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.carezy.forms.service.FormsIndexService;
import com.shdata.health.carezy.forms.vo.DicHljlwsVo;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import com.shdata.health.carezy.forms.vo.FormsSaveLineVo;
import com.shdata.health.carezy.panoramic.vo.PanoramicVo;
import com.shdata.health.carezy.patientmanage.entity.RegistInformation;
import com.shdata.health.carezy.patientmanage.service.LaboratoryService;
import com.shdata.health.carezy.patientmanage.service.RegistInformationService;
import com.shdata.health.carezy.patientmanage.service.TaskService;
import com.shdata.health.carezy.patientmanage.vo.*;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 全景护理接口
 *
 * @author: panwei
 * @Description:
 */
@RestController
@RequestMapping("panoramic")
public class PanoramicController {

    private static final Logger log = LoggerFactory.getLogger(PanoramicController.class);
    private final FormsIndexService formsIndexService;
    private final RegistInformationService registInformationService;
    private final TaskService taskService;
    private final LaboratoryService laboratoryService;

    public PanoramicController(FormsIndexService formsIndexService,
                               RegistInformationService registInformationService, TaskService taskService,
                               LaboratoryService laboratoryService) {
        this.formsIndexService = formsIndexService;
        this.registInformationService = registInformationService;
        this.taskService = taskService;
        this.laboratoryService = laboratoryService;
    }

    /**
     * 全景护理一览
     * todo 封装进service层
     **/
    @PostMapping("overview")
    public ResponseEntity<PanoramicVo> getPanoramic(@RequestBody LaboratorySearchVo v) {
        if (v.getBrid() == null) {
            throw new ParameterException("参数错误");
        }

        RegistInformation byId = registInformationService.getById(v.getBrid());
        if (byId == null) {
            throw new ParameterException("无效的病人信息");
        }
        String yljgid = UserUtils.getOrganId();
        PanoramicVo vo = new PanoramicVo();
        //体征-体温等\
        List<FormsSaveLineVo> tz1= new ArrayList<>();
        String wsid1 = formsIndexService.getWsidByylgjAndwsbm(v.getBrid(), "11", yljgid);
        if (wsid1 != null) {
            tz1 = formsIndexService.getList2(v.getBrid(), wsid1, null);
            //根据item.getRecordIndex().getUpdateTime()降序排序并且取出前7个
            if (CollUtil.isNotEmpty(tz1)) {
                tz1 = tz1.stream()
                        .sorted(Comparator.comparing(FormsSaveLineVo::getRq, Comparator.nullsLast(Comparator.naturalOrder())))
                        .limit(7)
                        .toList();
                FormsSaveLineVo formsSaveLineVo = tz1.get(tz1.size() - 1);

                Map<String, Object> detailMap1 = formsSaveLineVo.getDetailMap();
                if (detailMap1.containsKey("XM_WCSSY") && detailMap1.containsKey("XM_WCSZY")) {
                    vo.setXya(detailMap1.get("XM_WCSSY") + "/" + detailMap1.get("XM_WCSZY"));
                }else {
                    vo.setXya("/");
                }
                if (detailMap1.containsKey("XM_ZYZXYANG")) {
                    vo.setXyang((String) detailMap1.get("XM_ZYZXYANG"));
                }
            }
        }
        List<FormsSaveLineVo> tz2 = new ArrayList<>();
        //体征-出入量
        String wsid2 = formsIndexService.getWsidByylgjAndwsbm(v.getBrid(), "12", yljgid);
        if (wsid2 != null) {
            tz2 = formsIndexService.getList2(v.getBrid(), wsid2, null);
            if (CollUtil.isNotEmpty(tz2)) {
                tz2 = tz2.stream()
                        .sorted(Comparator.comparing(FormsSaveLineVo::getRq, Comparator.nullsLast(Comparator.naturalOrder())))
                        .limit(7)
                        .toList();
                FormsSaveLineVo formsSaveLineVo2 = tz2.get(tz2.size() - 1);
                Map<String, Object> detailMap2 = formsSaveLineVo2.getDetailMap();
                if (detailMap2.containsKey("XM_DBCS")) {
                    vo.setDb((String) detailMap2.get("XM_DBCS"));
                }
                if (detailMap2.containsKey("XM_XB")) {
                    vo.setXb((String) detailMap2.get("XM_XB"));
                }

            }
            //筛选出tz2的getrq为最近24小时的所有数据
            // 获取当前时间
            Date now = new Date();
            // 计算24小时前的时间
            Date twentyFourHoursAgo = new Date(now.getTime() - 24 * 60 * 60 * 1000);
            // 使用Stream API筛选出最近24小时内的数据
            List<FormsSaveLineVo> recentData = tz2.stream()
                    .filter(item -> item.getRq().after(twentyFourHoursAgo) && item.getRq().before(now))
                    .toList();

            double sumXM_CL = 0;
            double sumXM_RL = 0;

            // 遍历recentData列表，累加detailMap中对应key的值
            for (FormsSaveLineVo item : recentData) {
                Map<String, Object> detailMap = item.getDetailMap();
                if (detailMap != null) {
                    Object valueXM_CL = detailMap.get("XM_CL");
                    Object valueXM_RL = detailMap.get("XM_RL");
                    try {
                        if (valueXM_CL instanceof String) {
                            sumXM_CL += Double.parseDouble((String) valueXM_CL);
                        }

                        if (valueXM_RL instanceof String) {
                            sumXM_RL += Double.parseDouble((String) valueXM_RL);
                        }
                    }catch (NumberFormatException e){
                        log.error("无法将字符串转换为数字：" + e.getMessage());
                    }
                    finally {
                        sumXM_RL += 0;
                        sumXM_CL += 0;
                    }
                }
            }

            // 将总和转换为String类型
            String Out24 = String.valueOf(sumXM_CL);
            String In24 = String.valueOf(sumXM_RL);
            vo.setOut24(Out24);
            vo.setIn24(In24);
        }
        //风险评估
        List<FormsIndexVo> riskList = formsIndexService.getIndexInfo(v.getBrid(), Constants.WSFL_FXPG);
        // 使用Stream API进行处理
        Map<String, FormsIndexVo> latestByWsid = riskList.stream()
                .collect(Collectors.toMap(
                        FormsIndexVo::getWsid, // key映射函数，使用wsid作为key
                        form -> form, // value映射函数，直接使用当前的FormsIndexVo对象
                        (existing, replacement) -> {
                            // 当存在相同wsid的记录时，比较rq和updateTime，返回最新的记录
                            if (existing.getRq().before(replacement.getRq())) {
                                return replacement;
                            } else if (existing.getRq().equals(replacement.getRq()) && existing.getUpdateTime().before(replacement.getUpdateTime())) {
                                return replacement;
                            }
                            return existing;
                        }
                ));

// 将Map转换回List
        List<FormsIndexVo> latestFormsIndexVoList = new ArrayList<>(latestByWsid.values());
        //基本信息
        RegistInformationVo registInformationVo = registInformationService.getInfoByBrid(v.getBrid());
        //未完成任务清单
        List<TaskVo> taskList = taskService.getTodoTaskByBrid(v.getBrid());

//        PageData<LaboratoryVo> abnormalLaboratoryList = laboratoryService.getAbnormalLaboratoryList(v);
        vo.setBaseInfo(registInformationVo);
        vo.setVitalSigns1(tz1);
        vo.setRiskEvaluation(latestFormsIndexVoList);
        vo.setVitalSigns2(tz2);
        vo.setTaskList(taskList);
//        vo.setAbnormalLaboratory(abnormalLaboratoryList);
        return ResponseEntity.ok(vo);
    }


    /**
     * 全景 异常化验
     **/
    @PostMapping("abnormalLaboratoryList")
    public ResponseEntity<PageData<LaboratoryVo>> abnormalLaboratoryList(@RequestBody LaboratorySearchVo vo) {
        return ResponseEntity.ok(laboratoryService.getAbnormalLaboratoryList(vo));
    }

    /**
     * 更新患者状态
     **/
    @PostMapping("updatePatientStatus")
    public ResponseEntity<String> updatePatientStatus(@RequestBody RegistInformationSearchVo vo) {
        return ResponseEntity.ok(registInformationService.updatePatientStatus(vo));
    }

    /**
     * 获取当前机构文书导航
     **/
    @GetMapping("getWsList")
    public ResponseEntity<List<DicHljlwsVo>> getWsList(@RequestParam(value = "brid")String brid) {
        String organId = UserUtils.getOrganId();
        return ResponseEntity.ok(registInformationService.getWsList(organId, brid));
    }
}

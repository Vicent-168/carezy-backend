package com.shdata.health.carezy.patientmanage.controller;

import com.shdata.health.carezy.forms.vo.FormsSaveTipsVo;
import com.shdata.health.carezy.patientmanage.service.RegistInformationService;
import com.shdata.health.carezy.patientmanage.vo.*;
import com.shdata.health.common.base.PageData;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 患者管理
 *
 * @author panwei
 * @date 2024-10-21
 */
@Validated
@RestController
@RequestMapping("registInformation")
public class PatientManagementController {

    private final RegistInformationService registInformationService;

    public PatientManagementController(RegistInformationService registInformationService) {
        this.registInformationService = registInformationService;
    }

    /**
     * 通过ID查询患者信息
     */
    @GetMapping("get")
    public ResponseEntity<RegistInformationVo> get(@RequestParam("id") String id) {
        return ResponseEntity.ok(registInformationService.findById2(id));
    }

//    /**
//     * 通过ID逻辑删除病人登记信息
//     */
//    @GetMapping("delete")
//    public ResponseEntity<String> delete(@RequestParam("id") String id) {
//        return ResponseEntity.ok(registInformationService.deleteById(id));
//    }

    /**
     * 保存或者更新病人登记信息数据
     */
    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody @Valid RegistInformationVo vo) {
        return ResponseEntity.ok(registInformationService.saveYszdById(vo));
    }

    /**
     * 患者列表
     */
    @PostMapping("page")
    public ResponseEntity<PageData<RegistInformationListVo>> page(@RequestBody RegistInformationSearchVo vo) {
        return ResponseEntity.ok(registInformationService.findByPage(vo));
    }


    /**
     * 患者列表同步
     */
    @GetMapping("sync")
    public ResponseEntity<String> sync() {
        return ResponseEntity.ok(registInformationService.sync());
    }



    /**
     * 病床号（下拉列表）
     */
    @PostMapping("bchList")
    public ResponseEntity<RegistInformationBchVo> bchList() {
        return ResponseEntity.ok(registInformationService.findAll());
    }


    /**
     * 根据病床号和病区查询顶部病人信息
     */
    @GetMapping("findBybchList")
    public ResponseEntity<RegistInformationBchVo> findBybchList(String bch, String bqid) {
        return ResponseEntity.ok(registInformationService.findBybchList(bch, bqid));
    }

    /**
     * 获取当前用户的权限病区
     */
//    @PostMapping("findWardListByUserId")
//    public ResponseEntity<List<Dept>> findWardListByUserId() {
//        return ResponseEntity.ok(registInformationService.findWardListByUserId());
//    }

    /**
     * 批量查询患者的信息
     */
    @PostMapping("batchRetrievePatients")
    public ResponseEntity<List<RegistInformationListVo>> batchRetrievePatients(@RequestBody PatientSearchVo vo) {
        return ResponseEntity.ok(registInformationService.batchRetrievePatients(vo));
    }

    /**
     * 批量查询患者的生命体征信息2
     */
    @PostMapping("/batchRetrievePatientVitalSignInfos")
    public ResponseEntity<List<PatientVitalSignVo>> batchRetrievePatientVitalSignInfos(@RequestBody PatientVitalSignSearchVo vo) {
        return ResponseEntity.ok(registInformationService.batchRetrievePatientVitalSignInfos(vo));
    }

    /**
     * 批量查询患者的血糖信息2
     */
    @PostMapping("/batchRetrievePatientBloodGlucose")
    public ResponseEntity<List<PatientBloodGlucoseVo>> batchRetrievePatientBloodGlucose(@RequestBody PatientBloodGlucoseSearchVo vo) {
        return ResponseEntity.ok(registInformationService.batchRetrievePatientBloodGlucose(vo));
    }

    /**
     * 批量查询患者的生命体征
     */
    /* @PostMapping("batchRetrievePatientVitalSignsss")
    public ResponseEntity<List<PatientVitalSignInfosVo>> batchRetrievePatientVitalSignsss(@RequestBody VitalSignSearchFiltersVo vo) {
        return ResponseEntity.ok(registInformationService.batchRetrievePatientVitalSignsss(vo));
    } */


    /**
     * 批量查询患者的血糖数据
     */

    /* @PostMapping("batchBloodGlucoseQuery")
    public ResponseEntity<List<PatientBloodGlucoseDatasVo>> batchBloodGlucoseQuery(@RequestBody PatientBloodGlucoseBatchQueryParamsVo vo) {
        return ResponseEntity.ok(registInformationService.batchBloodGlucoseQuerys(vo));
    } */


    /**
     * 批量保存患者的生命体征
     */
    @PostMapping("batchSavePatientVitalSigns")
    public ResponseEntity<FormsSaveTipsVo> batchSavePatientVitalSigns(@RequestBody VitalSignsVo vo) {
        return ResponseEntity.ok(registInformationService.batchSavePatientVitalSigns(vo));
    }

    /**
     * 批量保存患者的血糖数据
     */
    @PostMapping("batchSaveBloodGlucoseQuery")
    public ResponseEntity<FormsSaveTipsVo> batchSaveBloodGlucoseQuery(@RequestBody BloodSugarMeasurementVo vo) {
        return ResponseEntity.ok(registInformationService.batchSaveBloodGlucoseQuery(vo));
    }


    /**
     * 护理告知列表
     */
    @PostMapping("careNotificationList")
    public ResponseEntity<PageData<NursingNoticeVo>> CareNotificationList(@RequestBody NursingNoticeSearchVo vo) {

        return ResponseEntity.ok(registInformationService.CareNotificationList(vo));
    }

    /**
     * 有创治疗列表
     */
    @PostMapping("/getInvasiveTreatmentList")
    public ResponseEntity<PageData<InvasiveTreatmentVo>> getInvasiveTreatmentList(@RequestBody InvasiveTreatmentSearchVo vo) {

        return ResponseEntity.ok(registInformationService.getInvasiveTreatmentList(vo));
    }

    /**
     * 护理任务列表
     */
    @PostMapping("/getNursingTaskList")
    public ResponseEntity<PageData<NursingTaskVo>> getNursingTaskList(@RequestBody NursingTaskSearchVo vo) {

        return ResponseEntity.ok(registInformationService.getNursingTaskList(vo));
    }


    /**
     * 健康宣教详情查询
     *//*
    @PostMapping("/getHealthEducationQuery")
    public ResponseEntity<HealthEducationVo> getHealthEducationQuery(@RequestBody HealthEducationSearchVo vo) {

        return ResponseEntity.ok(registInformationService.getHealthEducationQuery(vo));
    }
 */


    /**
     * 风险汇总
     */
    @PostMapping("/riskSummary")
    public ResponseEntity<PageData<RiskBaseInfo>> riskSummary(@RequestBody RegistInformationSearchVo vo) throws IllegalAccessException {

        return ResponseEntity.ok(registInformationService.riskSummary(vo));
    }
    /**
     * 风险汇总-统计
     */
    @PostMapping("/riskSummaryCounts")
    public ResponseEntity<List<RiskResVo>> riskSummaryCounts(@RequestBody RegistInformationSearchNoPageVo vo) {

        return ResponseEntity.ok(registInformationService.riskSummaryriskSummaryCounts(vo));
    }
//
//    /**
//     * 风险汇总-单风险
//     */
//    @PostMapping("/riskSummaryDetail")
//    public ResponseEntity<PageData<RegistInformationVo>> riskSummaryDetail(@RequestBody RegistInformationSearchVo vo) {
//
//        return ResponseEntity.ok(registInformationService.riskSummaryDetail(vo));
//    }


}

package com.shdata.health.carezy.forms.controller;

import com.shdata.health.carezy.forms.vo.*;
import com.shdata.health.carezy.forms.service.FormsIndexService;
import com.shdata.health.common.base.PageData;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 表单的历史记录
 *
 * @author myy
 * @date 2024-10-21
 */
@Validated
@RestController
@RequestMapping("zyformsIndex")
public class FormsIndexController {

    private final FormsIndexService formsIndexService;

    public FormsIndexController(FormsIndexService formsIndexService) {
        this.formsIndexService = formsIndexService;
    }

    /**
     * 根据brid获取历史索引列表
     */
    @GetMapping("/getIndexByBrid")
    public ResponseEntity<List<FormsIndexVo>> getIndex(@RequestParam(value = "brid")String brid, @RequestParam(value = "wsid", required = false)String wsid, @RequestParam(value = "rq", required = false) String rq, @RequestParam(value = "wsfl", required = false)String wsfl, @RequestParam(value = "wsbm", required = false)String wsbm){
        return ResponseEntity.ok(formsIndexService.getIndex(brid, wsid,rq,wsbm, wsfl));
    }
    /**
     * 根据主键ID获取历史索引+历史详情
     */
    @GetMapping("/getInfoById")
    public ResponseEntity<FormsSaveVo> getInfoById(@RequestParam(value = "id")String id, @RequestParam(value = "brid", required = false)String brid, @RequestParam(value = "wsid", required = false)String wsid, @RequestParam(value = "wsfl", required = false)String wsfl) throws Exception {
        return ResponseEntity.ok(formsIndexService.getInfoById(id, brid, wsid, wsfl));
    }
    /**
     * 根据brid获取历史索引+历史记录
     */
    @GetMapping("/getInfosByBrid")
    public ResponseEntity<List<FormsSaveVo>> getList(@RequestParam(value = "brid",required = false)String brid, @RequestParam(value = "wsid", required = false)String wsid, @RequestParam(value = "rq", required = false) String rq, @RequestParam(value = "wsfl", required = false)String wsfl, @RequestParam(value = "wsbm", required = false)String wsbm){
        return ResponseEntity.ok(formsIndexService.getList(brid, wsid,rq,wsbm, wsfl));
    }

    /**
     * 根据brid获取历史索引+历史记录[分页][护理列表]
     */
    @PostMapping("/getInfosByBridPage")
    public ResponseEntity<PageData<FormsSaveVo>> getListPage(@RequestBody FormsDetailSearchVo vo){
        return ResponseEntity.ok(formsIndexService.getListPage(vo));
    }
    /**
     * 根据brid生命体征报告查询
     */
    @GetMapping("/getVitalSigns")
    public ResponseEntity<VitalSignsFromsVo> getVitalSigns(@RequestParam(value = "brid")String brid, @RequestParam(value = "wsfl", required = false)String wsfl, @RequestParam(value = "wsid", required = false)String wsid, @RequestParam(value = "wsbm", required = false)String wsbm, @RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false)String endTime){
        return ResponseEntity.ok(formsIndexService.getVitalSigns(brid, wsid,wsbm, wsfl,startTime,endTime));
    }
    /**
     * 根据brid获取生命体征列表
     */
    @GetMapping("/getVitalSignsList")
    public ResponseEntity<List<VitalSignsListVo>> getVitalSignsList(@RequestParam(value = "brid")String brid, @RequestParam(value = "rq", required = false)String rq, @RequestParam(value = "wsfl",required = false)String wsfl, @RequestParam(value = "wsid", required = false)String wsid){
        return ResponseEntity.ok(formsIndexService.getVitalSignsList(brid, rq, wsid,wsfl));
    }
    /**
     * 根据主键ID删除历史记录
     */
    @GetMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id")String id) {
        return ResponseEntity.ok(formsIndexService.delete(id));
    }
    /**
     * 删除某天的表单记录
     */
    @GetMapping("/deleteByDate")
    public ResponseEntity<String> deleteByDate(@RequestParam(value = "brid")String brid, @RequestParam(value = "wsid")String wsid,@RequestParam(value = "date")String date,@RequestParam(value = "wsfl", required = false)String wsfl){
        return ResponseEntity.ok(formsIndexService.deleteByDate(brid, wsid,date,wsfl));
    }

    /**
     * 风险评估列表[全部]
     */
    @GetMapping("/getRiskList")
    public ResponseEntity<Map<String,List<RiskListVo>>> getRiskList(@RequestParam(value = "brid")String brid){
        return ResponseEntity.ok(formsIndexService.getRiskList(brid));
    }
    /**
     * 风险评估列表[最新]
     */
    @GetMapping("/getRiskListNew")
    public ResponseEntity<List<RiskListVo>> getRiskListNew(@RequestParam(value = "brid")String brid){
        return ResponseEntity.ok(formsIndexService.getRiskListNew(brid));
    }
    /**
     * 风险评估列表[今日]
     */
    @GetMapping("/getRiskListToday")
    public ResponseEntity<List<RiskListVo>> getRiskListToday(@RequestParam(value = "brid")String brid){
        return ResponseEntity.ok(formsIndexService.getRiskListToday(brid));
    }
    /**
     * 风险评估右侧的监控列表
     */
    @GetMapping("/getRiskListRight")
    public ResponseEntity<List<FormsIndexVo>> getRiskListRight(@RequestParam(value = "brid")String brid) {
        return ResponseEntity.ok(formsIndexService.getRiskListRight(brid));
    }
    /**
     * 健康宣教是否已做
     */
    @GetMapping("/getHealthExplain")
    public ResponseEntity<MarkedVo> getHealthExplain(@RequestParam(value = "brid")String brid){
        return ResponseEntity.ok(formsIndexService.getHealthExplain(brid));
    }

    /**
     * 停报时间
     */
    @GetMapping("/getStopTime")
    public ResponseEntity<String> getStopTime(@RequestParam(value = "brid")String brid, @RequestParam(value = "wsid")String wsid){
        return ResponseEntity.ok(formsIndexService.getStopTime(brid,wsid));
    }

    /**
     * 是否已做导管
     * 0未做，1已做
     */
    @GetMapping("/getDoDuct")
    public ResponseEntity<String> getDoDuct(@RequestParam(value = "brid")String brid) {
        return ResponseEntity.ok(formsIndexService.getDoDuct(brid));
    }

    /**
     * 已做过表单的表单名称和代码[去重]
     */
    @GetMapping("/getDoForm")
    public ResponseEntity<List<DoFormsVo>> getDoForm(@RequestParam(value = "brid")String brid){
        return ResponseEntity.ok(formsIndexService.getDoForm(brid));
    }

    /**
     * 入院评估单补充部分
     */
    @GetMapping("/getInHospital")
    public ResponseEntity<List<AdditionalInfoVo>> getInHospital(@RequestParam(value = "brid")String brid){
        return ResponseEntity.ok(formsIndexService.getInHospital(brid));
    }
    /**
     * 入院告知内容子表
     */
    @GetMapping("/getAdmissionForm")
    public ResponseEntity<AdmissionFormVo> getAdmissionForm(@RequestParam(value = "id")String id){
        return ResponseEntity.ok(formsIndexService.getAdmissInfoById(id));
    }

}

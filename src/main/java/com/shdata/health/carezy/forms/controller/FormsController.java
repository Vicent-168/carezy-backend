package com.shdata.health.carezy.forms.controller;

import com.shdata.health.carezy.forms.vo.*;
import com.shdata.health.carezy.forms.service.FormsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 表单的评估保存
 *
 * @author myy
 * @date 2024-10-21
 */
@Validated
@RestController
@RequestMapping("zyformsDetail")
public class FormsController {

    private final FormsService formsService;

    public FormsController(FormsService formsService) {
        this.formsService = formsService;
    }

    /**
     * 表单保存/更新/评估
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    public ResponseEntity<FormsSaveTipsVo> saveAndReview(@RequestBody FormsSaveVo vo){
        return ResponseEntity.ok(formsService.formsSave(vo));
    }

    /**
     * 量表评估预览
     */
    @PostMapping("preview")
    public ResponseEntity<PreviewResultVo> assessPreview(@RequestBody PreviewAssessVo previewAssessVo) throws Exception {
        return ResponseEntity.ok(formsService.assessPreview(previewAssessVo));
    }

    /**
     * 获取文书信息
     */
    @PostMapping("getWs")
    public ResponseEntity<List<DicHljlwsVo>> getWs(@RequestBody WsbmSearchVo vo){
        return ResponseEntity.ok(formsService.getWs(vo));
    }

    /**
     * 根据BQID获取护士长信息
     */
    @GetMapping("getNurse")
    public ResponseEntity<List<NurseVo>> getNurse(String bqid){
        return ResponseEntity.ok(formsService.getNurse(bqid));
    }

    /**
     * 根据文书分类获取文书信息
     */
    @GetMapping("getWsByWsfl")
    public ResponseEntity<List<DicHljlwsVo>> getWsByWsfl(String fldm){
        return ResponseEntity.ok(formsService.getWsByWsfl(fldm));
    }

}

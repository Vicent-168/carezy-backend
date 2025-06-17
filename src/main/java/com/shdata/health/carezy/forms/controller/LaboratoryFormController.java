package com.shdata.health.carezy.forms.controller;

import com.shdata.health.carezy.forms.service.LaboratoryFormService;
import com.shdata.health.carezy.forms.vo.LaboratoryFormDetasilsSearchVo;
import com.shdata.health.carezy.forms.vo.LaboratoryFormDetasilsVo;
import com.shdata.health.carezy.forms.vo.LaboratoryFormVo;
import com.shdata.health.common.base.PageData;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 化验指标的历史记录
 */
@RestController
@Validated
@RequestMapping("/laboratory")
public class LaboratoryFormController {
    private final LaboratoryFormService laboratoryFormService;

    public LaboratoryFormController(LaboratoryFormService laboratoryFormService) {
        this.laboratoryFormService = laboratoryFormService;
    }

    /**
     * 根据brid获取历史列表
     */
    @GetMapping("/getLaboratoryFormByBrid")
    public ResponseEntity<List<LaboratoryFormVo>> getLaboratoryFormByBrid(@RequestParam(value = "brid",required = false) String brid){
        return laboratoryFormService.getLaboratoryFormByBrid(brid);
    }

    /**
     * 根据主键ID[化验编号]获取化验历史详情[分页]
     */
    @PostMapping("/findDetailsByPage")
    public ResponseEntity<PageData<LaboratoryFormDetasilsVo>> findByPage(@RequestBody LaboratoryFormDetasilsSearchVo vo){
        return ResponseEntity.ok(laboratoryFormService.findByPage(vo));
    }

    /**
     * 根据主键ID[化验编号]获取详情和索引
     * @param id
     * @return
     */
    @GetMapping("/getLaboratoryFormById")
    public ResponseEntity<LaboratoryFormVo> getLaboratoryFormById(@RequestParam(value = "id",required = false) String id) {
        return laboratoryFormService.getLaboratoryFormById(id);
    }




}


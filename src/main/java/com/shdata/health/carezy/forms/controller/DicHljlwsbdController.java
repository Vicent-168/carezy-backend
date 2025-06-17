package com.shdata.health.carezy.forms.controller;

import com.shdata.health.carezy.forms.service.DicHljlwsbdService;
import com.shdata.health.carezy.forms.vo.DicHljlwsbdVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 表单的项目代码
 *
 * @author myy
 * @date 2024-10-21
 */
@Validated
@RestController
@RequestMapping("dichlzyjlwsbd")
public class DicHljlwsbdController {

    private final DicHljlwsbdService dicHljlwsbdService;

    public DicHljlwsbdController(DicHljlwsbdService dicHljlwsbdService) {
        this.dicHljlwsbdService = dicHljlwsbdService;
    }

    /**
     * 护理记录单文书表单字典列表
     */
    @GetMapping("getLists")
    public ResponseEntity<List<DicHljlwsbdVo>> findByWsid(@RequestParam String wsid, @RequestParam(required = false) String xmdm) {
        return ResponseEntity.ok(dicHljlwsbdService.findByWsid(wsid, xmdm));
    }

    /**
     * 护理记录单文书表单字典列表（分类）
     */
    @GetMapping("getListsByFl")
    public ResponseEntity<Map<String,List<DicHljlwsbdVo>>> findByFl(@RequestParam String wsid, @RequestParam(required = false) String xmdm) {
        return ResponseEntity.ok(dicHljlwsbdService.findByFl(wsid, xmdm));
    }


}

package com.shdata.health.carezy.forms.controller;

import com.shdata.health.carezy.forms.service.DicHljlwsxmService;
import com.shdata.health.carezy.forms.vo.DicHljlwsxmVo;
import com.shdata.health.common.dict.Dict;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 表单的项目明细
 *
 * @author myy
 * @date 2024-10-21
 */
@Validated
@RestController
@RequestMapping("dichlzyjlwsxm")
public class DicHljlwsxmController {

    private final DicHljlwsxmService dicHljlwsxmService;

    public DicHljlwsxmController(DicHljlwsxmService dicHljlwsxmService) {
        this.dicHljlwsxmService = dicHljlwsxmService;
    }

    /**
     * 获取表单的项目明细
     */
    @GetMapping("/get")
    public ResponseEntity<List<DicHljlwsxmVo>> getProject(@RequestParam String wsid, @RequestParam(required = false) String zdlx) {
        return ResponseEntity.ok(dicHljlwsxmService.getProject(wsid,zdlx));
    }

    /**
     * 获取表单的项目明细(分类)
     */
    @GetMapping("/getByFl")
    public ResponseEntity<Map<String,List<DicHljlwsxmVo>>> getProjectByFl(@RequestParam String wsid, @RequestParam(required = false) String zdlx) {
        return ResponseEntity.ok(dicHljlwsxmService.getProjectByFl(wsid,zdlx));
    }

    /**
     * 项目明细中文映射
     */
    @GetMapping("/getName")
    public ResponseEntity<Map<String, List<Dict>>> getName(@RequestParam String wsid, @RequestParam String types) {
        return ResponseEntity.ok(dicHljlwsxmService.getName(wsid,types));
    }



}

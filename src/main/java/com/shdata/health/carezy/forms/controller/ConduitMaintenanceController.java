package com.shdata.health.carezy.forms.controller;

import com.shdata.health.carezy.forms.service.ConduitMaintenanceService;
import com.shdata.health.carezy.forms.vo.ConduitMaintenanceVo;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 导管置管_导管维护表接口
 *
 * @author myy
 * @date 2024-11-20
 */
@Validated
@RestController
@RequestMapping("conduitMaintenance")
public class ConduitMaintenanceController {

    private final ConduitMaintenanceService conduitMaintenanceService;

    public ConduitMaintenanceController(ConduitMaintenanceService conduitMaintenanceService) {
        this.conduitMaintenanceService = conduitMaintenanceService;
    }
    /**
     * 根据brid获取导管维护列表
     */
    @GetMapping("getList")
    public ResponseEntity<List<ConduitMaintenanceVo>> getList(@RequestParam("brid") String brid) {
        return ResponseEntity.ok(conduitMaintenanceService.getList(brid));
    }

    /**
     * 通过ID查询导管维护表
     */
    @GetMapping("get")
    public ResponseEntity<ConduitMaintenanceVo> get(@RequestParam("id") String id) {
        return ResponseEntity.ok(conduitMaintenanceService.findById(id, ConduitMaintenanceVo.class));
    }

    /**
     * 通过ID逻辑删除导管维护记录
     */
    @GetMapping("delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        return ResponseEntity.ok(conduitMaintenanceService.deleteById(id));
    }

    /**
     * 保存或更新导管维护记录
     */
    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody @Valid ConduitMaintenanceVo vo) {
        return ResponseEntity.ok(conduitMaintenanceService.saveOrUpdate(vo));
    }

}

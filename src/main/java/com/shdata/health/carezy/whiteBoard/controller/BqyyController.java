package com.shdata.health.carezy.whiteBoard.controller;

import com.shdata.health.carezy.common.utils.UserUtils;
import com.shdata.health.carezy.whiteBoard.service.BqyyMkbService;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkbVo;
import com.shdata.health.carezy.whiteBoard.vo.TestQueryVo;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 白板大屏动态配置接口
 */
@Validated
@RestController
@RequestMapping("/bqyy")
public class BqyyController {

    private final BqyyMkbService mkbService;

    public BqyyController(BqyyMkbService mkbService) {
        this.mkbService = mkbService;
    }

    /**
     * 大屏白板布局获取接口
     * mklx:模块类型，多个模块以逗号隔开
     * 01	关键事件
     * 02	护理
     * 03	血压
     * 04	导管
     * 05	血糖
     * 06	风险评估
     * 07	今日待办
     * 08	病区状态概览
     * 09	在院患者性别与年龄
     * 10	护理工作量
     * 11	当前护理文件数
     * 12	每月入院人数趋势
     * 13	病区设备概览
     * 14	护理人员概况
     * 15	住院诊断 Top 10
     * 16	护理等级
     * 17	注意事项
     * 18	备忘录
     * 19	资质饼图
     */
    @GetMapping("/lgscreen")
    public ResponseEntity<List<BqyyMkbVo>> lgscreen(String mklx) {
        return ResponseEntity.ok(mkbService.findByOrganId(mklx, UserUtils.getOrganId()));
    }

    /**
     * 测试Bean功能
     */
    @PostMapping("/testQuery")
    public ResponseEntity<Map<String, String>> testQuery(@RequestBody TestQueryVo query) {
        return ResponseEntity.ok(mkbService.testQuery(query));
    }

}

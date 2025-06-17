package com.shdata.health.carezy.patientmanage.controller;

import com.shdata.health.common.base.PageData;
import com.shdata.health.carezy.patientmanage.service.TaskService;
import com.shdata.health.carezy.patientmanage.vo.TaskSearchVo;
import com.shdata.health.carezy.patientmanage.vo.TaskVo;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 住院护理任务接口
 *
 * @author panwei
 * @date 2024-10-23
 */
@Validated
@RestController
@RequestMapping("task")
public class

TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 通过ID查询住院护理任务VO
     */
    @GetMapping("get")
    public ResponseEntity<TaskVo> get(@RequestParam("id") String id) {
        return ResponseEntity.ok(taskService.findById(id, TaskVo.class));
    }

    /**
     * 通过ID逻辑删除住院护理任务
     */
    @GetMapping("delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        return ResponseEntity.ok(taskService.deleteById(id));
    }

    /**
     * 保存或者更新住院护理任务数据
     */
    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody @Valid TaskVo vo) {
        return ResponseEntity.ok(taskService.saveOrUpdate(vo));
    }

    /**
     * 查询住院护理任务分页数据
     */
    @PostMapping("page")
    public ResponseEntity<PageData<TaskVo>> page(@RequestBody TaskSearchVo vo) {
        return ResponseEntity.ok(taskService.findByPage(vo));
    }

}

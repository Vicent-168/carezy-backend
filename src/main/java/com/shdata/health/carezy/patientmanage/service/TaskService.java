package com.shdata.health.carezy.patientmanage.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.carezy.patientmanage.entity.Task;
import com.shdata.health.carezy.patientmanage.mapper.TaskMapper;
import com.shdata.health.carezy.patientmanage.vo.TaskSearchVo;
import com.shdata.health.carezy.patientmanage.vo.TaskVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.base.PageData;
import com.shdata.health.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 住院护理任务  Service服务
 *
 * @author panwei
 * @date 2024-10-23
 */
@Service
@Transactional(readOnly = true)
public class TaskService extends BaseService<TaskMapper,Task> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(TaskVo vo) {
        validate(vo);
        Task po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, Task.class);
            po.setId(IdUtil.objectId());
            po.init();
            save(po);
            return "保存成功";
        } else {
            BeanUtil.copyProperties(vo, po);
            po.initByUpdate();
            updateById(po);
            return "更新成功";
        }
    }

    /**
     * 验证对象
     *
     * @param vo 提交参数
     */
    private void validate(TaskVo vo) {
        if (vo == null) {
            throw new ParameterException("参数不能为空");
        }

    }

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return
     */
    public PageData<TaskVo> findByPage(TaskSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }


    /**
     * 根据brid查询待办任务
     *
     * @param brid
     * @return
     */
    public List<TaskVo> getTodoTaskByBrid(String brid) {
        List<TaskVo> todoTaskByBrid = baseMapper.getTodoTaskByBrid(brid);
        //保留任务计划开始时间小于等于当前时间的所有List
        return todoTaskByBrid.stream().filter(taskVo -> taskVo.getRwjhkssj().getTime() <= System.currentTimeMillis()).toList();

    }

}

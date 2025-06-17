package com.shdata.health.carezy.patientmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.patientmanage.entity.Task;
import com.shdata.health.carezy.patientmanage.vo.TaskSearchVo;
import com.shdata.health.carezy.patientmanage.vo.TaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 住院护理任务Mapper接口
 *
 * @author panwei
 * @date 2024-10-23
 */
@Mapper
@Repository
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<TaskVo> findByPage(TaskSearchVo search);

    List<TaskVo> getTodoTaskByBrid(String brid);

    List<TaskVo> getTaskByBridAndWsid(String brid,String wsid, String rwzt);

    void deleteByBridAndWsid(String brid, String wsid);

    void insertTasks(List<TaskVo> list);

    void updateBatch(List<TaskVo> list);
}

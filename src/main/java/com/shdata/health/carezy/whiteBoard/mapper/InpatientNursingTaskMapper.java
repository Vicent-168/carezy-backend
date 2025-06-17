package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.InpatientNursingTask;
import com.shdata.health.carezy.whiteBoard.vo.InpatientNursingTaskSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.InpatientNursingTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 住院护理任务表Mapper接口
 *
 * @author ljt
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface InpatientNursingTaskMapper extends BaseMapper<InpatientNursingTask> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<InpatientNursingTaskVo> findByPage(InpatientNursingTaskSearchVo search);
}

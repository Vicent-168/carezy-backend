package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.WardOperationsTasks;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationsTasksSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationsTasksVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 病区运营_大屏代办Mapper接口
 *
 * @author ljt
 * @date 2024-10-25
 */
@Mapper
@Repository
public interface WardOperationsTasksMapper extends BaseMapper<WardOperationsTasks> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<WardOperationsTasksVo> findByPage(WardOperationsTasksSearchVo search);

    /**
     * 根据日期分组查询
     */
    List<WardOperationsTasks> selectByRq(String organId, String ward, Date rq);
}

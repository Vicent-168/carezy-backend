package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.WardOperationSummary;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationSummarySearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationSummaryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 病区运营汇总表Mapper接口
 *
 * @author ljt
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface WardOperationSummaryMapper extends BaseMapper<WardOperationSummary> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<WardOperationSummaryVo> findByPage(WardOperationSummarySearchVo search);
}

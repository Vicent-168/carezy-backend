package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.WardLargeScreenSummary;
import com.shdata.health.carezy.whiteBoard.vo.WardLargeScreenSummarySearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardLargeScreenSummaryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 病区大屏汇总表Mapper接口
 *
 * @author ljt
 * @date 2024-10-17
 */
@Mapper
@Repository
public interface WardLargeScreenSummaryMapper extends BaseMapper<WardLargeScreenSummary> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<WardLargeScreenSummaryVo> findByPage(WardLargeScreenSummarySearchVo search);
}

package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.WardExtension;
import com.shdata.health.carezy.whiteBoard.vo.WardExtensionSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardExtensionVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 病区扩展表Mapper接口
 *
 * @author ljt
 * @date 2024-11-05
 */
@Mapper
@Repository
public interface WardExtensionMapper extends BaseMapper<WardExtension> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<WardExtensionVo> findByPage(WardExtensionSearchVo search);
}

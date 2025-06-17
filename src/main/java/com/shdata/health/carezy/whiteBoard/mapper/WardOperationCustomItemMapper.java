package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.WardOperationCustomItem;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationCustomItemSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.WardOperationCustomItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 病区运营_自定义项目表Mapper接口
 *
 * @author ljt
 * @date 2024-10-24
 */
@Mapper
@Repository
public interface WardOperationCustomItemMapper extends BaseMapper<WardOperationCustomItem> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<WardOperationCustomItemVo> findByPage(WardOperationCustomItemSearchVo search);
}

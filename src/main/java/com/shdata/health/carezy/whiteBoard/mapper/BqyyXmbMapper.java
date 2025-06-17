package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.BqyyXmb;
import com.shdata.health.carezy.whiteBoard.vo.BqyyXmbSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyXmbVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 病区运营项目表Mapper接口
 *
 * @author xgb
 * @date 2024-11-19
 */
@Mapper
@Repository
public interface BqyyXmbMapper extends BaseMapper<BqyyXmb> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<BqyyXmbVo> findByPage(BqyyXmbSearchVo search);

    Set<String> findAllYljgids();

    void updateBatch(List<BqyyXmb> list);
}

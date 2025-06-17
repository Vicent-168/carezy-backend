package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.BqyyDpsj;
import com.shdata.health.carezy.whiteBoard.vo.BqyyDpsjSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyDpsjVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 病区运营大屏数据表Mapper接口
 *
 * @author xgb
 * @date 2024-11-19
 */
@Mapper
@Repository
public interface BqyyDpsjMapper extends BaseMapper<BqyyDpsj> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<BqyyDpsjVo> findByPage(BqyyDpsjSearchVo search);

    BqyyDpsj findBy(@Param("yljgid") String yljgid, @Param("bqid") String bqid, @Param("mkxmid") String mkxmid, @Param("rq") Date rq, @Param("sd") String sd);
}

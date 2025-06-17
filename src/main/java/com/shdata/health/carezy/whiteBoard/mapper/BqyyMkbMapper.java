package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.BqyyMkb;
import com.shdata.health.carezy.whiteBoard.entity.MkMkxmSj;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkbSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkbVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 病区运营模块表Mapper接口
 *
 * @author xgb
 * @date 2024-11-19
 */
@Mapper
@Repository
public interface BqyyMkbMapper extends BaseMapper<BqyyMkb> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<BqyyMkbVo> findByPage(BqyyMkbSearchVo search);

    List<BqyyMkb> findByOrganId(@Param("organId") String organId, @Param("mklxs") Set<String> mklxs);

    List<MkMkxmSj> selectDefaultMkAndSj(List<String> mklxList, @Param("bqid") String ward);

    List<MkMkxmSj> selectDefaultMkXmSj(List<String> mklxList, @Param("organId") String organId);

    List<MkMkxmSj> selectMkAndSjByOrganId(String organId, List<String> mklxList,@Param("bqid") String ward);

    void updateBatch(List<BqyyMkb> list);

    List<MkMkxmSj> selectLastDefaultMkXmSj(String organId, @Param("mklxs") List<String> moduleTypeList, String ward);
}

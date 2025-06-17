package com.shdata.health.carezy.whiteBoard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.whiteBoard.entity.BqyyMkxmb;
import com.shdata.health.carezy.whiteBoard.entity.MkMkxmSj;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkXmVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkxmbSearchVo;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkxmbVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 病区运营模块项目表Mapper接口
 *
 * @author xgb
 * @date 2024-11-19
 */
@Mapper
@Repository
public interface BqyyMkxmbMapper extends BaseMapper<BqyyMkxmb> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<BqyyMkxmbVo> findByPage(BqyyMkxmbSearchVo search);

    List<BqyyMkxmbVo> findByMkdIds(@Param("mkdIds") List<String> mkdIds);

    List<BqyyMkXmVo> findByMkIds(@Param("mkIds") List<String> mkIds);

    BqyyMkXmVo findDefualtByBean(@Param("bean") String bean);

    List<BqyyMkxmb> selectByMkIdAndOrganId(@Param("organId") String organId);

    List<BqyyMkXmVo> selectByOrganId(@Param("organId") String organId, List<String> moduleTypeList);

    void updateBatch(List<BqyyMkxmb> list);

    List<MkMkxmSj> selectDefaultMkxm(@Param("mklxs") List<String> moduleTypeList);
}

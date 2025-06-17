package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.DicHljlws;
import com.shdata.health.carezy.forms.vo.DicHljlwsSearchVo;
import com.shdata.health.carezy.forms.vo.DicHljlwsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 护理记录单文书字典Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface DicHljlwsMapper extends BaseMapper<DicHljlws> {


    /**
     * 通过文书编码获取文书名称
     *
     * @param wsbm 文书编码
     * @return
     */
    String getMcBywsbm(@Param("wsbm") String wsbm,String yljgid);

    /**
     * 通过文书编码获取文书名称
     *
     * @param wsbm 文书编码
     * @return
     */
    String getwsidDictBywsbm(@Param("wsbm") String wsbm, @Param("yljgid") String yljgid);
    /**
     * 通过文书id获取文书分类
     *
     * @param wsid 文书编码
     * @return
     */
    DicHljlwsVo getwsflBywsid(@Param("wsid")String wsid);

    List<DicHljlwsVo> getWsByWsfl(@Param("wsfl") String wsfl,String yljgid);

    List<DicHljlwsVo> getriskFormDictList(String wsflFxpg, String organId);
}

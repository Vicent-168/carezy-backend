package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shdata.health.carezy.forms.entity.DicHljlwsbd;
import com.shdata.health.carezy.forms.vo.DicHljlwsbdVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 护理记录单文书表单字典Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface DicHljlwsbdMapper extends BaseMapper<DicHljlwsbd> {

    List<DicHljlwsbdVo> findByWsid(String wsid, String xmdm);
}

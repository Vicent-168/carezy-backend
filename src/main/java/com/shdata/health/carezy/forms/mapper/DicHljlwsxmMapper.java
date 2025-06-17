package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shdata.health.carezy.forms.entity.DicHljlwsxm;
import com.shdata.health.carezy.forms.vo.DicHljlwsxmVo;
import com.shdata.health.common.dict.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 护理记录单文书项目字典Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface DicHljlwsxmMapper extends BaseMapper<DicHljlwsxm> {

    List<DicHljlwsxmVo> getProject(String wsid, String zdlx);

    List<Dict> findByType(String wsid,String type);
}

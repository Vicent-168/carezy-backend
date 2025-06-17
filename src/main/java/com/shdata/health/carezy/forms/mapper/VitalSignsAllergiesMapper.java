package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.VitalSignsAllergies;
import com.shdata.health.carezy.forms.vo.VitalSignsAllergiesSearchVo;
import com.shdata.health.carezy.forms.vo.VitalSignsAllergiesVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 生命体征_过敏表Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface VitalSignsAllergiesMapper extends BaseMapper<VitalSignsAllergies> {

    /**
     * 保存生命体征_过敏表
     * @return
     */
    void insertVitalSignsAllergiesList(List<VitalSignsAllergies> list);


    List<VitalSignsAllergiesVo> getVitalSignsAllergies(String id);

    boolean deleteVitalSignsAllergies(List<String> list);
}

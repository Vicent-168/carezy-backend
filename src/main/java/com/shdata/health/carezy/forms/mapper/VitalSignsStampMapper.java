package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.VitalSignsAllergies;
import com.shdata.health.carezy.forms.entity.VitalSignsStamp;
import com.shdata.health.carezy.forms.vo.VitalSignsAllergiesVo;
import com.shdata.health.carezy.forms.vo.VitalSignsStampSearchVo;
import com.shdata.health.carezy.forms.vo.VitalSignsStampVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 生命体征_图章表Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface VitalSignsStampMapper extends BaseMapper<VitalSignsStamp> {

    /**
     * 保存生命体征_图章表
     * @return
     */
    void insertVitalSignsStampList(List<VitalSignsStamp> list);


    List<VitalSignsStampVo> getVitalSignsStamp(String id);

    boolean deleteVitalSignsStamp(List<String> list);
}

package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.EventOccurrence;
import com.shdata.health.carezy.forms.entity.RequestConsultation;
import com.shdata.health.carezy.forms.vo.EventOccurrenceVo;
import com.shdata.health.carezy.forms.vo.RequestConsultationSearchVo;
import com.shdata.health.carezy.forms.vo.RequestConsultationVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 请求会诊表Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface RequestConsultationMapper extends BaseMapper<RequestConsultation> {

    void insertRequestConsultationList(List<RequestConsultation> list);


    List<RequestConsultationVo> getRequestConsultation(String id);

    boolean deleteRequestConsultation(List<String> list);
}

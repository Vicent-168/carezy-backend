package com.shdata.health.carezy.forms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shdata.health.carezy.forms.entity.EventOccurrence;
import com.shdata.health.carezy.forms.entity.VitalSignsAllergies;
import com.shdata.health.carezy.forms.vo.EventOccurrenceSearchVo;
import com.shdata.health.carezy.forms.vo.EventOccurrenceVo;
import com.shdata.health.carezy.forms.vo.VitalSignsAllergiesVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 事件发生表Mapper接口
 *
 * @author myy
 * @date 2024-10-21
 */
@Mapper
@Repository
public interface EventOccurrenceMapper extends BaseMapper<EventOccurrence> {

    void insertEventOccurrenceList(List<EventOccurrence> list);


    List<EventOccurrenceVo> getEventOccurrence(String id);

    boolean deleteEventOccurrence(List<String> list);
}

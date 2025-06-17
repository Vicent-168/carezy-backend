package com.shdata.health.carezy.forms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单索引
     */
    private FormsIndexVo recordIndex;

    /**
     * 表单明细
     */
    private List<FormsDetailVo> detailList;

    /**
     * 入院告知内容表
     */
    private List<AdmissionFormVo> admissionFormList;
    /**
     * 生命体征_过敏表
     */
    private List<VitalSignsAllergiesVo> vitalSignsAllergiesList;
    /**
     * 生命体征_图章表
     */
    private List<VitalSignsStampVo> vitalSignsStampsList;
    /**
     * 请求会诊表
     */
    private List<RequestConsultationVo> requestConsultationList;
    /**
     * 事件发生表
     */
    private List<EventOccurrenceVo> eventOccurrenceList;

    //ToDO 其它table类型的项目

}

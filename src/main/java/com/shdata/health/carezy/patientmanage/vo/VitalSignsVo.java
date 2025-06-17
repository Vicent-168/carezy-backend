package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 批量保存生命提振对象  VO
 *
 * @author dwt
 * @date 2024-11-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VitalSignsVo implements Serializable {
    /**
     *病区id
     * */
    private String bqid;

    /** 日期 日期格式:yyyy-MM-dd **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @NotBlank
    private Date xmClsj;
    /** 时间阶段
     *
     * */
    @JsonProperty("xmSjjd")
    private String xmSjjd;

    /** 项目测量体温部位 */
    @JsonProperty("xmCltwbw")
    private String xmCltwbw;
    /** 生命体征指标数据
     *
     * */
    private List<PatientVitalSignInfosVo> patientVitalSignInfosVos;

}

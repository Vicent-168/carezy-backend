package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 生命体征_过敏表  VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VitalSignsAllergiesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /** 病人文书索引唯一键 */
    @NotBlank
    private String blwsid;
    /** 测量日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date clrq;
    /** 测量时间段 */
    private String clsj;
    /** 过敏类型 */
    private String gmlx;
    /** 阴阳性 */
    private String yyx;
    /** 反应描述 */
    private String fyms;
}

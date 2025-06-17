package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 请求会诊表  VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RequestConsultationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    @NotBlank
    private String wsid;
    /**文书编码**/
    @NotBlank
    private String wsbm;
    /** 病人文书索引唯一键 */
    private String blwsid;
    /** 记录时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date jlsj;
    /** 申请时间 */
    private String sqsj;
    /** 发起病区 */
    private String fqbq;
    /** 请求对象 */
    private String qqdx;
    /** 请求目的 */
    private String qqmd;
    /** 患者情况 */
    private String hzqk;
    /** 主要诊断 */
    private String zyzd;
    /** 因何会诊 */
    private String yhhz;
    /** 会诊目的 */
    private String hzmd;
    /** 会诊时间 */
    private String hzsj;
    /** 会诊意见 */
    private String hzyj;
    /** 会诊医生 */
    private String hzys;
}

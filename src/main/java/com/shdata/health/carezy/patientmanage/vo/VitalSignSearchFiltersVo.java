package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 病人生命体征搜索VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VitalSignSearchFiltersVo {
    /** 患者筛选类型 */
    private String hzzt;
    /** 病区id */
    private String bqid;
    /** 时间 */
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sj;
    /** 护士id */
    private String hsid;
    /** 医疗机构id */
    private String yljgid;
    /** 文书编码 */
    private String wsbm;
    /** 体温 */
    private String tw;
    /** 脉搏 */
    private String mb;
    /** 心率 */
    private String xl;
    /** 呼吸 */
    private String hx;
    /** 收缩压 */
    private String ssy;
    /** 舒张压 */
    private String szy;
    /** 血氧 */
    private String xy;


}

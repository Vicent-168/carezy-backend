package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 病人血糖搜索VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientBloodGlucoseBatchQueryParamsVo {
    /** 患者状态 */
    private String hzzt;
    /** 病区 */
    private String bqid;
    /** 护士id */
    private String hsid;

    /** 时间 */
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sj;
    /** 时间阶段
     * 1  '02:00
     * 2 '06:00
     * 3  '10:00
     * 4  '14:00
     * 5  '18:00
     * 6   '22:00
     * */
    @JsonProperty("XM_SJJD")
    private String XM_SJJD;
    /** 标本类型
     * 1表示 指末血
     * 2表示 尿液
     * */
    // @JsonProperty("XM_BB")
    // private String XM_BB;
    /** 测量时段
     * 1 表示 凌晨
     * 2 表示 早饭前
     * 3 表示 早饭后
     * 4 表示 午餐前
     * 5 表示 午餐后
     * 6 表示 晚餐前
     * 7 表示 晚餐后
     * 8 表示 睡前
     * 9 表示 随机
     * */
    // @JsonProperty("XM_CLSD")
    // private String XM_CLSD;
    /** 医疗机构id */
    private String yljgid;
    /** 文书编码 */
    private String wsbm;
}

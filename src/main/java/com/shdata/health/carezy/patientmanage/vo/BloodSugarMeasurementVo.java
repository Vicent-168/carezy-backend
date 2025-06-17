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
 * 批量保存血糖对象  VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BloodSugarMeasurementVo implements Serializable {

    /**
     *病区id
     * */
    private String bqid;

    /** 测量时间 日期格式:yyyy-MM-dd   */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @NotBlank
    private Date xmClsj;
    /** 时间阶段
     *  2:00
     *  6:00
     *  10:00
     *  14:00
     *  18:00
     *  22:00
     * */
    @JsonProperty("xmSjjd")
    private String xmSjjd;
    /** 测量标本
     *  1 血液
     *  2 尿液
     * */
    @JsonProperty("xmBb")
    private String xmBb;

    /** 测量时段
     *  1 表示 凌晨
     *  2 表示 早饭前
     *  3 表示 早饭后
     *  4 表示 午餐前
     *  5 表示 午餐后
     *  6 表示 晚餐前
     *  7 表示 晚餐后
     *  8 表示 睡前
     *  9 表示 随机
     * */
    @JsonProperty("xmClsd")
    private String xmClsd;
    /**
     *病人血糖指标数据
     * */
    private List<BloodSugarRecordVo> bloodSugarRecordVos;

}

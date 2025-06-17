package com.shdata.health.carezy.patientmanage.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 健康宣教搜索  VO
 *
 * @author dingwentao
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HealthEducationSearchVo {
    /** 病床号 */
    @NotBlank
    private String bch;
    /** 病区id */
    @NotBlank
    private String bqid;
    /** 护士id */
    private String hsid;
    /** 医疗机构id */
    private String yljgid;
}

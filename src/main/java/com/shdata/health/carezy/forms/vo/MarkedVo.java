package com.shdata.health.carezy.forms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MarkedVo {
    /**
     * 入院宣教
     */
    private String admissionEducation;

    /**
     * 住院宣教
     */
    private String hospitalEducation;

    /**
     * 卫生宣教
     */
    private String healthEducation;

    /**
     * 出院宣教
     */
    private String dischargeEducation;
}

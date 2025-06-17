package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月17日 17:28
 */
@Data
public class PatientRequestInputVo {
    /**
     * 患者筛选 00:全部 01:新入 09:危重
     */
    private String patientFilter;
    /**
     * 病区id
     */
    private String ward;
}

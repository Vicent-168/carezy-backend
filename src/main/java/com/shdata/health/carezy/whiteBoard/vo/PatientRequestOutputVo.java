package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月17日 17:28
 */
@Data
public class PatientRequestOutputVo {
    /**
     * 病人id
     */
    private String brid;
    /**
     * 床号
     */
    private String bedNumber;
    /**
     * 住院号
     */
    private String admissionNumber;
    /**
     * 姓名
     */
    private String name;
    /**
     * 过敏
     */
    private String allergy;
}

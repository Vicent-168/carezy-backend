package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 健康宣教  VO
 *
 * @author dingwentao
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HealthEducationVo {
    /**
     * 姓名
     */
    private String xm;
    /**
     * 诊断id
     */
    private String yzdid;
    /**
     * 过敏
     */
    private String gm;

    /**
     * 入院宣教
     */
    private PatientAdmissionGuidanceVo patientAdmissionGuidanceVo;
    /**
     * 住院宣教
     */
    private InpatientEducationVo inpatientEducationVo;
    /**
     * 卫生宣教
     */
    private HygieneEducationVo hygieneEducationVo;
    /**
     * 出院宣教
     */
    private DischargeEducationVo dischargeEducationVo;
    /**
     * 宣教历史记录
     */
    private List<InstructionHistoryVo> instructionHistoryList;


}

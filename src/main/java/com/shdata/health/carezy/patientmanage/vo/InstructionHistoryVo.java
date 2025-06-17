package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 宣教历史  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InstructionHistoryVo {
    /** 日期 */
    private String rq;
    /** 宣教项目 */
    private List<MissionPropagatorVo> missionPropagatorList;
    /** 评估人 */
    private String hsid;

}

package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 汇总  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SummaryVo {
    /** 压力性损伤风险因素 */
    private String ylxssfxys;
    /** 压力性损伤发生 */
    private String ylxssfs;
    /** 跌倒坠床风险因素 */
    private String ddzcssfxys;
    /** 跌倒坠床风险发生 */
    private String ddzcssfs;
    /** 导管风险因素 */
    private String dgfxys;
    /** 导管风险发生 */
    private String dgfxfx;

}

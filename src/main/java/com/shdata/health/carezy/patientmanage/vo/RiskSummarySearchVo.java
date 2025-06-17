package com.shdata.health.carezy.patientmanage.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 风险汇总  搜索VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RiskSummarySearchVo extends PageSearch<RiskSummaryVo> {

    /** 患者筛选类型 */
    private List<String> hzxxs;
    /** 病区 */
    private List<String> bqids;
    /** 护士id */
    private String hsid;
    /** 压力性损伤高危极高危 */
    private List<String> ylxssfxys;
    /** 压力性损伤发生 */
    private List<String> ylxssfs;
    /** 跌倒坠床高危极高危 */
    private List<String> ddzcssfxys;
    /** 跌倒坠床发生事件*/
    private List<String> ddzcssfs;
    /** 导管风险因素高危极高危 */
    private List<String> dgfxys;
    /** 导管风险发生 */
    private List<String> dgfxfx;

}

package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 出院宣教  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DischargeEducationVo {
    /** 宣教时间 */
    private String XM_XJSJ;
    /** 时间阶段 */
    private String XM_SJJD;
    /** 疾病保健知识_宣教方式 */
    private String XM_JBBJZS_XJFS;
    /** 疾病保健知识_效果评价 */
    private String XM_JBBJZS_XGPJ;
    /** 活动、休息、康复知识_宣教方式 */
    private String XM_HDXXKFZS_XJFS;
    /** 活动、休息、康复知识_效果评价 */
    private String XM_HDXXKFZS_XGPJ;
    /** 正确用药知识_宣教方式 */
    private String XM_ZQYYZS_XJFS;
    /** 正确用药知识_效果评价 */
    private String XM_ZQYYZS_XGPJ;
    /** 饮食、营养知识_宣教方式 */
    private String XM_YSYYZS_XJFS;
    /** 饮食、营养知识_效果评价 */
    private String XM_YSYYZS_XGPJ;
    /** 定期复查、随访知识_宣教方式 */
    private String XM_DQFCSFZS_XJFS;
    /** 定期复查、随访知识_效果评价 */
    private String XM_DQFCSFZS_XGPJ;
    /** 护士签字 */
    private String XM_HSQZ;
    /** 患者签字 */
    private String XM_HZQZ;
}

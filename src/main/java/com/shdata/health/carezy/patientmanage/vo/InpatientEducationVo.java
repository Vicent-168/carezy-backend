package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 住院宣教  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InpatientEducationVo {
    /** 宣教时间 */
    private String XM_XJSJ;
    /** 时间阶段 */
    private String XM_SJJD;
    /** 护理计划交流_宣教方式 */
    private String XM_HLJHJL_XJFS;
    /** 护理计划交流_效果评价 */
    private String XM_HLJHJL_XGPJ;
    /** 疾病治疗、护理、康复相关知识_宣教方式 */
    private String XM_JBZL_XJFS;
    /** 疾病治疗、护理、康复相关知识_效果评价 */
    private String XM_JBZL_XGPJ;
    /** 陪客制度_宣教方式 */
    private String XM_PKZD_XJFS;
    /** 陪客制度_效果评价 */
    private String XM_PKZD_XGPJ;
    /** 病区联系方法和电话号码_宣教方式 */
    private String XM_BQLXFS_XJFS;
    /** 病区联系方法和电话号码_效果评价 */
    private String XM_BQLXFS_XGPJ;
    /** 护士签字 */
    private String XM_HSQZ;
    /** 患者签字 */
    private String XM_HZQZ;
}

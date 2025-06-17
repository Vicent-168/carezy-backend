package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 卫生宣教  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HygieneEducationVo {

    /** 宣教时间 */
    private String XM_XJSJ;
    /** 时间阶段 */
    private String XM_SJJD;
    /** 心理卫生知识_宣教方式*/
    private String XM_XLWSZS_XJFS;
    /** 心理卫生知识_效果评价 */
    private String XM_XLWSZS_XGPJ;
    /** 疾病保健知识_宣教方式 */
    private String XM_JBBJZS_XJFS;
    /** 疾病保健知识_效果评价 */
    private String XM_JBBJZS_XGPJ;
    /** 饮食方式、目的、注意事项_宣教方式 */
    private String XM_YSFSMDZYSX_XJFS;
    /** 饮食方式、目的、注意事项_效果评价 */
    private String XM_YSFSMDZYSX_XGPJ;
    /** 功能锻炼_宣教方式 */
    private String XM_GNDL_XJFS;
    /** 功能锻炼_效果评价 */
    private String XM_GNDL_XGPJ;
    /** 护士签字 */
    private String XM_HSQZ;
    /** 患者签字 */
    private String XM_HZQZ;
}

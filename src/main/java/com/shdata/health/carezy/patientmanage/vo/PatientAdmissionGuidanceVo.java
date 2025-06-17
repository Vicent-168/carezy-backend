package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *   入院宣教 VO
 *
 * @author dingwentao
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientAdmissionGuidanceVo {
    /** 宣教时间 */
    private String XM_XJSJ;
    /** 时间阶段 */
    private String XM_SJJD;
    /** 热情接待，主动介绍床位医生和责任护士_宣教方式 */
    private String XM_RQJD_XJFS;
    /** 热情接待，主动介绍床位医生和责任护士_效果评价 */
    private String XM_RQJD_XGPJ;
    /** 医院规章制度_宣教方式 */
    private String XM_YYGZZD_XJFS;
    /** 医院规章制度_效果评价 */
    private String XM_YYGZZD_XGPJ;
    /** 陪客制度_宣教方式 */
    private String XM_PKZD_XJFS;
    /** 陪客制度_效果评价 */
    private String XM_PKZD_XGPJ;
    /** 病区联系方式和电话号码_宣教方式 */
    private String XM_PQLXFS_XJFS;
    /** 病区联系方式和电话号码_效果评价 */
    private String XM_PQLXFS_XGPJ;
    /** 护士签字 */
    private String XM_HSQZ;
    /** 患者签字 */
    private String XM_HZQZ;
}

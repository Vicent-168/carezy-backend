package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病区运营汇总表  VO
 *
 * @author ljt
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WardOperationSummaryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 医疗机构ID */
    @NotBlank
    private String yljgid;
    /** 病区ID */
    @NotBlank
    private String bqid;
    /** 服务总人数 */
    private long fwzrs;
    /** 当前在院人数 */
    private long dqzyrs;
    /** 出院人数 */
    private long cyrs;
    /** 再入院人数 */
    private long zryrs;
    /** 床位总数 */
    private long cwzs;
    /** 已用床位 */
    private long yycw;
    /** 空闲床位 */
    private long kxcw;
    /** 护理文件总数 */
    private long hlwjzs;
    /** 平均每日护理文件数 */
    private long pjmrhlwjs;
    /** 护理风险评估数 */
    private long hlfxpgs;
    /** 当前护理任务数 */
    private long dqhlrws;
    /** 导管数 */
    private long dgs;
    /** 入院评估数 */
    private long rypgs;
    /** 生命体征数量 */
    private long smtzsl;
    /** 风险评估数 */
    private long fxpgs;
    /** 健康宣教数 */
    private long jkxjs;
    /** 行业报告 */
    private long hybg;
    /** 转载文章 */
    private long zzwz;
    /** 国外网站 */
    private long gwwz;
    /** UGC原创 */
    private long ugcyc;
    /** 其他 */
    private long qt;
}

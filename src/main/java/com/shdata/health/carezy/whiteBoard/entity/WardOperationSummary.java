package com.shdata.health.carezy.whiteBoard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 病区运营汇总表实体 对应表名T_HL_DP_BQYY
 *
 * @author ljt
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQYY")
public class WardOperationSummary extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 医疗机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 病区ID */
    @TableField("BQID")
    private String bqid;
    /** 服务总人数 */
    @TableField("FWZRS")
    private long fwzrs;
    /** 当前在院人数 */
    @TableField("DQZYRS")
    private long dqzyrs;
    /** 出院人数 */
    @TableField("CYRS")
    private long cyrs;
    /** 再入院人数 */
    @TableField("ZRYRS")
    private long zryrs;
    /** 床位总数 */
    @TableField("CWZS")
    private long cwzs;
    /** 已用床位 */
    @TableField("YYCW")
    private long yycw;
    /** 空闲床位 */
    @TableField("KXCW")
    private long kxcw;
    /** 护理文件总数 */
    @TableField("HLWJZS")
    private long hlwjzs;
    /** 平均每日护理文件数 */
    @TableField("PJMRHLWJS")
    private long pjmrhlwjs;
    /** 护理风险评估数 */
    @TableField("HLFXPGS")
    private long hlfxpgs;
    /** 当前护理任务数 */
    @TableField("DQHLRWS")
    private long dqhlrws;
    /** 导管数 */
    @TableField("DGS")
    private long dgs;
    /** 入院评估数 */
    @TableField("RYPGS")
    private long rypgs;
    /** 生命体征数量 */
    @TableField("SMTZSL")
    private long smtzsl;
    /** 风险评估数 */
    @TableField("FXPGS")
    private long fxpgs;
    /** 健康宣教数 */
    @TableField("JKXJS")
    private long jkxjs;
    /** 行业报告 */
    @TableField("HYBG")
    private long hybg;
    /** 转载文章 */
    @TableField("ZZWZ")
    private long zzwz;
    /** 国外网站 */
    @TableField("GWWZ")
    private long gwwz;
    /** UGC原创 */
    @TableField("UGCYC")
    private long ugcyc;
    /** 其他 */
    @TableField("QT")
    private long qt;
}

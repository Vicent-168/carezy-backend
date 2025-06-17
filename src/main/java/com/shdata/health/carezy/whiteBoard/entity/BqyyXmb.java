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
 * 病区运营项目表实体 对应表名T_HL_DP_BQYY_XMB
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQYY_XMB")
public class BqyyXmb extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 项目名称 */
    @TableField("XMMC")
    private String xmmc;
    /** 计算BEAN */
    @TableField("BEAN")
    private String bean;
    /** 参数 */
    @TableField("CS")
    private String cs;
    /** 刷新间隔 */
    @TableField("SXJG")
    private long sxjg;
    /** 医疗机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 单位 */
    @TableField("DW")
    private String dw;
}

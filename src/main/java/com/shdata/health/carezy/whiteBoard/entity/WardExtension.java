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
 * 病区扩展表实体 对应表名T_HL_DIC_BQKZB
 *
 * @author ljt
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DIC_BQKZB")
public class WardExtension extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病区ID */
    @TableField("BQID")
    private String bqid;
    /** 病区总床位数 */
    @TableField("BQZCWS")
    private String bqzcws;
    /** 病区护士长 */
    @TableField("BQHSZ")
    private String bqhsz;
    /** 病区副护士长 */
    @TableField("BQFHSZ")
    private String bqfhsz;
}

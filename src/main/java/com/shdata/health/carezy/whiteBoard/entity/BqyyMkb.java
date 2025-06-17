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
 * 病区运营模块表实体 对应表名T_HL_DP_BQYY_MKB
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQYY_MKB")
public class BqyyMkb extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 医疗机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 模块类型 */
    @TableField("MKLX")
    private String mklx;
    /** 模块名称 */
    @TableField("MKMC")
    private String mkmc;
}

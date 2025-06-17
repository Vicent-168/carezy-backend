package com.shdata.health.carezy.whiteBoard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * 病区运营大屏数据表实体 对应表名T_HL_DP_BQYY_DPSJ
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQYY_DPSJ")
public class BqyyDpsj extends BaseEntity {
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
    /** 日期 */
    @TableField("RQ")
    private Date rq;
    /** 时段 */
    @TableField("SD")
    private String sd;
    /** 模块项目ID */
    @TableField("MKXMID")
    private String mkxmid;
    /** 项目值 */
    @TableField("XMZ")
    private String xmz;
    /** 项目值1 */
    @TableField("XMZ1")
    private String xmz1;
    /** 项目值2 */
    @TableField("XMZ2")
    private String xmz2;
    /** 项目值3 */
    @TableField("XMZ3")
    private String xmz3;
}

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
 * 病区运营_大屏代办实体 对应表名T_HL_DP_DB
 *
 * @author ljt
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_DB")
public class WardOperationsTasks extends BaseEntity {

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
    /** 时间 */
    @TableField("SJ")
    private Date sj;
    /** 事项 */
    @TableField("SX")
    private String sx;
    /** 患者ID */
    @TableField("HZID")
    private String hzid;
}

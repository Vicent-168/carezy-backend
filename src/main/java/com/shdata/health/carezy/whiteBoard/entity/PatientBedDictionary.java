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
 * 病床字典表实体 对应表名T_HL_DIC_BC
 *
 * @author ljt
 * @date 2024-10-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DIC_BC")
public class PatientBedDictionary extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病床名称 */
    @TableField("BCMC")
    private String bcmc;
    /** 机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 病区ID */
    @TableField("BQID")
    private String bqid;
    /** 病床号 */
    @TableField("BCH")
    private String bch;
    /** 病房号 */
    @TableField("BFH")
    private String bfh;
}

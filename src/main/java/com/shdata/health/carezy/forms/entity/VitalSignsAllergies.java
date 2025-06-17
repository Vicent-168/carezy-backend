package com.shdata.health.carezy.forms.entity;

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
 * 生命体征_过敏表实体 对应表名T_HL_YW_SMTZ_CRLB
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_SMTZ_CRLB")
public class VitalSignsAllergies extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人唯一号 */
    @TableField("BRID")
    private String brid;
    /** 病人文书索引唯一键 */
    @TableField("BLWSID")
    private String blwsid;
    /** 测量日期 */
    @TableField("CLRQ")
    private Date clrq;
    /** 测量时间段 */
    @TableField("CLSJ")
    private String clsj;
    /** 过敏类型 */
    @TableField("GMLX")
    private String gmlx;
    /** 阴阳性 */
    @TableField("YYX")
    private String yyx;
    /** 反应描述 */
    @TableField("FYMS")
    private String fyms;
}

package com.shdata.health.carezy.forms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 护理记录单文书表单字典实体 对应表名T_HL_DIC_HLJLWSBD
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DIC_HLJLWSBD")
public class DicHljlwsbd extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病历文书ID */
    @TableField("WSID")
    private String wsid;
    /** 项目代码 */
    @TableField("XMDM")
    private String xmdm;
    /** 项目名称 */
    @TableField("XMMC")
    private String xmmc;
    /** 项目类型 */
    @TableField("XMLX")
    private String xmlx;
    /** 是否多值 */
    @TableField("SFDX")
    private String sfdx;
    /** 是否必填 */
    @TableField("SFBT")
    private String sfbt;
    /** 字典项代码 */
    @TableField("XMZD")
    private String xmzd;
    /** 项目长度 */
    @TableField("XMCD")
    private long xmcd;
    /** 排序 */
    @TableField("SORT")
    private long sort;
}

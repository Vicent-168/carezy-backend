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
 * 病人护理记录单明细项表实体 对应表名T_HL_YW_HLJLMX
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_HLJLMX")
public class FormsDetail extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人文书索引唯一键 */
    @TableField("BLWSID")
    private String blwsid;
    /** 项目代码 */
    @TableField("XMDM")
    private String xmdm;
    /** 项目名称 */
    @TableField("XMMC")
    private String xmmc;
    /** 项目值说明 */
    @TableField("XMZSM")
    private String xmzsm;
    /** 项目值 */
    @TableField("XMZ")
    private String xmz;
    /** 分值 */
    @TableField("FZ")
    private long fz;
    /** 备注 */
    @TableField("BZ")
    private String bz;
}

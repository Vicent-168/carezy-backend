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
 * 护理记录单文书项目字典实体 对应表名T_HL_DIC_HLJLWSXM
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DIC_HLJLWSXM")
public class DicHljlwsxm extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病历文书ID */
    @TableField("WSID")
    private String wsid;
    /** 字典类型 */
    @TableField("ZDLX")
    private String zdlx;
    /** 字典类型描述 */
    @TableField("ZDLXMS")
    private String zdlxms;
    /** 字典序号 */
    @TableField("ZDXH")
    private long zdxh;
    /** 字典代码 */
    @TableField("ZDDM")
    private String zddm;
    /** 字典名称 */
    @TableField("ZDMC")
    private String zdmc;
    /** 分值 */
    @TableField("FZ")
    private long fz;
    /** 级别 */
    @TableField("JB")
    private String jb;
}

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
 * 病人护理入院告知内容表实体 对应表名T_HL_YW_RYGZNR
 *
 * @author myy
 * @date 2024-12-16
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_RYGZNR")
public class AdmissionForm extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人文书索引唯一键 */
    @TableField("BLWSID")
    private String blwsid;
    /** 入院告知 */
    @TableField("RYGZ")
    private String rygz;
}

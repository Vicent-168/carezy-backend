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
 * 事件发生表实体 对应表名T_HL_YW_SJFSB
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_SJFSB")
public class EventOccurrence extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人文书索引唯一键 */
    @TableField("BLWSID")
    private String blwsid;
    /** 病人唯一号 */
    @TableField("BRID")
    private String brid;
    /** 发生时间 */
    @TableField("FSSJ")
    private Date fssj;
    /** 发生事件 */
    @TableField("FSSHIJ")
    private String fsshij;
    /** 事件发生地点 */
    @TableField("SJFSDD")
    private String sjfsdd;
    /** 严重程度 */
    @TableField("YZCD")
    private String yzcd;
    /** 评估护士 */
    @TableField("PGHS")
    private String pghs;
}

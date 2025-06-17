package com.shdata.health.carezy.patientmanage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 住院护理化验单实体 对应表名T_HL_YW_ZYHLHYD
 *
 * @author panwei
 * @date 2024-10-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_ZYHLHYD")
public class Laboratory extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人唯一号 */
    @TableField("BRID")
    private String brid;
    /** 报告单号 */
    @TableField("BGDH")
    private String bgdh;
    /** 审核时间 */
    @TableField("SHSJ")
    private String shsj;
    /** 检验项目 */
    @TableField("JYXM")
    private String jyxm;
    /** 英文名 */
    @TableField("YWM")
    private String ywm;
    /** 中文名 */
    @TableField("ZWM")
    private String zwm;
    /** 定量结果 */
    @TableField("DLJG")
    private String dljg;
    /** 定性 */
    @TableField("DX")
    private String dx;
    /** 参考值 */
    @TableField("CKZ")
    private String ckz;
    /** 单位 */
    @TableField("DW")
    private String dw;
    /** 操作 */
    @TableField("CZ")
    private String cz;
}

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
 * 导管置管_导管维护表实体 对应表名T_HL_YW_DGZGDGWH
 *
 * @author myy
 * @date 2024-11-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_DGZGDGWH")
public class ConduitMaintenance extends BaseEntity {

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
    /** 维护时间 */
    @TableField("WHSJ")
    private Date whsj;
    /** 导管名称 */
    @TableField("DGMC")
    private String dgmc;
    /** 导管部位 */
    @TableField("DGBW")
    private String dgbw;
    /** 导管编号 */
    @TableField("DGBH")
    private String dgbh;
    /** 使用情况 */
    @TableField("SYQK")
    private String syqk;
    /** 是否异常 */
    @TableField("SFYC")
    private String sfyc;
    /** 维护内容 */
    @TableField("WHNR")
    private String whnr;
    /** 异常选择 */
    @TableField("YCXZ")
    private String ycxz;
}

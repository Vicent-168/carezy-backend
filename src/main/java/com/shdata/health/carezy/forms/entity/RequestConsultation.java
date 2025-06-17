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
 * 请求会诊表实体 对应表名T_HL_YW_QQHZB
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_QQHZB")
public class RequestConsultation extends BaseEntity {

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
    /** 记录时间 */
    @TableField("JLSJ")
    private Date jlsj;
    /** 申请时间 */
    @TableField("SQSJ")
    private String sqsj;
    /** 发起病区 */
    @TableField("FQBQ")
    private String fqbq;
    /** 请求对象 */
    @TableField("QQDX")
    private String qqdx;
    /** 请求目的 */
    @TableField("QQMD")
    private String qqmd;
    /** 患者情况 */
    @TableField("HZQK")
    private String hzqk;
    /** 主要诊断 */
    @TableField("ZYZD")
    private String zyzd;
    /** 因何会诊 */
    @TableField("YHHZ")
    private String yhhz;
    /** 会诊目的 */
    @TableField("HZMD")
    private String hzmd;
    /** 会诊时间 */
    @TableField("HZSJ")
    private String hzsj;
    /** 会诊意见 */
    @TableField("HZYJ")
    private String hzyj;
    /** 会诊医生 */
    @TableField("HZYS")
    private String hzys;
}

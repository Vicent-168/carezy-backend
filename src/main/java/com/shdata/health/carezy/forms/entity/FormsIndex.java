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
 * 病人护理记录单文书索引表实体 对应表名T_HL_YW_HLJLWSSYB
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_HLJLWSSYB")
public class FormsIndex extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人唯一号 */
    @TableField("BRID")
    private String brid;
    /** 病历文书ID */
    @TableField("WSID")
    private String wsid;
    /** 文书分类 */
    @TableField("WSFL")
    private String wsfl;
    /**文书编码**/
    @TableField("WSBM")
    private String wsbm;
    /** 总分 */
    @TableField("ZF")
    private long zf;
    /** 结论 */
    @TableField("JL")
    private String jl;
    /**监控状态**/
    @TableField("JKZT")
    private String jkzt;
    /**拔管时间**/
    @TableField("BGSJ")
    private Date bgsj;
    /** 医疗机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 科室ID */
    @TableField("KSID")
    private String ksid;
    /** 日期 */
    @TableField("RQ")
    private Date rq;
    /** 签名医生ID */
    @TableField("QMYSID")
    private String qmysid;

    /**
     * 风险等级
     */
    @TableField("FXDJ")
    private String fxdj;
    /**
     *风险发生类别
     */
    @TableField("FXFSLB")
    private String fxfslb;

    /**导管类型
     */
    @TableField("DGLX")
    private String dglx;

}

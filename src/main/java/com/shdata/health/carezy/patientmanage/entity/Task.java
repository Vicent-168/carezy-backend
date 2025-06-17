package com.shdata.health.carezy.patientmanage.entity;

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
 * 住院护理任务实体 对应表名T_HL_YW_ZYHLRWB
 *
 * @author panwei
 * @date 2024-10-23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_ZYHLRWB")
public class Task extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人唯一号 */
    @TableField("BRID")
    private String brid;
    /** 任务代码 */
    @TableField("RWDM")
    private String rwdm;
    /** 任务名称 */
    @TableField("RWMC")
    private String rwmc;
    /** 任务状态 */
    @TableField("RWZT")
    private String rwzt;
    /** 病历文书ID  */
    @TableField("WSID")
    private String wsid;
    /** 病人文书索引唯一键 */
    @TableField("BLWSID")
    private String blwsid;
    /** 任务计划开始时间 */
    @TableField("RWJHKSSJ")
    private Date rwjhkssj;
    /** 任务计划结束时间 */
    @TableField("RWJHJSSJ")
    private Date rwjhjssj;
    /** 任务计划提醒时间 */
    @TableField("RWJHTXSJ")
    private Date rwjhtxsj;
    /** 任务完成时间 */
    @TableField("RWWCSJ")
    private Date rwwcsj;
    /** 任务类型 */
    @TableField("RWLX")
    private String rwlx;
}

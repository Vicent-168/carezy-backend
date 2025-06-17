package com.shdata.health.carezy.whiteBoard.entity;

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
 * 住院护理任务表实体 对应表名T_HL_YW_ZYHLRWB
 *
 * @author ljt
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_ZYHLRWB")
public class InpatientNursingTask extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 病人唯一号 */
    @TableField("BRID")
    private String brid;
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
    /** 任务完成时间 */
    @TableField("RWWCSJ")
    private Date rwwcsj;
}

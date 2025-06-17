package com.shdata.health.carezy.whiteBoard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 病区运营_自定义项目表实体 对应表名T_HL_DP_BQYY_XMB
 *
 * @author ljt
 * @date 2024-10-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQYY_XMB")
public class WardOperationCustomItem extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 医疗机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 病区ID */
    @TableField("BQID")
    private String bqid;
    /** 项目类型 */
    @TableField("XMLX")
    private String xmlx;
    /** 项目类型名称 */
    @TableField("XMLXMC")
    private String xmlxmc;
    /** 项目名称 */
    @TableField("XMMC")
    private String xmmc;
    /** 项目值 */
    @TableField("XMZ")
    private String xmz;
}

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
 * 护理记录单文书字典实体 对应表名T_HL_DIC_HLJLWS
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DIC_HLJLWS")
public class DicHljlws extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 文书名称 */
    @TableField("MC")
    private String mc;
    /** 文书序号 */
    @TableField("XH")
    private long xh;
    /** 文书分类名称 */
    @TableField("FL")
    private String fl;
    /** 文书分类代码 */
    @TableField("FLDM")
    private String fldm;
    /** 文书分类序号 */
    @TableField("FLXH")
    private long flxh;
    /** 版本号 */
    @TableField("BBH")
    private long bbh;
    /** 系统关联页面ID（关联菜单信息表通过菜单表进行授权使用） */
    @TableField("URL")
    private String url;
    /** 文书类型 */
    @TableField("LX")
    private String lx;
    /** 量表类型 */
    @TableField("LBLX")
    private String lblx;
    /** 启停标记 */
    @TableField("QTBJ")
    private String qtbj;
    /** 启用日期 */
    @TableField("QYRQ")
    private Date qyrq;
    /** 停用日期 */
    @TableField("TYRQ")
    private Date tyrq;
    /** BEAN评估对象 */
    @TableField("BEAN")
    private String bean;
    /**文书编码**/
    @TableField("WSBM")
    private String wsbm;
    /**是否批量**/
    @TableField("SFPLDY")
    private String sfpldy;
}

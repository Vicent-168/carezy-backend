package com.shdata.health.carezy.whiteBoard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * @author ljt
 * @date 2024年12月02日 13:46
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQYY_MKB")
public class MkMkxmSj extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模块id
     */
    @TableField("mkid")
    private String mkid;
    /**
     * 模块类型
     */
    @TableField("mklx")
    private String mklx;
    /**
     * 模块名称
     */
    @TableField("mkmc")
    private String mkmc;
    /**
     * 项目id
     */
    @TableField("xmid")
    private String xmid;
    /**
     * 项目名称
     */
    @TableField("xmmc")
    private String xmmc;
    /**
     * 模块项目id
     */
    @TableField("mkxmid")
    private String mkxmid;
    /**
     * 项目顺序
     */
    @TableField("xmsx")
    private String xmsx;
    /**
     * 项目值
     */
    @TableField("xmz")
    private String xmz;
    /**
     * 项目值1
     */
    @TableField("xmz1")
    private String xmz1;
    /**
     * 项目值2
     */
    @TableField("xmz2")
    private String xmz2;
    /**
     * 项目值3
     */
    @TableField("xmz3")
    private String xmz3;
    /**
     * 日期
     */
    @TableField("rq")
    private Date rq;
    /**
     * 是否展示 0:展示 1:不展示
     */
    @TableField("sfzs")
    private String sfzs;
    /**
     * 单位
     */
    @TableField("dw")
    private String dw;


}

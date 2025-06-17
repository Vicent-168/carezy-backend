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
 * 病区运营模块项目表实体 对应表名T_HL_DP_BQYY_MKXMB
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_DP_BQYY_MKXMB")
public class BqyyMkxmb extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 项目模块ID */
    @TableField("MKID")
    private String mkid;
    /** 项目ID */
    @TableField("XMID")
    private String xmid;
    /** 项目名称 */
    @TableField("XMMC")
    private String xmmc;
    /** 项目顺序 */
    @TableField("XMSX")
    private String xmsx;
    /**
     * 是否展示 0展示 1不展示
     * @param item
     */
    @TableField("SFZS")
    private String sfzs;

    public BqyyMkxmb(BqyyMkxmb item) {
        this.id = item.getId();
        this.mkid = item.getMkid();
        this.xmid = item.getXmid();
        this.xmmc = item.getXmmc();
        this.xmsx = item.getXmsx();
        this.sfzs = item.getSfzs();
    }
}

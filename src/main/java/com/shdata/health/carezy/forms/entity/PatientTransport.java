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
 * 患者转运表实体 对应表名T_HL_YW_HZZYB
 *
 * @author myy
 * @date 2024-11-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_HZZYB")
public class PatientTransport extends BaseEntity {

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
    /** 转运日期 */
    @TableField("ZYRQ")
    private Date zyrq;
    /** 患者意识 */
    @TableField("HZYS")
    private String hzys;
    /** 导管情况 */
    @TableField("DGQK")
    private String dgqk;
    /** 皮肤情况 */
    @TableField("PFQK")
    private String pfqk;
    /** 皮肤情况_压疮部位 */
    @TableField("PFQK_YCBW")
    private String pfqkYcbw;
    /** 皮肤情况_压疮面积 */
    @TableField("PFQK_YCMJ")
    private String pfqkYcmj;
    /** 皮肤情况_压疮创面情况 */
    @TableField("PFQK_YCCMQK")
    private String pfqkYccmqk;
    /** 药品 */
    @TableField("YP")
    private String yp;
    /** 物品 */
    @TableField("WP")
    private String wp;
    /** 特殊交班 */
    @TableField("TSJB")
    private String tsjb;
    /** 转出科室 */
    @TableField("ZCKS")
    private String zcks;
    /** 评估护士 */
    @TableField("PGHS")
    private String pghs;
}

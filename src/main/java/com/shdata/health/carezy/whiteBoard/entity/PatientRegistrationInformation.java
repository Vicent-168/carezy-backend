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
 * 护理病人登记信息实体 对应表名T_HL_YW_BRDJXX
 *
 * @author ljt
 * @date 2024-10-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_BRDJXX")
public class PatientRegistrationInformation extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private String id;
    /** 医疗机构ID */
    @TableField("YLJGID")
    private String yljgid;
    /** 卡号 */
    @TableField("KH")
    private String kh;
    /** 卡类型 */
    @TableField("KLX")
    private String klx;
    /** 姓名 */
    @TableField("XM")
    private String xm;
    /** 性别 */
    @TableField("XB")
    private String xb;
    /** 身份证号 */
    @TableField("SFZH")
    private String sfzh;
    /** 出身日期 */
    @TableField("CSRQ")
    private Date csrq;
    /** 年龄 */
    @TableField("NL")
    private long nl;
    /** 联系电话 */
    @TableField("LXDH")
    private String lxdh;
    /** 住院号 */
    @TableField("ZYH")
    private String zyh;
    /** 病区 */
    @TableField("BQID")
    private String bqid;
    /** 病床号 */
    @TableField("BCH")
    private String bch;
    /** 入院时间 */
    @TableField("RYSJ")
    private Date rysj;
    /** 出院时间 */
    @TableField("CYSJ")
    private Date cysj;
    /** 诊断ID */
    @TableField("ZDID")
    private String zdid;
    /** 责任护士 */
    @TableField("HSID")
    private String hsid;
    /** 床位医生 */
    @TableField("YSID")
    private String ysid;
    /** 膳食 */
    @TableField("SS")
    private String ss;
    /** 护理项目 */
    @TableField("HLXMID")
    private String hlxmid;
    /** 护理等级 */
    @TableField("HLDJ")
    private String hldj;
    /** 过敏源 */
    @TableField("GM")
    private String gm;
    /** 患者状态 */
    @TableField("HZZT")
    private String hzzt;
    /** 入院出院状态 */
    @TableField("RYCYZT")
    private String rycyzt;
    /** 登记科室ID */
    @TableField("DJKSID")
    private String djksid;
    /** 登记医生ID */
    @TableField("DJYSID")
    private String djysid;
    /** 登记日期 */
    @TableField("DJRQ")
    private Date djrq;
}

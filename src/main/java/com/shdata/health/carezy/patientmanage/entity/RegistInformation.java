package com.shdata.health.carezy.patientmanage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shdata.health.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 病人登记信息实体 对应表名T_HL_YW_BRDJXX
 *
 * @author panwei
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("T_HL_YW_BRDJXX")
public class RegistInformation extends BaseEntity {
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
    @TableField("MZ")
    private String mz;
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
    /** 身高 */
    @TableField("SG")
    private BigDecimal sg;
    /** 体重 */
    @TableField("TZ")
    private BigDecimal tz;
    /** 职业 */
    @TableField("ZY")
    private String zy;
    /** 婚姻 */
    @TableField("HY")
    private String hy;
    /** 国籍 */
    @TableField("GJ")
    private String gj;
    /** 籍贯省市 */
    @TableField("JGSS")
    private String jgss;
    /** 单位名称 */
    @TableField("DWMC")
    private String dwmc;
    /** 单位电话 */
    @TableField("DWDH")
    private String dwdh;
    /** 单位地址 */
    @TableField("DWDZ")
    private String dwdz;
    /** 出生省市 */
    @TableField("CSSS")
    private String csss;
    /** 家庭电话 */
    @TableField("JTDH")
    private String jtdh;
    /** 家庭地址 */
    @TableField("JTDZ")
    private String jtdz;
    /** 户口省市 */
    @TableField("HKSS")
    private String hkss;
    /** 户口地址 */
    @TableField("HKDZ")
    private String hkdz;
    /** 联系人员 */
    @TableField("LXRY")
    private String lxry;
    /** 联系关系 */
    @TableField("LXGX")
    private String lxgx;
    /** 联系电话 */
    @TableField("LXDH")
    private String lxdh;
    /** 联系地址 */
    @TableField("LXDZ")
    private String lxdz;
    /** 血型_ABO */
    @TableField("XXABO")
    private String xxabo;
    /** 血型_RH */
    @TableField("XXRH")
    private String xxrh;
    /** 住院号 */
    @TableField("ZYH")
    private String zyh;
    /** 门诊号 */
    @TableField("MZH")
    private String mzh;
    /** 病案号 */
    @TableField("BAH")
    private String bah;
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
    /** 陪客标识 */
    @TableField("PKBS")
    private String pkbs;
    /** 病人状态 */
    @TableField("BRZT")
    private String brzt;
    /** 入院途径 */
    @TableField("YYTJ")
    private String yytj;
    /** 主任医生 */
    @TableField("ZRYS")
    private String zrys;
    /** 主治医生 */
    @TableField("ZZYS")
    private String zzys;
    /** 医生诊断 */
    @TableField("YSZD")
    private String yszd;
    /** 生命体征 */
    @TableField("SMTZ")
    private String smtz;
    /** 治疗类别 */
    @TableField("ZLLB")
    private String zllb;
    /** 危重级别 */
    @TableField("WZJB")
    private String wzjb;
    /** 潜在并发症 */
    @TableField("QZBFZ")
    private String qzbfz;
    /** 今日手术 */
    @TableField("JRSS")
    private String jrss;
    /** 异常检查 */
    @TableField("YCJC")
    private String ycjc;
    /** 康复诊断 */
    @TableField("KFZD")
    private String kfzd;
    /** 异常检验 */
    @TableField("YCJY")
    private String ycjy;
    /** 死亡日期 */
    @TableField("SWRQ")
    private Date swrq;
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
    /** 转归状态 */
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

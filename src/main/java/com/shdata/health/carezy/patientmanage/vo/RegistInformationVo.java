package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
/**
 * 病人登记信息  VO
 *
 * @author panwei
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RegistInformationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 医疗机构ID */
    private String yljgid;
    /** 卡号 */
    private String kh;
    /** 卡类型 */
    @NameField(type = DictType.DICT, target = "klx", key = "DIC_KLX")
    private String klx;

    @NameField(type = DictType.DICT, target = "mzmc", key = "DIC_MZ")
    private String mz;
    private String mzmc;
    /** 姓名 */
    private String xm;
    /** 性别 */
    @NameField(type = DictType.DICT, target = "xb", key = "DIC_XB")
    private String xb;
    /** 身份证号 */
    private String sfzh;
    /** 出身日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date csrq;
    /** 年龄 */
    private long nl;
    /** 联系电话 */
    private String lxdh;
    /** 住院号 */
    private String zyh;
    /** 病区 */
    @NameField(type = DictType.Dept, target = "bqmc")
    private String bqid;
    /** 病区名称 */
    private String bqmc;
    /** 病床号 */
    private String bch;
    /** 住院状态 */
    @NameField(type = DictType.DICT, target = "zyzt", key = "DIC_ZYZT")
    private String zyzt;
    /** 入院天数 */
    private Integer ryts;
    /** 入院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rysj;
    /** 出院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cysj;
    /** 诊断ID */
    //@NameField(type = DictType.CUSTOM, key = "ID", target = "zdmc", bean = DiseaseNameFieldConverter.class)
    private String zdid;
    /** 诊断名称 */
    private String zdmc;
    /** 责任护士 */
    @NameField(type = DictType.User, target = "hsxm")
    private String hsid;
    private String hsxm;
    /** 床位医生 */
    @NameField(type = DictType.User, target = "ysxm")
    private String ysid;
    private String ysxm;
    /** 膳食 */
    private String ss;
    /** 护理项目 */
    private String hlxmid;
    /** 护理等级 */
    private String hldj;
    /** 过敏源 */
    private String gm;
    /** 患者状态 */
    private String hzzt;
    /** 入院出院状态 */
    @NameField(type = DictType.DICT, target = "rycyzt", key = "DIC_RYCYZT")
    private String rycyzt;
    /** 登记科室ID */
    @NameField(type = DictType.Dept, target = "djksmc")
    private String djksid;
    private String djksmc;
    /** 登记医生ID */
    @NameField(type = DictType.User, target = "djysxm")
    private String djysid;
    private String djysxm;
    /** 登记日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date djrq;
    /** 更新时间 日期格式:yyyy-MM-dd HH:mm:ss */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private long updateTimestamp;

    /** 患者费别 */
    private String hzfb;
    /** 任务数 */
    private Integer rws;
    /** 身高 */
    private BigDecimal sg;
    /** 体重 */
    private BigDecimal tz;
    /** 职业 */
    private String zy;
    /** 婚姻 */
    private String hy;
    /** 国籍 */
    private String gj;
    /** 籍贯省市 */
    private String jgss;
    /** 单位名称 */
    private String dwmc;
    /** 单位电话 */
    private String dwdh;
    /** 单位地址 */
    private String dwdz;
    /** 出生省市 */
    private String csss;
    /** 家庭电话 */
    private String jtdh;
    /** 家庭地址 */
    private String jtdz;
    /** 户口省市 */
    private String hkss;
    /** 户口地址 */
    private String hkdz;
    /** 联系人员 */
    private String lxry;
    /** 联系关系 */
    private String lxgx;
    /** 联系地址 */
    private String lxdz;
    /** 血型_ABO */
    private String xxabo;
    /** 血型_RH */
    private String xxrh;
    /** 门诊号 */
    private String mzh;
    /** 病案号 */
    private String bah;
    /** 陪客标识 */
    private String pkbs;
    /** 病人状态 */
    private String brzt;
    /** 入院途径 */
    private String yytj;
    /** 主任医生 */
    private String zrys;
    /** 主治医生 */
    private String zzys;
    /** 医生诊断 */
    private String yszd;
    /** 生命体征 */
    private String smtz;
    /** 治疗类别 */
    private String zllb;
    /** 危重级别 */
    private String wzjb;
    /** 潜在并发症 */
    private String qzbfz;
    /** 今日手术 */
    private String jrss;
    /** 异常检查 */
    private String ycjc;
    /** 康复诊断 */
    private String kfzd;
    /** 异常检验 */
    private String ycjy;
    /** 死亡日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date swrq;
    /** 备注 */
    private String remarks;
    /**
     * 获取入院天数
     * 规则：出院时间-入院时间，如果出院时间为空，则取当前时间
     * @return
     */
    public Integer getRyts(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate cysj = this.getCysj() == null ? LocalDate.now() : LocalDate.parse(this.getCysj().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString(), formatter);
        LocalDate rysj = LocalDate.parse(this.getRysj().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString(), formatter);
        long between = ChronoUnit.DAYS.between(rysj, cysj);
        return Integer.valueOf(String.valueOf(between));
    }

}

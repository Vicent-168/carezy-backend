package com.shdata.health.carezy.whiteBoard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
/**
 * 护理病人登记信息  VO
 *
 * @author ljt
 * @date 2024-10-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientRegistrationInformationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 医疗机构ID */
    private String yljgid;
    /** 卡号 */
    @NotBlank
    private String kh;
    /** 卡类型 */
    @NotBlank
    private String klx;
    /** 姓名 */
    @NotBlank
    private String xm;
    /** 性别 */
    private String xb;
    /** 身份证号 */
    @NotBlank
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
    private String bqid;
    /** 病床号 */
    private String bch;
    /** 入院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rysj;
    /** 出院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cysj;
    /** 诊断ID */
    private String zdid;
    /** 责任护士 */
    private String hsid;
    /** 床位医生 */
    private String ysid;
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
    private String rycyzt;
    /** 登记科室ID */
    private String djksid;
    /** 登记医生ID */
    private String djysid;
    /** 登记日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date djrq;
}

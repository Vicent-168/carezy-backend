package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 病人血糖 VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientBloodGlucoseDataVo implements Serializable {
    /** 病人唯一号 */
    private String id;
    /** 床号 */
    private String bch;
    /** 姓名 */
    private String xm;
    /** 住院号 */
    private String zyh;
    /** 身份证号 */
    private String sfzh;
    /** 性别 */
    @NameField(type = DictType.DICT, target = "xb", key = "DIC_XB")
    private String xb;
    /** 年龄 */
    private long nl;
    /** 入院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rysj;
    /** 出院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cysj;
    /** 入院天数 */
    private Integer ryts;
    /** 床位医生 */
    @NameField(type = DictType.User, target = "ysxm")
    private String ysid;
    private String ysxm;

    /** 出身日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date csrq;
    /** 文书id */
    private String wsid;
    /** 病人文书唯一索引键 */
    private String brwsid;
    /** 文书编码  */
    private String  wsbm;
    /** 文书分类  */
    private String  wsfl;
    /** 医疗机构ID */
    @NameField(type = DictType.Yljg,target = "yljgmc")
    private String yljgid;
    /** 科室ID */
    @NameField(type = DictType.Dept,target = "ksmc")
    private String ksid;
    /** 日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm-ss")
    @JsonFormat(pattern = "yyyy-MM-dd-hh-mm-ss ", timezone = "GMT+8")
    @NotBlank
    private Date rq;

    /** 检测标本 */
    private String XM_BB;

    /** 测量时间 日期格式:yyyy-MM-dd HH:mm */
    @DateTimeFormat(pattern = "yyyy-MM-dd  ")
    @JsonFormat(pattern = "yyyy-MM-dd  ", timezone = "GMT+8")
    private Date XM_CLSJ;
    /** 时间阶段 */
    private String XM_SJJD;
    /** 测量时段 */
    private String XM_CLSD;
    /** 血糖测量值 */
    private BigDecimal XM_CLZ;




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
    public void setCsrqAndNlFromIdCard() throws ParseException {
        if (this.sfzh == null || this.sfzh.length() != 18) {
            throw new IllegalArgumentException("身份证号格式不正确");
        }

        // 从身份证号中提取出生日期
        String birthDateStr = this.sfzh.substring(6, 14);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date birthDate;
        try {
            birthDate = dateFormat.parse(birthDateStr);
        } catch (ParseException e) {
            throw new RuntimeException("出生日期解析失败", e);
        }

        // 计算年龄
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);
        int birthYear = birthCalendar.get(Calendar.YEAR);
        int birthMonth = birthCalendar.get(Calendar.MONTH);
        int birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar nowCalendar = Calendar.getInstance();
        int currentYear = nowCalendar.get(Calendar.YEAR);
        int currentMonth = nowCalendar.get(Calendar.MONTH);
        int currentDay = nowCalendar.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear;
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }

        // 赋值给字段
        this.nl = age;
        // 提取性别信息
        char genderCode = this.sfzh.charAt(16);
        this.xb= (genderCode % 2 == 0) ? "2" : "1";
    }
}

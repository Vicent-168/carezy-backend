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
public class RegistInformationListWithNoneConertVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 姓名 */
    private String xm;
    /** 性别 */

    private String xb;

    /** 年龄 */
    private long nl;

    /** 住院号 */
    private String zyh;
    /** 病区 */
    private String bqid;
    /** 病床号 */
    private String bch;

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

    /** 床位医生 */
    private String ysid;
    private String ysxm;

    /** 护理等级 */
    private String hldj;
    private String hldjmc;
    /** 过敏源 */
    private String gm;
    /** 患者状态 */
    private String hzzt;
    /** 入院出院状态 */
    private String rycyzt;


    /** 更新时间 日期格式:yyyy-MM-dd HH:mm:ss */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private long updateTimestamp;

    /** 患者费别 */
    private String klx;
    /** 任务数 */
    private Integer rws;
    /** 责任护士 */
    private String hsid;

    private String hsxm;

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

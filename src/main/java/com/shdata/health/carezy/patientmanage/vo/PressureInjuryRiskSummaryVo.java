package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * 风险汇总压力性损伤
 *
 * @author Dingwentao
 * @date 2024-10-31
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PressureInjuryRiskSummaryVo {
    /** 病人唯一号 */
    private String id;
    /** 床号 */
    private String bch;
    /** 姓名 */
    private String xm;
    /** 住院号 */
    private String zyh;
    /** 年龄 */
    private long nl;
    /** 入院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rysj;
    /** 入院天数 */
    private Integer ryts;
    /** 床位医生 */
    @NameField(type = DictType.User, target = "ysxm")
    private String ysid;
    private String ysxm;
    /** 性别 */
    @NameField(type = DictType.DICT, target = "xb", key = "DIC_XB")
    private String xb;
    /** 出院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cysj;
    /** 任务数 */
    private String rws;
    /** 患者费别 */
    private String hzfb;
    /** 护士id */
    private String hsid;
    /** 护士名称 */
    private String hsmc;
    /** 过敏 */
    private String gm;
    /** 快捷标签 */
    private List<String> kjbqs;





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

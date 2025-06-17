package com.shdata.health.carezy.patientmanage.vo;

import com.shdata.health.carezy.common.dictconverter.FormNameFieldConverter;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 住院护理任务  VO
 *
 * @author panwei
 * @date 2024-10-23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TaskVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /** 任务名称 */
    private String rwmc;
    /** 任务代码 */
    private String rwdm;
    /** 任务状态 */
    private String rwzt;
    /** 病历文书ID  */
    @NameField(type = DictType.CUSTOM, key = "ID", target = "wsmc", bean = FormNameFieldConverter.class)
    private String wsid;
    private String wsmc;
    /** 病人文书索引唯一键 */
    private String blwsid;
    /** 任务计划开始时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rwjhkssj;
    /** 任务计划结束时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rwjhjssj;
    /** 任务计划提醒时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rwjhtxsj;
    /** 任务完成时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rwwcsj;

    private String rwlx;
    /**
     * 备注
     */
    private String rwmarks;
}

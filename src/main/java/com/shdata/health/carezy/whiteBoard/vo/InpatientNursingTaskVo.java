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
 * 住院护理任务表  VO
 *
 * @author ljt
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InpatientNursingTaskVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /** 任务名称 */
    private String rwmc;
    /** 任务状态 */
    private String rwzt;
    /** 病历文书ID  */
    private String wsid;
    /** 病人文书索引唯一键 */
    @NotBlank
    private String blwsid;
    /** 任务计划开始时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rwjhkssj;
    /** 任务计划结束时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rwjhjssj;
    /** 任务完成时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rwwcsj;
    /**
     * 病床号
     */
    private String allBch;
}

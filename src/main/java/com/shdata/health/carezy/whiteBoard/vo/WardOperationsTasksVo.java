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
 * 病区运营_大屏代办  VO
 *
 * @author ljt
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WardOperationsTasksVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 医疗机构ID */
    @NotBlank
    private String yljgid;
    /** 病区ID */
    @NotBlank
    private String bqid;
    /** 日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rq;
    /** 时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sj;
    /** 事项 */
    private String sx;
    /** 患者ID */
    private String hzid;
    /**删除标识*/
    private String delFlag;
}

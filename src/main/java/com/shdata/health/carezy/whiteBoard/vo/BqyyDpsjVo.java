package com.shdata.health.carezy.whiteBoard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
/**
 * 病区运营大屏数据表  VO
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BqyyDpsjVo implements Serializable {
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
    /** 日期 日期格式:yyyy-MM-dd */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rq;
    /** 时段 */
    @NotBlank
    private String sd;
    /** 模块项目ID */
    @NotBlank
    private String mkxmid;
    /** 项目值 */
    @NotBlank
    private String xmz;
    /** 项目值1 */
    private String xmz1;
    /** 项目值2 */
    private String xmz2;
    /** 项目值3 */
    private String xmz3;
}

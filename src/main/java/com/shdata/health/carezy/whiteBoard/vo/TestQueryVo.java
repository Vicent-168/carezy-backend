package com.shdata.health.carezy.whiteBoard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class TestQueryVo implements Serializable {
    /**
     * 查询Spring Bean
     */
    @NotBlank
    private String bean;
    /**
     * 医疗机构ID
     */
    @NotBlank
    private String yljgid;
    /**
     * 查询日期
     */
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rq;

}

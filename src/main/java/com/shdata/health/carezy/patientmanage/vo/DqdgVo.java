package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.common.serializer.jackson.DictFormat;
import com.shdata.health.common.serializer.jackson.DictType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 当前导管对象  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DqdgVo {

    private String id;
    /** 日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
    @NotBlank
    private Date rq;
    /** 文书名称 */
    //private String dgmc;
    /** 文书编码 */
    private String wsbm;
    /** 文书id */
    private String wsid;
    /** 跳转链接 *//*
    private String url;
     *//** 组件名称 *//*
    private String compName; */
    /** 文书分类 */
    private String wsfl;
    /**
     * 导管类型
     */
    @DictFormat(dictType = DictType.DICT, dictKey = "DIC_DGLX")
    private String dglx;
}

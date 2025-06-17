package com.shdata.health.carezy.forms.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 病人护理记录单文书索引表  VO
 *
 * @author myy
 * @date 2024-08-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsSaveLineVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rq;

    /**
     * 表单明细
     */
    private Map<String,Object> detailMap;
    //ToDO 其它table类型的项目





}

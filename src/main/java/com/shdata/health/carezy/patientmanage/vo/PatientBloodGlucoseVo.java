package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.carezy.forms.vo.FormsIndexVoo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * 病人血糖 VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientBloodGlucoseVo {

    /** 病人唯一号 */
    private String brid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sj;
    /** 测量值 */
    private String xmClz;
    /**
     * 表单索引
     */
    private FormsIndexVoo recordIndex;

    /**
     * 表单明细
     */
    private Map<String,Object> detailMap;
}

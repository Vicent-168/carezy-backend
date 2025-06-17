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
 * 病人生命体征  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientVitalSignVo {
    /** 病人唯一号 */
    private String brid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sj;
    /** 体温 */
    private String xmTw;
    /** 脉搏 */
    private String xmMb;
    /** 心率 */
    private String xmXl;
    /** 呼吸 */
    private String xmHx;
    /** 遵医嘱血氧 */
    private String xmyzxYang;
    /** 收缩压 */
    private String xmWcssy;
    /** 舒张压 */
    private String xmWcszy;


    /**
     * 表单索引
     */
    private FormsIndexVoo recordIndex;

    /**
     * 表单明细
     */
    private Map<String,Object> detailMap;
}

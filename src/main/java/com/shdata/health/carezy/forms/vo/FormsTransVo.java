package com.shdata.health.carezy.forms.vo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
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
public class FormsTransVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单索引
     */
    private FormsIndexVo recordIndex;

    /**
     * 转运明细
     */
    private PatientTransportVo patientTransport;





}

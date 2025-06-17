package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病人护理入院告知内容表  VO
 *
 * @author myy
 * @date 2024-12-16
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdmissionFormVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人文书索引唯一键 */
    @NotBlank
    private String blwsid;
    /** 入院告知 */
    @NotBlank
    private String rygz;
}

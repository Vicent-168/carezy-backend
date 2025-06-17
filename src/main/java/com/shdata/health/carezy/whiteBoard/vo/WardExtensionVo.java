package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病区扩展表  VO
 *
 * @author ljt
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WardExtensionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病区ID */
    @NotBlank
    private String bqid;
    /** 病区总床位数 */
    private String bqzcws;
    /** 病区护士长 */
    private String bqhsz;
    /** 病区副护士长 */
    private String bqfhsz;
}

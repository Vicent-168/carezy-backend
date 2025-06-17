package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病床字典表  VO
 *
 * @author ljt
 * @date 2024-10-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientBedDictionaryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病床名称 */
    @NotBlank
    private String bcmc;
    /** 机构ID */
    @NotBlank
    private String yljgid;
    /** 病区ID */
    @NotBlank
    private String bqid;
    /** 病床号 */
    private String bch;
    /** 病房号 */
    private String bfh;
}

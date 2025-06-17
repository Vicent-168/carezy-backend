package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病区运营模块项目表  VO
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BqyyMkxmbVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 项目模块ID */
    @NotBlank
    private String mkid;
    /** 项目ID */
    @NotBlank
    private String xmid;
    /** 项目名称 */
    @NotBlank
    private String xmmc;
    /** 排序 */
    private Integer xmsx;
    /** 单位 */
    private String dw;
}

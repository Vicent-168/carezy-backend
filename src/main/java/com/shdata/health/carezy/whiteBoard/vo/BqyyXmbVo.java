package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病区运营项目表  VO
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BqyyXmbVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 项目名称 */
    @NotBlank
    private String xmmc;
    /** 计算BEAN */
    private String bean;
    /** 参数 */
    private String cs;
    /** 刷新间隔 */
    private long sxjg;
    /** 医疗机构ID */
    private String yljgid;
    /** 单位 */
    private String dw;
}

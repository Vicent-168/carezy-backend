package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病区运营_自定义项目表  VO
 *
 * @author ljt
 * @date 2024-10-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WardOperationCustomItemVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 医疗机构ID */
    @NotBlank
    private String yljgid;
    /** 病区ID */
    @NotBlank
    private String bqid;
    /** 项目类型 */
    private String xmlx;
    /** 项目类型名称 */
    private String xmlxmc;
    /** 项目名称 */
    private String xmmc;
    /** 项目值 */
    private String xmz;
}

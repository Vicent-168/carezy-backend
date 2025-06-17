package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 护理记录单文书表单字典  VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DicHljlwsbdVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病历文书ID */
    @NotBlank
    private String wsid;
    /** 项目代码 */
    @NotBlank
    private String xmdm;
    /** 项目名称 */
    @NotBlank
    private String xmmc;
    /** 项目类型 */
    @NotBlank
    private String xmlx;
    /** 是否多值 */
    @NotBlank
    private String sfdx;
    /** 是否必填 */
    @NotBlank
    private String sfbt;
    /** 字典项代码 */
    private String xmzd;
    /** 项目长度 */
    private long xmcd;
    /** 排序 */
    private long sort;
}

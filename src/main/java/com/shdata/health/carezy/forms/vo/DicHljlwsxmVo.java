package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 护理记录单文书项目字典  VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DicHljlwsxmVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病历文书ID */
    @NotBlank
    private String wsid;
    /** 字典类型 */
    @NotBlank
    private String zdlx;
    /** 字典类型描述 */
    private String zdlxms;
    /** 字典序号 */
    @NotNull
    private long zdxh;
    /** 字典代码 */
    @NotBlank
    private String zddm;
    /** 字典名称 */
    @NotBlank
    private String zdmc;
    /** 分值 */
    private long fz;
    /** 级别 */
    private String jb;
}

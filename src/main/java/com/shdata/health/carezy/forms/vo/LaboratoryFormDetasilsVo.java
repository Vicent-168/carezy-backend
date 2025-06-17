package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 住院护理化验单详情  VO
 *
 * @author myy
 * @date 2024-11-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LaboratoryFormDetasilsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /** 化验单唯一编号 */
    @NotBlank
    private String bgdh;
    /** 审核时间 */
    private String shsj;
    /** 检验项目 */
    private String jyxm;
    /** 英文名 */
    private String ywm;
    /** 中文名 */
    private String zwm;
    /** 定量结果 */
    private String dljg;
    /** 定性 */
    private String dx;
    /** 参考值 */
    private String ckz;
    /** 单位 */
    private String dw;
    /** 操作 */
    private String cz;
}

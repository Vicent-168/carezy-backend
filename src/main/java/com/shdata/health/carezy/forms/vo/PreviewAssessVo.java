package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * 量表评估预览  VO
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PreviewAssessVo {

    /**
     * 量表中项目
     */
    private Map<String, Object> scaleItem;

    @NotBlank
    /** 病历文书ID  */
    private String wsid;

    /**
     * 前端计算的分值
     */
    private String score;
}

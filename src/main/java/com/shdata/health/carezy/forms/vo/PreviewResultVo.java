package com.shdata.health.carezy.forms.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * 量表评估预览  VO
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class PreviewResultVo {

    /**
     * 提示字段
     */
    private String tips;

    /**
     * 前端表单
     */
    private Map<String, Object> previewMap;

    /**
     * 评估分值[后端]
     */
    private String score;

    /**
     * 评估结论[后端]
     */
    private String result;



}

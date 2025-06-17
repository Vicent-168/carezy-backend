package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.base.PageSearch;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 住院护理化验单  搜索VO
 *
 * @author myy
 * @date 2024-11-07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LaboratoryFormIndexSearchVo extends PageSearch<LaboratoryFormIndexVo> {
    /**报告唯一编码**/
    @NotBlank(message = "报告唯一编码不能为空")
    private String BGDH;
    /**医疗机构ID**/
    private String yljgid;

}

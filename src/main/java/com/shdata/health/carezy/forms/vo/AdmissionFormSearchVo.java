package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 病人护理入院告知内容表  搜索VO
 *
 * @author myy
 * @date 2024-12-16
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdmissionFormSearchVo extends PageSearch<AdmissionFormVo> {

}

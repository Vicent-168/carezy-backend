package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 病人护理记录单文书索引表  搜索VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsIndexSearchVo extends PageSearch<FormsIndexVo> {

}

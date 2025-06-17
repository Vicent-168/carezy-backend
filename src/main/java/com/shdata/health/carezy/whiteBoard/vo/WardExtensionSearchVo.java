package com.shdata.health.carezy.whiteBoard.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 病区扩展表  搜索VO
 *
 * @author ljt
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WardExtensionSearchVo extends PageSearch<WardExtensionVo> {

}

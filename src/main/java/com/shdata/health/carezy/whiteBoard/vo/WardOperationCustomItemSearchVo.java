package com.shdata.health.carezy.whiteBoard.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 病区运营_自定义项目表  搜索VO
 *
 * @author ljt
 * @date 2024-10-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WardOperationCustomItemSearchVo extends PageSearch<WardOperationCustomItemVo> {

}

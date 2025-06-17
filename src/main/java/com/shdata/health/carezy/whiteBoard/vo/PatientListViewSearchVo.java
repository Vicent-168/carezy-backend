package com.shdata.health.carezy.whiteBoard.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 患者列表视图  搜索VO
 *
 * @author ljt
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientListViewSearchVo extends PageSearch<PatientListViewVo> {

}

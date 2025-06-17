package com.shdata.health.carezy.whiteBoard.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 病床字典表  搜索VO
 *
 * @author ljt
 * @date 2024-10-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientBedDictionarySearchVo extends PageSearch<PatientBedDictionaryVo> {

}

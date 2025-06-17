package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 患者转运表  搜索VO
 *
 * @author myy
 * @date 2024-11-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientTransportSearchVo extends PageSearch<PatientTransportVo> {

}

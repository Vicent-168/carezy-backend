package com.shdata.health.carezy.whiteBoard.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 护理病人登记信息  搜索VO
 *
 * @author ljt
 * @date 2024-10-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientRegistrationInformationSearchVo extends PageSearch<PatientRegistrationInformationVo> {

}

package com.shdata.health.carezy.patientmanage.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 住院护理化验单  搜索VO
 *
 * @author panwei
 * @date 2024-10-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LaboratorySearchVo extends PageSearch<LaboratoryVo> {
    private String brid;

}

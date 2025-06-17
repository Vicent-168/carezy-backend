package com.shdata.health.carezy.forms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VitalSignsFromsVo {

    private List<PhysicalSignsFormVo> physicalSignsFormList;
    private List<InAndOutFormsVo> inAndOutFormsList;
    private List<TuzhangFormsVo>  tuzhangFormsList;
    private List<AllergyFormsVo>  allergyFormsList;
    private List<DayCountVo> dayCountList;




}

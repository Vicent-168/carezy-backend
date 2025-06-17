package com.shdata.health.carezy.forms.vo;


import com.shdata.health.carezy.forms.mapper.LaboratoryFormIndexMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LaboratoryFormVo {
    /**
     * 化验单索引
     */
    private LaboratoryFormIndexVo laboratoryFormIndexVo;

    /**
     * 化验单明细
     */
    private List<LaboratoryFormDetasilsVo> laboratoryFormDetasilsList;
}

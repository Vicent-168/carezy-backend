package com.shdata.health.carezy.patientmanage.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RegistInformationBchVo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String brid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String yszd;
    private String mzzd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String gm;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NameField(target = "xb", type = DictType.DICT, key = "DIC_XB")
    private String xb;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer nl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String xm;
    //病区病床号映射
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, List<String>> bch;
}

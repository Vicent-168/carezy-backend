package com.shdata.health.carezy.forms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsSaveVoo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单索引
     */
    private FormsIndexVoo recordIndex;

    /**
     * 表单明细
     */
    private Map<String,Object> detailMap;
}

package com.shdata.health.carezy.forms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DoFormsVo {
    /**
     * 文书名称
     */
    private String wsmc;

    /**
     * 文书编码
     */
    private String wsid;

}

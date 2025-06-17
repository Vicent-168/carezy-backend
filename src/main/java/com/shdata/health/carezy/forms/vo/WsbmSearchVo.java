package com.shdata.health.carezy.forms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WsbmSearchVo {

    /**
     * 文书ID
     */
    private String wsid;
    /**
     * 文书编码
     */
    private String wsbm;
    /**
     * 分类代码
     */
    private String fldm;

    /**
     * 医疗机构ID
     */
    private String yljgid;

}

package com.shdata.health.carezy.forms.vo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdditionalInfoVo {
    /**
     * 名称
     */
    private String name;

    /**
     * 结果
     */
    private String result;
}

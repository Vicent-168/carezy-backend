package com.shdata.health.carezy.forms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 保单保存相关提示
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsSaveTipsVo {

    /**
     * 保存或更新的提示信息
     */
    private String msg;

    /**
     * 保存或更新的记录id
     */
    private String id;
}

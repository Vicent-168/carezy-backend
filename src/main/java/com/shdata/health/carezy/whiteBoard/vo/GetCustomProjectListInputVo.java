package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年11月21日 17:51
 */
@Data
public class GetCustomProjectListInputVo {
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
    /**
     * 模块类型,多个类型用逗号隔开
     */
    @NotBlank(message = "模块类型不能为空")
    private String moduleType;
}

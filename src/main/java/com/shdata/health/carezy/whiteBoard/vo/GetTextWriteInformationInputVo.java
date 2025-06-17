package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月21日 13:50
 */
@Data
public class GetTextWriteInformationInputVo {
    /**
     * 模块类型,多个类型用逗号隔开
     */
    @NotBlank(message = "模块类型不能为空")
    private String moduleType;
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
}

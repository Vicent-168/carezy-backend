package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月23日 10:06
 */
@Data
public class RoomAndScreenInfoInputVo {
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
    /**
     * 模块类型
     */
    @NotBlank(message = "模块类型不能为空")
    private String moduleType;

}

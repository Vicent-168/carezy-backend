package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月21日 13:54
 */
@Data
public class WardBaseInfoInputVo {
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
}

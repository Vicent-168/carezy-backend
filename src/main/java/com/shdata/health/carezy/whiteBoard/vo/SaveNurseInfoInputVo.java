package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年11月06日 9:42
 */
@Data
public class SaveNurseInfoInputVo {
    /**
     * 病区护士长id
     */
    private String headNurseId;
    /**
     * 病区副护士长id
     */
    private String deputyNurseId;
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
}

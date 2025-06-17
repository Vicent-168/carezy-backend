package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月25日 15:15
 */
@Data
public class TotalBedInputVo {
    /**
     * 病区运营id
     */
    @NotBlank(message = "病区运营id不能为空")
    private String id;
    /**
     * 总床位数
     */
    @NotNull(message = "总床位数不能为空")
    private Long totalBed;
}

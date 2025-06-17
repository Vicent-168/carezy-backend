package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年12月11日 15:35
 */
@Data
public class GetWardScreenDataInputVo {
    /**
     * 模块类型,多个类型用逗号隔开
     */
    @NotBlank(message = "模块类型不能为空")
    private String moduleType;
}

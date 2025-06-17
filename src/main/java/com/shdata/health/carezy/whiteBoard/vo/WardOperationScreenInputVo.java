package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年10月18日 10:26
 */
@Data
public class WardOperationScreenInputVo {
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
    /**
     * 标题
     */
    private String title;
    /**
     * 保存列表 左侧list长度4 右侧list长度6
     */
    private List<WardOperationScreen> wardOperationScreens;

    @Data
    public static class WardOperationScreen {
        /**
         * 字段名
         */
        private String fieldNames;
        /**
         * 值
         */
        private String values;

    }

}

package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 待办事项
 *
 * @author ljt
 * @date 2024年10月21日 14:30
 */
@Data
public class ToDoItemsInputVo {
    /**
     * 操作类型 0:保存 1:删除
     */
    @NotBlank(message = "操作类型不能为空")
    private String operationType;
    /**
     * 待办表id
     */
    private String id;
    /**
     * 待办时间 格式:yyyy-MM-dd HH:mm
     */
    private String todoDate;
    /**
     * 待办事项
     */
    private String todoItem;
    /**
     * 病人床号列表 多个值使用逗号隔开
     */
    private String bedNumber;
}

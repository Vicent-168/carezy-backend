package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月21日 15:26
 */
@Data
public class ToDoItemsOutputVo {
    /**
     * 待办时间 格式:HH:mm
     */
    private String todoDate;
    /**
     * 待办事项
     */
    private String todoItem;
    /**
     * 床号 使用逗号分隔
     */
    private String bedNumber;
}

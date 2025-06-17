package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年10月21日 14:52
 */
@Data
public class SaveLargeScreenDataInputVo {
    /**
     * 大屏信息
     */
    private LargeScreenDataInput largeScreenDataInput;
    /**
     * 待办事项
     */
    private List<ToDoItemsInputVo> toDoItemsInputVoList;
}

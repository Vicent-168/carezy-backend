package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年11月21日 17:35
 */
@Data
public class SaveTextWriteInputVo {
    /**
     * 病区id
     */
    private String ward;
    /**
     * 备忘录模块项目id
     */
    @NotBlank(message = "备忘录模块项目id不能为空")
    private String memoMPId;
    /**
     * 注意事项模块项目id
     */
    @NotBlank(message = "注意事项模块项目id不能为空")
    private String noticeMPId;
    /**
     * 待办事项模块项目id
     */
    @NotBlank(message = "待办事项模块项目id不能为空")
    private String todoMPId;
    /**
     * 注意事项(带格式)
     */
    private String notice;
    /**
     * 备忘录(带格式)
     */
    private String memo;
    /**
     * 待办列表
     */
    private List<TodoTasks> todoTasksList;


    /**
     * 待办
     */
    @Data
    public static class TodoTasks {
        /**
         * 待办日期 1:今日,2:明日,3:后日
         */
        private String todoDateType;
        /**
         * 待办事项列表
         */
        private List<ToDoItems> toDoItemsList;
    }

    /**
     * 待办事项
     */
    @Data
    public static class ToDoItems {
        /**
         * 待办时间 格式 HH:mm
         */
        private String todoDate;
        /**
         * 待办事项
         */
        private String todoItem;
        /**
         * 患者
         */
        private String bedNumber;
    }
}

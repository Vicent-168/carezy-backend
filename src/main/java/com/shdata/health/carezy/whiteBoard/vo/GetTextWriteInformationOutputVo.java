package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ljt
 * @date 2024年10月21日 15:25
 */
@Data
public class GetTextWriteInformationOutputVo {
    /**
     * 备忘录模块项目id
     */
    private String memoMPId;
    /**
     * 注意事项模块项目id
     */
    private String noticeMPId;
    /**
     * 待办事项模块项目id
     */
    private String todoMPId;
    /**
     * 注意事项
     */
    private String notice;
    /**
     * 备忘录
     */
    private String memo;
    /**
     * 待办列表
     */
    private List<GetTextWriteInformationOutputVo.TodoTasks> todoTasksList;
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
        private List<GetTextWriteInformationOutputVo.ToDoItems> toDoItemsList;
    }

    /**
     * 待办事项
     */
    @Data
    public static class ToDoItems {
        /**
         * 待办时间 格式:HH:mm
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

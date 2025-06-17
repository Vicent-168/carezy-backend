package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年11月21日 11:00
 */
@Data
public class GetCustomInfoOutputVo {
    /**
     * 模块类型
     */
    private String moduleType;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 项目列表
     */
    private List<GetCustomInfoOutputVo.Project> projectList;

    @Data
    public static class Project {
        /**
         * 项目主键id
         */
        private String projectId;
        /**
         * 模块项目表主键id
         */
        private String moduleProjectId;
        /**
         * 项目名
         */
        private String customProjectName;
        /**
         * 项目值
         */
        private String customValue;
        /**
         * 顺序
         */
        private String order;
        /**
         * 是否显示 true:显示 false:不显示
         */
        private Boolean isShow;
        /**
         * 是否为自定义项目 true:自定义项目 false:非自定义项目
         */
        private Boolean isCustom;
    }

}

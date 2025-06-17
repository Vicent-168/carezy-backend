package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年11月25日 15:40
 */
@Data
public class SaveCustomInfoInputVo {
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
    /**
     * 模块列表
     */
    private List<Module> moduleList;

    @Data
    public static class Module {
        /**
         * 模块类型
         */
        @NotBlank(message = "模块类型不能为空")
        private String moduleType;
        /**
         * 模块名
         */
        @NotBlank(message = "模块名不能为空")
        private String moduleName;
        /**
         * 项目列表
         */
        private List<Project> projectList;
    }


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
        @NotBlank(message = "项目名不能为空")
        private String customProjectName;
        /**
         * 项目值(自定义项目时需传入)
         */
        private String customValue;
        /**
         * 顺序
         */
        @NotBlank(message = "顺序不能为空")
        private String order;
        /**
         * 是否显示 true:显示 false:不显示
         */
        @NotNull(message = "是否显示不能为空")
        private Boolean isShow;
        /**
         * 是否自定义项目 true:是 false:不是
         */
        @NotNull(message = "是否自定义项目不能为空")
        private Boolean isCustom;
    }
}

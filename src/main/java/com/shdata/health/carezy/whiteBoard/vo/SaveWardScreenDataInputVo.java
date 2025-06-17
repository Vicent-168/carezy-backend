package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年12月11日 14:07
 */
@Data
public class SaveWardScreenDataInputVo {
    /**
     * 模块列表
     */
    private List<SaveWardScreenDataInputVo.Module> moduleList;

    @Data
    public static class Module {
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
        private List<SaveWardScreenDataInputVo.Project> projectList;
    }


    @Data
    public static class Project {
        /**
         * 模块项目表主键id
         */
        private String moduleProjectId;
        /**
         * 项目值
         */
        private String customValue;
        /**
         * 项目值2
         */
        private String customValue2;
        /**
         * 项目值3
         */
        private String customValue3;
    }
}

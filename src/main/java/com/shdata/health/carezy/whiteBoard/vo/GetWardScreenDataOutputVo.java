package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年12月11日 15:36
 */
@Data
public class GetWardScreenDataOutputVo {

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
    private List<GetWardScreenDataOutputVo.Project> projectList;


    @Data
    public static class Project {
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
         * 项目值2
         */
        private String customValue2;
        /**
         * 项目值3
         */
        private String customValue3;
        /**
         * 单位
         */
        private String unit;
    }
}

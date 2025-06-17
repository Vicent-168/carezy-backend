package com.shdata.health.carezy.whiteBoard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 大屏刷新枚举
 *
 * @author ljt
 * @date 2024年11月12日 13:53
 */
@Getter
@AllArgsConstructor
public enum LargeScreenModuleEnum {
    HLDP_01("hldp01", "carezy01"),
    HLDP_09("hldp09", "carezy03"),
    HLDP_37("hldp37", "carezy07"),
    HLDP_49("hldp49", "carezy26"),
    ;

    /**
     * 模块id
     */
    private final String moduleId;
    /**
     * 表
     */
    private final String mybatisId;
}

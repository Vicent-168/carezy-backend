package com.shdata.health.carezy.common.dict;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 通用字典表Vo
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DictVo {
    /**
     * 字典代码
     */
    private String code;
    /**
     * 字典名称
     */
    private String name;

    /**
     * 层级
     */
    private Integer cj;
    /**
     * 上级代码
     */
    private String sjdm;

    /**
     * 孩子集合
     */
    private List<DictVo> children;
}

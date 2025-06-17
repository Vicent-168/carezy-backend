package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 护理任务列表对象  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HlrwVo {
    /** 文书索引主键 */
    private String id;
    /** 任务名称 */
    private String rwmc;
    /** 文书id */
    private String wsid;
    /** 文书编码 */
    private String wsbm;
    /** 跳转链接 *//*
    private String url;
     *//** 组件名称 *//*
    private String compName; */
    /** 文书分类 */
    private String wsfl;
}

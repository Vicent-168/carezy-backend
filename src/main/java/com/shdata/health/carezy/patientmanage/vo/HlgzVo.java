package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 护理告知对象  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HlgzVo {

    /** id */
    private String id;
    /** 日期 */
    private String rq;
    /** 文书名称 */
    private String hlgzmc;
    /** 文书编码 */
    private String wsbm;
    /** 文书id */
    private String wsid;
    /** 跳转链接 *//*
    private String url;
     *//** 组件名称 *//*
    private String compName; */
    /** 文书分类 */
    private String wsfl;
}

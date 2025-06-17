package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BqyyMkXmVo implements Serializable {

    /**
     * 模块项目ID
     */
    private String id;
    /**
     * 项目模块ID
     */
    private String mkid;
    /**
     * 项目ID
     */
    private String xmid;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * 计算BEAN
     */
    private String bean;
    /**
     * 参数
     */
    private String cs;
    /**
     * 刷新间隔
     */
    private long sxjg;
    /**
     * 项目顺序
     */
    private String xmsx;
    /**
     * 是否展示 0展示、1不展示
     */
    private String sfzs;
    /**
     * 模块类型
     */
    private String mklx;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

}

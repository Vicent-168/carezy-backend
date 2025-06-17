package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 病区运营模块表  VO
 *
 * @author xgb
 * @date 2024-11-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BqyyMkbVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 模块类型
     */
    private String mklx;
    /**
     * 模块名称
     */
    private String mkmc;
    /**
     * 模块项目表
     */
    private List<BqyyMkxmbVo> mkxmbList;
}

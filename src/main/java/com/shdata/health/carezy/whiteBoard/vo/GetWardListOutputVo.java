package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年10月24日 10:22
 */
@Data
public class GetWardListOutputVo {
    /**
     * 病区id
     */
    private String wardId;
    /**
     * 病区名称
     */
    private String wardName;
}

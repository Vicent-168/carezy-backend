package com.shdata.health.carezy.whiteBoard.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月18日 10:22
 */
@Data
public class WardRoomDetailsInputVo {
    /**
     * 总床位数
     */
    private String totalBed;
    /**
     * 操作 0:删除 1:保存
     */
    @NotBlank(message = "操作类型不能为空")
    private String operationType;
    /**
     * 病区id
     */
    @NotBlank(message = "病区id不能为空")
    private String ward;
    /**
     * 主键id operationType=0时必传
     */
    private String id;
    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 对应床位号,多个用逗号隔开
     */
    private String bedNumbers;
}

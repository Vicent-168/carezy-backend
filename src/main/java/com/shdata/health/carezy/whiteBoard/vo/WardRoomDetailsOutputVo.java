package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

/**
 * @author ljt
 * @date 2024年10月29日 17:05
 */
@Data
public class WardRoomDetailsOutputVo {
    /**
     * 主键id
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
    /**
     * 床位数
     */
    private int bedCount;
}

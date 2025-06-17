package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ljt
 * @date 2024年10月23日 10:08
 */
@Data
public class RoomAndScreenInfoOutputVo {
    /**
     * 病房信息列表
     */
    private List<WardRoomDetailsOutputVo> roomList;

}

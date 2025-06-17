package com.shdata.health.carezy.whiteBoard.vo;

import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;

import java.util.List;

/**
 * @author ljt
 * @date 2024年10月18日 10:09
 */
@Data
public class WardBaseInfoOutputVo {
    /**
     * 病区总床位数
     */
    private String totalBeds;
    /**
     * 病区护士长姓名
     */
    @NameField(type = DictType.User, target = "bqhszxm")
    private String headNurseName;
    /**
     * 病区副护士长姓名
     */
    @NameField(type = DictType.User, target = "bqfhszxm")
    private String deputyNurseName;
    /**
     * 病房信息列表
     */
    private List<WardRoomDetailsOutputVo> roomList;
}

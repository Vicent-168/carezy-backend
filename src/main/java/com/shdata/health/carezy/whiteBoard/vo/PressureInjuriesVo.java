package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PressureInjuriesVo {
    /**
     * 病区编码
     */
    private String bqid;
    /**
     * 病床号
     */
    private String bch;
    /**
     * 风险等级
     */
    private String fxdj;

    /**
     * 更新时间
     */
    private String updateTime;
}

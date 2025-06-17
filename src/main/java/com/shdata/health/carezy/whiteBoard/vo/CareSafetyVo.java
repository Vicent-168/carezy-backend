package com.shdata.health.carezy.whiteBoard.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CareSafetyVo {
    /**
     * 病区编码
     */
    private String bqid;
    /**
     * 病床号
     */
    private String bch;
    /**
     * 文书编码
     */
    private String wsid;
    /**
     * 风险发生类别
     */
    private String fxfslb;
    /**
     * 导管类别
     */
    private String dglx;


}

package com.shdata.health.carezy.forms.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@Accessors(chain = true)
public class DayCountVo {


    /**
     * 入院天数
     */
    private Object ryts;

    /**
     * 手术天数
     */
    private List<Object> ssts;

    /**
     * 测量时间
     */
    private Object rq;


    @JsonProperty("XM_RYTS")
    public Object getRyts() {
        return ryts;
    }

    public void setRyts(Object ryts) {
        this.ryts = ryts;
    }

    @JsonProperty("XM_SSTS")
    public Object getSsts() {
        return ssts;
    }

    public void setSsts(List<Object> ssts) {
        this.ssts = ssts;
    }

    @JsonProperty("XM_CLSJ")
    public Object getRq() {
        return rq;
    }

    public void setRq(Object rq) {
        this.rq = rq;
    }
}

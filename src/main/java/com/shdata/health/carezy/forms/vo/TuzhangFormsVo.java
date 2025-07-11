package com.shdata.health.carezy.forms.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
public class TuzhangFormsVo {
    /**
     * 测量时间
     */
    private Object rq;
    /**
     * 时间阶段
     */
    private Object time;
    /**
     * 图章名称
     */
    private Object tzName;

    @JsonProperty("XM_CLRQ")
    public Object getRq() {
        return rq;
    }

    public void setRq(Object rq) {
        this.rq = rq;
    }
    @JsonProperty("XM_CLSJD")
    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    @JsonProperty("XM_TZMC")
    public Object getTzName() {
        return tzName;
    }

    public void setTzName(Object tzName) {
        this.tzName = tzName;
    }
}

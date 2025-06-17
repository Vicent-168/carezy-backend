package com.shdata.health.carezy.forms.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
public class InAndOutFormsVo {
    /**
     * 测量时间
     */
    private Object rq;
    /**
     * 时间阶段
     */
    private Object time;
    /**
     * 大便次数
     */
    private Object dbcs;
    /**
     * 小便
     */
    private Object xb;
    /**
     * 出量
     */
    private Object cl;

    /**
     * 入量
     */
    private Object rl;

    /**
     * 体重
     */
    private Object tz;

    /**
     * 血压
     */
    private Object xy;

    @JsonProperty("XM_CLSJ")
    public Object getRq() {
        return rq;
    }

    public void setRq(Object rq) {
        this.rq = rq;
    }
    @JsonProperty("XM_SJJD")
    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    @JsonProperty("XM_DBCS")
    public Object getDbcs() {
        return dbcs;
    }

    public void setDbcs(Object dbcs) {
        this.dbcs = dbcs;
    }

    @JsonProperty("XM_XB")
    public Object getXb() {
        return xb;
    }

    public void setXb(Object xb) {
        this.xb = xb;
    }

    @JsonProperty("XM_CL")
    public Object getCl() {
        return cl;
    }

    public void setCl(Object cl) {
        this.cl = cl;
    }

    @JsonProperty("XM_RL")
    public Object getRl() {
        return rl;
    }

    public void setRl(Object rl) {
        this.rl = rl;
    }

    @JsonProperty("XM_TZ")
    public Object getTz() {
        return tz;
    }

    public void setTz(Object tz) {
        this.tz = tz;
    }

    @JsonProperty("XM_XY")
    public Object getXy() {
        return xy;
    }

    public void setXy(Object xy) {
        this.xy = xy;
    }
}

package com.shdata.health.carezy.forms.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Accessors(chain = true)
public class PhysicalSignsFormVo {

    /**
     * 测量时间
     */
    private Object rq;
    /**
     * 时间阶段
     */
    private Object time;
    /**
     * 体温测量部位
     */
    private Object bw;
    /**
     * 体温
     */
    private Object tw;
    /**
     * 脉搏
     */
    private Object mb;
    /**
     * 呼吸
     */
    private Object hx;
    /**
     * 复测疼痛
     */
    private Object fctt;

    /**
     * 复测体温
     */
    private Object fctw;

    /**
     * 心率
     */
    private Object xl;

    /**
     *无创收缩压
     */
    private Object wcssy;

    /**
     *无创舒张压
     */
    private Object wcszy;

    @JsonProperty("XM_WCSZY")
    public Object getWcszy() {
        return wcszy;
    }

    public void setWcszy(Object wcszy) {
        this.wcszy = wcszy;
    }

    @JsonProperty("XM_WCSSY")
    public Object getWcssy() {
        return wcssy;
    }

    public void setWcssy(Object wcssy) {
        this.wcssy = wcssy;
    }

    @JsonProperty("XM_XL")
    public Object getXl() {
        return xl;
    }

    public void setXl(Object xl) {
        this.xl = xl;
    }

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

    @JsonProperty("XM_CLTWBW")
    public Object getBw() {
        return bw;
    }

    public void setBw(Object bw) {
        this.bw = bw;
    }

    @JsonProperty("XM_TW")
    public Object getTw() {
        return tw;
    }

    public void setTw(Object tw) {
        this.tw = tw;
    }

    @JsonProperty("XM_MB")
    public Object getMb() {
        return mb;
    }

    public void setMb(Object mb) {
        this.mb = mb;
    }

    @JsonProperty("XM_HX")
    public Object getHx() {
        return hx;
    }

    public void setHx(Object hx) {
        this.hx = hx;
    }

    @JsonProperty("XM_FCTT")
    public Object getFctt() {
        return fctt;
    }

    public void setFctt(Object fctt) {
        this.fctt = fctt;
    }

    @JsonProperty("XM_FCTW")
    public Object getFctw() {
        return fctw;
    }

    public void setFctw(Object fctw) {
        this.fctw = fctw;
    }

}

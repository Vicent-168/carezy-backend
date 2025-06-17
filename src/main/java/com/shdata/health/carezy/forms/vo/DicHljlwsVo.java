package com.shdata.health.carezy.forms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.carezy.patientmanage.vo.FormCodeNameEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
/**
 * 护理记录单文书字典  VO
 *
 * @author myy
 * @date 2024-10-21
 */

@NoArgsConstructor
@Accessors(chain = true)
public class DicHljlwsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否已做此类表单
     */
    private String isDo;

    /** 主键ID */
    private String id;
    /** 文书名称 */
    @NotBlank
    private String mc;
    /** 文书序号 */
    @NotNull
    private long xh;
    /** 文书分类名称 */
    @NotBlank
    private String fl;
    /** 文书分类代码 */
    @NotBlank
    private String fldm;
    /** 文书分类序号 */
    @NotNull
    private long flxh;
    /** 版本号 */
    @NotNull
    private long bbh;
    /** 系统关联页面ID（关联菜单信息表通过菜单表进行授权使用） */
    private String url;
    /** 文书类型 */
    private String lx;
    /** 量表类型 */
    private String lblx;
    /** 启停标记 */
    @NotBlank
    private String qtbj;
    /** 启用日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date qyrq;
    /** 停用日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tyrq;
    /**文书编码**/
    @NotBlank
    private String wsbm;
    /**文书id**/
    @NotBlank
    private String wsid;
    /**是否批量**/
    private String sfpldy;
    /**风险名称**/
    private String fxmc;



    @NotNull
    public String getIsDo() {
        return isDo;
    }

    public void setIsDo(String isDo) {
        this.isDo = isDo;
    }


    public int getFldmAsInt() {
        // 将fldm转换为int类型，这里需要处理可能的异常
        try {
            return Integer.parseInt(fldm);
        } catch (NumberFormatException e) {
            // 处理转换异常，例如返回一个默认值或者记录日志
            return 0;
        }
    }

    public String getFxmc(String wsbm) {
        return FormCodeNameEnum.getNameByCode(wsbm);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank String getMc() {
        return mc;
    }

    public void setMc(@NotBlank String mc) {
        this.mc = mc;
    }

    @NotNull
    public long getXh() {
        return xh;
    }

    public void setXh(@NotNull long xh) {
        this.xh = xh;
    }

    public @NotBlank String getFl() {
        return fl;
    }

    public void setFl(@NotBlank String fl) {
        this.fl = fl;
    }

    public @NotBlank String getFldm() {
        return fldm;
    }

    public void setFldm(@NotBlank String fldm) {
        this.fldm = fldm;
    }

    @NotNull
    public long getFlxh() {
        return flxh;
    }

    public void setFlxh(@NotNull long flxh) {
        this.flxh = flxh;
    }

    @NotNull
    public long getBbh() {
        return bbh;
    }

    public void setBbh(@NotNull long bbh) {
        this.bbh = bbh;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getLblx() {
        return lblx;
    }

    public void setLblx(String lblx) {
        this.lblx = lblx;
    }

    public @NotBlank String getQtbj() {
        return qtbj;
    }

    public void setQtbj(@NotBlank String qtbj) {
        this.qtbj = qtbj;
    }

    public Date getQyrq() {
        return qyrq;
    }

    public void setQyrq(Date qyrq) {
        this.qyrq = qyrq;
    }

    public Date getTyrq() {
        return tyrq;
    }

    public void setTyrq(Date tyrq) {
        this.tyrq = tyrq;
    }

    public @NotBlank String getWsbm() {
        return wsbm;
    }

    public void setWsbm(@NotBlank String wsbm) {
        this.wsbm = wsbm;
        // 在设置wsbm之后立即调用getFxmc方法
        this.fxmc = getFxmc(wsbm);
    }

    public @NotBlank String getWsid() {
        return wsid;
    }

    public void setWsid(@NotBlank String wsid) {
        this.wsid = wsid;
    }

    public String getSfpldy() {
        return sfpldy;
    }

    public void setSfpldy(String sfpldy) {
        this.sfpldy = sfpldy;
    }

    public void setFxmc(String fxmc) {
        this.fxmc = fxmc;
    }

}

package com.shdata.health.carezy.forms.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 病人护理记录单明细项表  VO
 *
 * @author myy
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人文书索引唯一键 */
    @NotBlank
    private String blwsid;
    /** 项目代码 */
    @NotBlank
    private String xmdm;
    /** 项目名称 */
    @NotBlank
    private String xmmc;
    /** 项目值说明 */
    @NotBlank
    private String xmzsm;
    /** 项目值 */
    @NotBlank
    private Object xmz;
    /** 分值 */
    private long fz;
    /** 备注 */
    private String bz;

    @Override
    public String toString() {
        return "{" +
                "id:\"" + id + '\"' +
                ", blwsid:\"" + blwsid + '\"' +
                ", xmdm:\"" + xmdm + '\"' +
                ", xmmc:\"" + xmmc + '\"' +
                ", xmzsm:\"" + xmzsm + '\"' +
                ", xmz:" + xmz +
                ", fz:" + fz +
                ", bz:\"" + bz + '\"' +
                '}';
    }
}

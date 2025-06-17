package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.serializer.jackson.DictFormat;
import com.shdata.health.common.serializer.jackson.DictType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 导管置管_导管维护表  VO
 *
 * @author myy
 * @date 2024-11-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ConduitMaintenanceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人文书索引唯一键 */
    @NotBlank
    private String blwsid;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /** 维护时间 日期格式:yyyy-MM-dd */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date whsj;
    /** 导管名称 */
    private String dgmc;
    /** 导管部位 */
    private String dgbw;
    /** 导管编号 */
    private String dgbh;
    /** 使用情况 */
    private String syqk;
    /** 是否异常 */
    private String sfyc;
    /** 维护内容 */
    private String whnr;
    /** 异常选择 */
    private String ycxz;
}

package com.shdata.health.carezy.forms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.common.serializer.jackson.DictFormat;
import com.shdata.health.common.serializer.jackson.DictType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 病人护理记录单文书索引表  VO
 *
 * @author dingwentao
 * @date 2024-12-03
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormsIndexVoo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /** 病历文书ID */
    @NotBlank
    //@NameField(type = DictType.CUSTOM, key = "ID", target = "wsmc", bean = FormNameFieldConverter.class)
    //@DictFormat(dictType = DictType.CUSTOM, dictKey = "ID")
    private String wsid;

    private String wsmc;
    /** 文书编码 */
    @NotBlank
    private String wsbm;
    /** 文书分类 */
    @NotBlank
    private String wsfl;
    /** 总分 */
    private long zf;
    /** 结论 */
    private String jl;
    /** 医疗机构ID */
    //@NameField(type = DictType.Yljg,target = "yljgmc")
    private String yljgid;
    /** 医疗机构名称 */
    //private String yljgmc;
    /** 科室ID */
    //@NameField(type = DictType.Dept,target = "ksmc")
    private String ksid;
    /** 科室名称 */
    //private String ksmc;
    /** 日期 日期格式:yyyy-MM-dd **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @NotBlank
    private Date rq;
    /** 拔管时间:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bgsj;
    /** 监控状态 */
    //@NameField(type = DictType.DICT,target = "jkztmc", key = "DIC_JKZT")
    private String jkzt;
    /**监控状态名称*/
    //private String jkztmc;
    /** 签名医生ID */
    //@NameField(type = DictType.User,target = "qmysmc")
    private String qmysid;
    /** 签名医生名称 */
    //private String qmysmc;
    /** 创建时间 */
    private Date createTime;
    /**更新时间*/
    private String updateTime;

    /**
     * 风险等级
     */
    //@DictFormat(dictType = DictType.DICT, dictKey = "DIC_FXDJ")
    private String fxdj;
    /**
     *风险发生类别
     */
    //@DictFormat(dictType = DictType.DICT, dictKey = "DIC_FXFSLB")
    private String fxfslb;

    /**
     * 停报时间
     */
    private Date tbsj;

    /**
     * 导管类型
     */
    @DictFormat(dictType = DictType.DICT, dictKey = "DIC_DGLX")
    private String dglx;
}

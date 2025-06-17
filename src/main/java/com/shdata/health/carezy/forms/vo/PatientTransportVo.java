package com.shdata.health.carezy.forms.vo;

import com.shdata.health.common.mybatis.NameField;
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
 * 患者转运表  VO
 *
 * @author myy
 * @date 2024-11-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientTransportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;
    /** 病人唯一号 */
    @NotBlank
    private String brid;
    /**文书id**/
    @NotBlank
    private String wsid;
    /**文书编码**/
    @NotBlank
    private String wsbm;
    /** 病人文书索引唯一键 */
    private String blwsid;
    /** 转运日期 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date zyrq;
    /** 患者意识 */
    @NameField(type = DictType.DICT,target = "hzysmc",key="DIC_HZYS")
    private String hzys;
    /**患者意识名称*/
    private String hzysmc;
    /** 导管情况 */
    @NameField(type = DictType.DICT,target = "dgqkmc",key="DIC_DGQK")
    private String dgqk;
    /** 导管情况名称 */
    private String dgqkmc;
    /** 皮肤情况 */
    @NameField(type = DictType.DICT,target = "pfqkmc",key="DIC_PFQK")
    private String pfqk;
    /** 皮肤情况名称 */
    private String pfqkmc;
    /** 皮肤情况_压疮部位 */
    private String pfqkYcbw;
    /** 皮肤情况_压疮面积 */
    private String pfqkYcmj;
    /** 皮肤情况_压疮创面情况 */
    private String pfqkYccmqk;
    /** 药品 */
    @NameField(type = DictType.DICT,target = "ypmc",key="DIC_YP")
    private String yp;
    /** 药品名称 */
    private String ypmc;
    /** 物品 */
    @NameField(type = DictType.DICT,target = "wpmc",key="DIC_WP")
    private String wp;
    /** 物品名称 */
    private String wpmc;
    /** 特殊交班 */
    @NameField(type = DictType.DICT,target = "tsjbmc",key="DIC_TSJB")
    private String tsjb;
    /** 特殊交班名称 */
    private String tsjbmc;
    /** 转出科室 */
    @NameField(type = DictType.Dept,target = "zcksmc")
    private String zcks;
    /** 转出科室名称 */
    private String zcksmc;
    /** 评估护士 */
    @NameField(type = DictType.User,target = "pghsxm")
    private String pghs;
    /** 评估护士姓名 */
    private String pghsxm;
}

package com.shdata.health.carezy.patientmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import com.shdata.health.carezy.forms.vo.FormsIndexVoo;
import com.shdata.health.common.mybatis.NameField;
import com.shdata.health.common.serializer.jackson.DictType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 病人生命体征  VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientVitalSignInfosVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /** 患者ID */
    private String id;
    /** 病床号*/
    private String bch;
    /**  姓名*/
    private String xm;
    /** 住院号*/
    private String zyh;
    /** 年龄*/
    private long nl;
    /** 入院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rysj;
    /** 出院时间 日期格式:yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cysj;
    /** 入院天数 */
    private Integer ryts;
    /** 性别*/
    @NameField(type = DictType.DICT, target = "xb", key = "DIC_XB")
    private String xb;
    /** 出身日期 日期格式:yyyy-MM-dd*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date csrq;
    /** 床位医生 */
    @NameField(type = DictType.User, target = "ysxm")
    private String ysid;
    private String ysxm;
    /** 体温 */
    private String xmTw;
    /** 脉搏 */
    private String xmMb;
    /** 心率 */
    private String xmXl;
    /** 呼吸 */
    private String xmHx;
    /** 遵医嘱血氧 */
    private String xmyzxYang;
    /** 收缩压 */
    private String xmWcssy;
    /** 舒张压 */
    private String xmWcszy;

    /** 测量值 */
    //private String XM_CLZ;




    /**
     * 患者登记表
     */
    //private RegistInformationVo registInformationVo;

    /**
     * 表单索引
     */
    private FormsIndexVoo recordIndex;

    /**
     * 表单明细
     */
    private Map<String,Object> detailMap;
}

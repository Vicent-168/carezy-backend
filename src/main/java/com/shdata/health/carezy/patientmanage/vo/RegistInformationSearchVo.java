package com.shdata.health.carezy.patientmanage.vo;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.vo.DicHljlwsVo;
import com.shdata.health.common.base.PageSearch;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 病人登记信息  搜索VO
 *
 * @author panwei
 * @date 2024-10-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RegistInformationSearchVo extends PageSearch<RegistInformationVo> {

    //病人id
    private String brid;

    //患者状态
    private String patientStatus;

    //病区
    private String wards;

    @NotBlank
    //入院0,点出院后1
    private String flag = "0";

    /** 搜索字段 */
    private String search;

    //开始日期
    private String startDate;

    //结束日期
    private String endDate;

    //更新时间
    private long updateTimestamp;

    private String yljgid;

    private List<String> bqList;


//    //压疮wsid
//    private String ycwsid;
//    //跌倒wsid
//    private String ddwsid;
//    //导管wsid
//    private String dgwsid;
    private List<DicHljlwsVo> riskFormList;
    private String fswsid;
    //风险类型，1：跌倒，2：压疮，3：导管
    private String riskType;

}

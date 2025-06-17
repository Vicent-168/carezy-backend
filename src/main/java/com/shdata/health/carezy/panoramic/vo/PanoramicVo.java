package com.shdata.health.carezy.panoramic.vo;

import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import com.shdata.health.carezy.forms.vo.FormsSaveLineVo;
import com.shdata.health.carezy.forms.vo.FormsSaveVo;
import com.shdata.health.carezy.patientmanage.vo.LaboratoryVo;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationVo;
import com.shdata.health.carezy.patientmanage.vo.TaskVo;
import com.shdata.health.common.base.PageData;
import lombok.Data;

import java.util.List;

@Data
public class PanoramicVo {

    //基本信息
    private RegistInformationVo baseInfo;
    //风险评估
    private List<FormsIndexVo> riskEvaluation;
    //体征1-体温等
    private List<FormsSaveLineVo> vitalSigns1;

    //体征2-出入量
    private List<FormsSaveLineVo> vitalSigns2;

    private List<TaskVo> taskList;

    //血压
    private String xya = "";
    //血氧
    private String xyang = "";
    //大便
    private String db = "";
    //小便
    private String xb = "";
    //24小时排出量
    private String out24 = "";
    //24小时入量
    private String in24 = "";
}

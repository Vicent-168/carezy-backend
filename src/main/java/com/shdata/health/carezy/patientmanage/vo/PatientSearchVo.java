package com.shdata.health.carezy.patientmanage.vo;

import com.shdata.health.carezy.common.utils.DataUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 病人生命体征搜索VO
 *
 * @author dingwentao
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PatientSearchVo {
    /** 患者筛选类型 */
    private String hzzt;
    /** 病区id */
    private String bqid;
    /** 病区ids */
    private List<String> bqList;
    /** 护士id *//*
    private String hsid; */
    /** 医疗机构id */
    private String yljgid;
    /** 文书编码 */
    private String wsbm;

}

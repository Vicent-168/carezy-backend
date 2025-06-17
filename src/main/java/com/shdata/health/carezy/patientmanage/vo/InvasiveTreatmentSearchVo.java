package com.shdata.health.carezy.patientmanage.vo;

import com.shdata.health.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 有创治疗列表 搜索VO
 *
 * @author Dingwentao
 * @date 2024-10-30
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InvasiveTreatmentSearchVo extends PageSearch<InvasiveTreatmentVo> {
    /** 患者筛选类型 */
    private String hzzt;
    /** 病区 */
    private String bqid;
    /** 病区ids */
    private List<String> bqList;
    /** 护士id */
    //private String hsid;
    /** 医疗机构id */
    private String yljgid;
}

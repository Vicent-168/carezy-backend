package com.shdata.health.carezy.patientmanage.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 汇总搜索  VO
 *
 * @author dingwentao
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SummarySearchVo {

        /** 患者筛选类型 */
        private List<String> hzxxs;
        /** 病区 */
        private List<String> bqids;
        /** 护士id */
        private String hsid;
}

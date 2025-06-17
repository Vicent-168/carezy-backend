package com.shdata.health.carezy.whiteBoard.bean;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.service.FormsIndexService;
import com.shdata.health.carezy.patientmanage.mapper.RegistInformationMapper;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 约束
 */
@Component("ConstraintQuery")
public class ConstraintQuery extends BaseQuery{
    @Resource
    private RegistInformationMapper registInformationMapper;
    @Resource
    private FormsIndexService formsIndexService;

    @Override
    public Map<String, String> queryXmzs(String yljgid, Date rq, List<String> wardIds) {
        String wsid = formsIndexService.getWsidByylgjAndwsbm2(Constants.BHXYS_WSBM, yljgid);
        if (wsid == null){
            return new HashMap<>();
        }
        List<RegistInformationVo> list = registInformationMapper.ConstraintRiskSummary(yljgid, wardIds, wsid);
        // 使用Stream API进行分组和转换
        return list.stream()
                .collect(Collectors.groupingBy(
                        RegistInformationVo::getBqid, // 分组依据，即bqid
                        Collectors.mapping(
                                RegistInformationVo::getBch, // 映射函数，提取bch
                                Collectors.joining(",") // 收集器，将bch列表转换为逗号分隔的字符串
                        )
                ));
    }
}

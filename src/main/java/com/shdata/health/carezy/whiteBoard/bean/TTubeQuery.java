package com.shdata.health.carezy.whiteBoard.bean;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.mapper.FormsIndexMapper;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * T管
 */
@Component("tTubeQuery")
public class TTubeQuery extends BaseQuery {

    @Resource
    private FormsIndexMapper formsIndexMapper;

    @Override
    public Map<String, String> queryXmzs(String yljgid, Date rq, List<String> wardIds) {
        List<RegistInformationVo> list = formsIndexMapper.singleDuctStatistics(yljgid, wardIds, Constants.DGZG_FLDM,Constants.TG);
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

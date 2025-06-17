package com.shdata.health.carezy.whiteBoard.bean;

import com.shdata.health.carezy.patientmanage.mapper.RegistInformationMapper;
import com.shdata.health.carezy.patientmanage.vo.RegistInformationVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在院总数
 */
@Component("InAllQuery")
public class InAllQuery extends BaseQuery{
    @Resource
    private RegistInformationMapper registInformationMapper;
    @Override
    public Map<String, String> queryXmzs(String yljgid, Date rq, List<String> wardIds) {

        List<RegistInformationVo> list = registInformationMapper.queryInAllQuery(yljgid, wardIds);
        // 使用Stream API进行分组和转换
        return list.stream()
                .collect(Collectors.groupingBy(
                        RegistInformationVo::getBqid, // 分组依据，即bqid
                        Collectors.summingInt(reg -> 1) // 收集器，计算每个分组中的元素数量
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.valueOf(entry.getValue()) // 将数量转换为字符串
                ));

    }
}

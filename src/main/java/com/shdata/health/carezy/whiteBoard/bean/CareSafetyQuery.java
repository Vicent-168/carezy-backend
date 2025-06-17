package com.shdata.health.carezy.whiteBoard.bean;

import cn.hutool.core.collection.CollUtil;
import com.shdata.health.carezy.forms.mapper.FormsIndexMapper;
import com.shdata.health.carezy.whiteBoard.vo.CareSafetyVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("careSafetyQuery")
public class CareSafetyQuery extends BaseQuery {
    @Resource
    private FormsIndexMapper formsIndexMapper;
    @Override
    public Map<String, String> queryXmzs(String yljgid, Date rq, List<String> wardIds) {
        List<CareSafetyVo> list = formsIndexMapper.careSafetyQuery(yljgid, wardIds);
        Map<String, List<CareSafetyVo>> map = new HashMap<>();
        if(CollUtil.isNotEmpty(list)){
            map = list.stream().collect(Collectors.groupingBy(CareSafetyVo::getBqid));
        }
        return Map.of();
    }
}

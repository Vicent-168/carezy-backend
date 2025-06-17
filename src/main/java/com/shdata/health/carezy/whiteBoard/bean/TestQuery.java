package com.shdata.health.carezy.whiteBoard.bean;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("TestQuery")
public class TestQuery extends BaseQuery {
    @Override
    public Map<String, String> queryXmzs(String yljgid, Date rq, List<String> wardIds) {
        Map<String, String> map = new HashMap<>();
        wardIds.forEach((wardId) -> map.put(wardId, RandomUtil.randomString(5)));
        return map;
    }
}

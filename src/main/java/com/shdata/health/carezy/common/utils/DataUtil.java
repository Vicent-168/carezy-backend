package com.shdata.health.carezy.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.shdata.health.carezy.common.dict.DictService;
import com.shdata.health.common.dict.Dict;
import com.shdata.health.common.utils.RedisUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DataUtil extends com.shdata.health.his.utils.DataUtil {


    public static Map<String, String> getDiseaseName() {

        String key = StrUtil.format("dict:{}", "DIC_HLZYJBZD");
        Map<String, String> zjdlDicts = RedisUtils.get(key);
        if (CollUtil.isNotEmpty(zjdlDicts)) {
            return zjdlDicts;
        }
        List<Dict> list = SpringUtil.getBean(DictService.class).getDiseaseDict();

        Map<String, String> collect = list.stream().collect(Collectors.toMap(Dict::getCode, Dict::getName));
        Map<String, String> map = new HashMap<>(collect);
        RedisUtils.set(key, map);
        return map;
    }

    public static Map<String, String> getFormName() {

        String key = StrUtil.format("dict:{}", "DIC_HLZYWSZD");
        Map<String, String> zjdlDicts = RedisUtils.get(key);
        if (CollUtil.isNotEmpty(zjdlDicts)) {
            return zjdlDicts;
        }
        List<Dict> list = SpringUtil.getBean(DictService.class).getFormName();

        Map<String, String> collect = list.stream().collect(Collectors.toMap(Dict::getCode, Dict::getName));
        Map<String, String> map = new HashMap<>(collect);
        RedisUtils.set(key, map, 1, TimeUnit.HOURS);
        return map;
    }
}

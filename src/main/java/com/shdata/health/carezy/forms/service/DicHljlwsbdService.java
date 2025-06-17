package com.shdata.health.carezy.forms.service;

import cn.hutool.core.collection.CollUtil;
import com.shdata.health.carezy.forms.entity.DicHljlwsbd;
import com.shdata.health.carezy.forms.mapper.DicHljlwsbdMapper;
import com.shdata.health.carezy.forms.vo.DicHljlwsbdVo;
import com.shdata.health.common.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 护理记录单文书表单字典  Service服务
 *
 * @author myy
 * @date 2024-10-21
 */
@Service
@Transactional(readOnly = true)
public class DicHljlwsbdService extends BaseService<DicHljlwsbdMapper,DicHljlwsbd> {

    public List<DicHljlwsbdVo> findByWsid(String wsid, String xmdm) {
        return baseMapper.findByWsid(wsid, xmdm);
    }


    /**
     * 根据分类获取报表的项目代码列表
     */
    public Map<String,List<DicHljlwsbdVo>> findByFl(String wsid, String xmdm) {
        Map<String, List<DicHljlwsbdVo>> map = new HashMap<>();
        List<DicHljlwsbdVo> list = baseMapper.findByWsid(wsid, xmdm);
        if (CollUtil.isNotEmpty(list)){
            map = list.stream().collect(Collectors.groupingBy(DicHljlwsbdVo::getXmlx));
        }
        return map;
    }


}

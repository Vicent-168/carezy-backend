package com.shdata.health.carezy.forms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.shdata.health.carezy.forms.entity.DicHljlwsxm;
import com.shdata.health.carezy.forms.mapper.DicHljlwsxmMapper;
import com.shdata.health.carezy.forms.vo.DicHljlwsxmVo;
import com.shdata.health.common.base.BaseService;
import com.shdata.health.common.dict.Dict;
import com.shdata.health.common.dict.DictService;
import com.shdata.health.common.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 护理记录单文书项目字典  Service服务
 *
 * @author myy
 * @date 2024-10-21
 */
@Service
@Transactional(readOnly = true)
public class DicHljlwsxmService extends BaseService<DicHljlwsxmMapper,DicHljlwsxm> {

    /**
     * 获取项目和分值
     */
    public List<DicHljlwsxmVo> getProject(String wsid, String zdlx) {
        return baseMapper.getProject(wsid, zdlx);
    }


    /**
     * 获取项目和分值(分类)
     */
    public Map<String,List<DicHljlwsxmVo>> getProjectByFl(String wsid, String zdlx) {
        Map<String,List<DicHljlwsxmVo>> map = new HashMap<>();
        List<DicHljlwsxmVo> projectDicts = baseMapper.getProject(wsid, zdlx);
        if (CollUtil.isNotEmpty(projectDicts)){
            map = projectDicts.stream().collect(Collectors.groupingBy(DicHljlwsxmVo::getZdlx));
        }
        return map;
    }


    public Map<String, List<Dict>> getName(String wsid, String types) {
        Map<String, List<Dict>> dictMap = new HashMap<>();
        if(StrUtil.isNotBlank(types) && StrUtil.isNotBlank(wsid)){
            String[] dicTypes = types.split(",");
            for (String type : dicTypes) {
                if (StrUtil.isNotBlank(type)){
                    type = type.trim();
                    dictMap.put(type,getDicts(wsid,type));
                }
            }
        }
        return dictMap;
    }
    public  List<Dict> getDicts(String wsid,String type) {
        if (StrUtil.isBlank(type)) {
            return null;
        } else {
            String key = StrUtil.format("xmlx:{}", new Object[]{type});
            List<Dict> dicts = (List) RedisUtils.get(key);
            if (CollUtil.isNotEmpty(dicts)) {
                return dicts;
            } else {
                List<Dict> dictList = baseMapper.findByType(wsid,type);
                if (CollUtil.isNotEmpty(dictList)) {
                    RedisUtils.set(key, dictList);
                }
                return dictList;
            }
        }
    }

}

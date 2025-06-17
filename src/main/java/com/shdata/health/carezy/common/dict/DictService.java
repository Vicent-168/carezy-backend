package com.shdata.health.carezy.common.dict;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.shdata.health.common.dict.Dict;
import com.shdata.health.common.exception.ParameterException;
import com.shdata.health.common.utils.DictUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DictService implements com.shdata.health.common.dict.DictService {
    private final DictMapper dictMapper;

    public DictService(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public List<Dict> findByType(String type) {
        return dictMapper.findDictByType(type);
    }


    public Map<String, List<Dict>> findByTypes(String types) {
        Map<String, List<Dict>> dictMap = new HashMap<>();
        if (StrUtil.isNotBlank(types)) {
            String[] dicTypes = types.split(",");
            for (String type : dicTypes) {
                if (StrUtil.isNotBlank(type)) {
                    type = type.trim();
                    dictMap.put(type, DictUtil.getDicts(type));
                }
            }
        }
        return dictMap;
    }

    public List<Dict> getDiseaseDict() {
        return dictMapper.getDiseaseDict();
    }

    public List<Dict> findByTypeAndCj(String type, String cj) {
        return dictMapper.findByTypeAndCj(type, cj);
    }

    public List<Dict> findByTypeAndSjdm(String type, String sjdm) {
        return dictMapper.findByTypeAndSjdm(type, sjdm);
    }

    public List<Dict> getFormName() {
        return dictMapper.getFormDict();
    }

    /**
     * 测试接口
     * @param types
     * @return
     */
    public Map<String, List<DictVo>> findByTypesByTree(String types) {
        Map<String, List<DictVo>> dictMap = new HashMap<>();
        if (StrUtil.isNotBlank(types)) {
            String[] dicTypes = types.split(",");
            for (String type : dicTypes) {
                if (StrUtil.isNotBlank(type)) {
                    type = type.trim();
                    List<DictVo> dictVos = dictMapper.findDict(type); // 当前类型的全部数据
                    if(CollUtil.isEmpty(dictVos)){
                        throw new ParameterException("字典类型不存在");
                    }
                    if(dictVos.get(0).getCj() != null){
                        List<DictVo> tree = new ArrayList<>();
                        Map<String, DictVo> dictVoMap = new HashMap<>();
                        for (DictVo dict : dictVos) {
                            dictVoMap.put(dict.getCode(), dict);
                        }
                        for (DictVo dict : dictVos) {
                            if (StrUtil.isBlank(dict.getSjdm())) {// 如果没有上级代码，即第一层
                                tree.add(dict);
                            } else {
                                DictVo parent = dictVoMap.get(dict.getSjdm());
                                if (parent != null) {
                                    if (parent.getChildren() == null) {
                                        parent.setChildren(new ArrayList<>());
                                    }
                                    parent.getChildren().add(dict);
                                }
                            }
                        }
                        dictMap.put(type, tree);
                    }else{
                        //从Dict映射成DictVo
                        dictMap.put(type, DictUtil.getDicts(type).stream().map(item -> {
                            DictVo dictVo = new DictVo();
                            BeanUtil.copyProperties(item, dictVo);
                            return dictVo;
                        }).toList());
                    }
                }
            }
        }
        return dictMap;
    }
}

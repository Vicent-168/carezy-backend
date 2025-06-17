package com.shdata.health.carezy.forms.service.assess;

import cn.hutool.core.collection.CollUtil;
import com.shdata.health.carezy.forms.vo.FormsDetailVo;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;

import java.util.List;
import java.util.Map;


public interface ScalesAssess {

    /**
     * 量表计算
     */
    default String compute(List<FormsDetailVo> list){
        if(CollUtil.isNotEmpty(list)){
            Long score = list.stream().mapToLong(FormsDetailVo::getFz).sum();
            return String.valueOf(score);
        }else {
            return "";
        }
    }
    default String compute(List<FormsDetailVo> list, FormsIndexVo indexByFront){
        if(CollUtil.isNotEmpty(list)){
            Long score = list.stream().mapToLong(FormsDetailVo::getFz).sum();
            return String.valueOf(score);
        }else {
            return "";
        }
    }

    default String compute(String userId,String wsid,String xmdm, Map<String, Map<String, String>> map){
        if (!map.containsKey(userId)) {
            return ""; // 若没有userId表示当前用户未开启过评估，返回null
        }
        Map<String, String> scoreMap = map.get(userId);
        int totalScore =0;
        for (Map.Entry<String, String> entry : scoreMap.entrySet())
            if (entry.getKey().startsWith(wsid)) {
                totalScore += Integer.parseInt(entry.getValue());//当前都是分值，暂不异常判断
            }
        return String.valueOf(totalScore);
    }


    /**
     * 评估
     */
    String assess(String score);
    String assess(String score, FormsIndexVo indexByFront);
}

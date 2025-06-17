package com.shdata.health.carezy.forms.service.assess;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import org.springframework.stereotype.Service;

/**
 * 老年人生活活动能力评分
 */

//ToDo 未完成  规则，不需要监控
@Service("elderlyLifeAbility")
public class ElderlyLifeAbility implements ScalesAssess{
    @Override
    public String assess(String score) {
        return null;
    }

    @Override
    public String assess(String score, FormsIndexVo indexByFront) {
        try {
            int scoreValue = Integer.parseInt(score);
            if (scoreValue>=0 && scoreValue<30) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.gfxdj);  //风险等级
                return "及时转出";
            } else if (scoreValue >= 31) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                return "无需转出";
            } else {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                return "无需转出";
            }
        }catch (Exception e) {
            return "格式有误，评估失败";
        }
    }
}

package com.shdata.health.carezy.forms.service.assess;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import org.springframework.stereotype.Service;

/**
 * 日常生活活动能力评估
 */
@Service("dailyLifeActivitiesAbility")
public class DailyLifeActivitiesAbility implements ScalesAssess{

    @Override
    public String assess(String score) {
        return "日常生活活动能力评估";
    }

    @Override
    public String assess(String score, FormsIndexVo indexByFront) {
        try {
            int scoreValue = Integer.parseInt(score);
            if (scoreValue>=0 && scoreValue<=40) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.gfxdj);  //风险等级
                return "重度依赖";
            } else if (scoreValue>=41 && scoreValue<=60) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.zfxdj);  //风险等级
                return "中度依赖";
            } else if (scoreValue>=61 && scoreValue<=99) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.dfxdj);  //风险等级
                return "轻度依赖";
            } else if (scoreValue == 100) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                return "无需依赖";
            } else {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                return "无需依赖";
            }
        }catch (Exception e) {
            return "格式有误，评估失败";
        }
    }
}

package com.shdata.health.carezy.forms.service.assess;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import org.springframework.stereotype.Service;

import static com.shdata.health.carezy.common.constants.Constants.yes;

/**
 * 压疮风险因素评估
 */
@Service("PressureUlcersRiskFactor")
public class PressureUlcersRiskFactor implements ScalesAssess {

    @Override
    public String assess(String score) {
        return null;
    }

    @Override
    public String assess(String score, FormsIndexVo indexByFront) {
        try {
            int scoreValue = Integer.parseInt(score);
            if (scoreValue<=9) {
                indexByFront.setFxdj(Constants.jgfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wjk);  //设置监控状态
                return "极高危险";
            } else if (scoreValue>=10 && scoreValue<=12) {
                indexByFront.setFxdj(Constants.gfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wjk);  //设置监控状态
                return "高危险";
            } else if (scoreValue>=13 && scoreValue<=14) {
                indexByFront.setFxdj(Constants.zfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "中危险";
            } else if (scoreValue>=15 && scoreValue<=17) {
                indexByFront.setFxdj(Constants.dfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "低危险";
            } else {
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "无危险";
            }
        }catch (Exception e) {
            return "格式有误，评估失败";
        }
    }
}

package com.shdata.health.carezy.forms.service.assess;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import org.springframework.stereotype.Service;

@Service("oralHealth")
public class OralHealth implements ScalesAssess{
    @Override
    public String assess(String score) {
        return null;
    }

    @Override
    public String assess(String score, FormsIndexVo indexByFront) {
        try {
            int scoreValue = Integer.parseInt(score);
            if (scoreValue>=1) {
                indexByFront.setFxdj(Constants.gfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "高危险";
            } else if (scoreValue==0) {
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "无危险";
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

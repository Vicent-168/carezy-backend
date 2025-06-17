package com.shdata.health.carezy.forms.service.assess;

import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.vo.FormsIndexVo;
import org.springframework.stereotype.Service;

/**
 * 简单营养评估表
 */
@Service(value = "simpleNutritional")
public class SimpleNutritional implements ScalesAssess{
    @Override
    public String assess(String score) {
        return null;
    }

    @Override
    public String assess(String score, FormsIndexVo indexByFront) {
        try {
            int scoreValue = Integer.parseInt(score);
            if (scoreValue>=0 && scoreValue<=7) {
                indexByFront.setFxdj(Constants.gfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "营养不良";
            } else if (scoreValue>=8 && scoreValue<=11) {
                indexByFront.setFxdj(Constants.dfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "有营养不良的风险";
            } else if (scoreValue>=12 && scoreValue<=14) {
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "正常营养状况";
            } else {
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                return "正常营养状况";
            }
        }catch (Exception e) {
            return "格式有误，评估失败";
        }
    }
}

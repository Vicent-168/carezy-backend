package com.shdata.health.carezy.forms.service.assess;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.carezy.forms.mapper.DicHljlwsbdMapper;
import com.shdata.health.carezy.forms.mapper.DicHljlwsxmMapper;
import com.shdata.health.carezy.forms.vo.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.shdata.health.carezy.common.constants.Constants.yes;

/**
 * 导管风险因素评估
 */
@Service("catheterRiskFactor")
public class CatheterRiskFactor implements ScalesAssess{

    private final DicHljlwsxmMapper dictHljlwsxmMapper;
    private final DicHljlwsbdMapper dicHljlwsbdMapper;

    public CatheterRiskFactor(DicHljlwsxmMapper dictHljlwsxmMapper, DicHljlwsbdMapper dicHljlwsbdMapper) {
        this.dictHljlwsxmMapper = dictHljlwsxmMapper;
        this.dicHljlwsbdMapper = dicHljlwsbdMapper;
    }

    /**
     * 量表计算
     */
    @Override
    public String compute(List<FormsDetailVo> list,FormsIndexVo indexByFront){
        if(CollUtil.isNotEmpty(list)){
            List<DicHljlwsbdVo> dicHljlwsbdList = dicHljlwsbdMapper.findByWsid(indexByFront.getWsid(),"");//配置表[key]
            List<DicHljlwsxmVo> projectList = dictHljlwsxmMapper.getProject(indexByFront.getWsid(),""); //项目明细
            Long score = 0L;
            for(FormsDetailVo item:list){
                String xmz = String.valueOf(item.getXmz());
                String[] code = new String[0];
                if(StrUtil.isNotEmpty(xmz)) {
                    if (item.getXmz().toString().startsWith("[") && item.getXmz().toString().endsWith("]")) {
                        if (item.getXmz().toString().startsWith("[{") && item.getXmz().toString().endsWith("}]")) {
                            String jsonData = item.getXmz().toString().replace("=", ":")
                                    .replaceAll("([a-zA-Z]+):", "\"$1\":")
                                    .replaceAll(":([,\\}])", ":\"\"$1")  // 空值处理，确保空的值替换为 ""
                                    .replace("，", ",");  // 将中文逗号替换为英文逗号
                            JSONArray jsonArray = new JSONArray(jsonData);
                            item.setXmz(jsonArray);
                        } else {
                            code = item.getXmz().toString().substring(1, item.getXmz().toString().length() - 1).split(", ");
                        }
                    }else{
                        code = xmz.split(", ");
                    }
                    //开始计算
                    if(CollUtil.isNotEmpty(Arrays.asList(code))){
                        for (String s : code) {
                            String xmzd;
                            DicHljlwsbdVo xmdmVo = dicHljlwsbdList.stream().filter(xmdm ->{
                                return xmdm.getXmdm().equals(item.getXmdm());
                            }).findFirst().orElseGet(() -> null);
                            if (xmdmVo != null){
                                xmzd = xmdmVo.getXmzd();
                            } else {
                                xmzd = "";
                            }
                            DicHljlwsxmVo xmmxVo = projectList.stream().filter(xmmx ->{
                                return xmmx.getZdlx().equals(xmzd) && xmmx.getZddm().equals(s);
                            }).findFirst().orElseGet(() -> null);
                            if(xmmxVo != null){
                                score += Long.valueOf(xmmxVo.getFz());
                            }
                        }
                    }
                }
            }
            return String.valueOf(score);
        }else {
            return "";
        }
    }

    @Override
    public String assess(String score) {
        return "导管风险因素评估";
    }

    @Override
    public String assess(String score, FormsIndexVo indexByFront) {
        try {
            int scoreValue = Integer.parseInt(score);
            if (scoreValue>=3) {
                indexByFront.setJkzt(Constants.wjk);  //设置监控状态
                indexByFront.setFxdj(Constants.gfxdj);  //风险等级
                return "高危险";
            } else if (scoreValue == 2) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.gfxdj);  //风险等级
                return "高危险";
            } else if (scoreValue == 1) {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.dfxdj);  //风险等级
                return "低危险";
            } else {
                indexByFront.setJkzt(Constants.wxjk);  //设置监控状态
                indexByFront.setFxdj(Constants.wfxdj);  //风险等级
                return "无危险";
            }
        }catch (Exception e) {
            return "格式有误，评估失败";
        }
    }

}

package com.shdata.health.carezy.whiteBoard.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.shdata.health.carezy.whiteBoard.vo.BqyyMkXmVo;
import com.shdata.health.common.exception.ParameterException;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public abstract class BaseQuery {

    /**
     * T_HL_DP_BQYY_XMB表对应的cs值
     */
    private BqyyMkXmVo xmb;

    public static BaseQuery create(BqyyMkXmVo xmb) {
        if (xmb == null) {
            throw new ParameterException("参数不能为空");
        }
        if (StrUtil.isBlank(xmb.getBean())) {
            throw new ParameterException(StrUtil.format("T_HL_DP_BQYY_XMB表ID:{}未配置Bean", xmb.getId()));
        }
        try {
            BaseQuery service = SpringUtil.getBean(xmb.getBean());
            service.xmb = xmb;
            return service;
        } catch (Exception e) {
            throw new ParameterException(StrUtil.format("T_HL_DP_BQYY_XMB表ID:{},配置的bean:{}未注册Spring Bean", xmb.getId(), xmb.getBean()));
        }
    }

    /**
     * 查询所有病区对应项目值
     */
    public abstract Map<String, String> queryXmzs(String yljgid, Date rq, List<String> wardIds);


}

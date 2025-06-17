package com.shdata.health.carezy.common.utils;

import java.math.BigDecimal;

public class NumberUtil extends cn.hutool.core.util.NumberUtil {


    /**
     * 去掉bigdecimal后面多余的0
     * @param b
     * @return
     */
    public static BigDecimal fixLastZero(BigDecimal b){
        return b!=null?b.stripTrailingZeros():null;
    }

}

package com.shdata.health.carezy.common.utils;

public class IdcardUtil extends cn.hutool.core.util.IdcardUtil {

    /**
     * 根据身份证号获取性别，支持15或18位身份证
     * @param idcard
     * @return 1：男；2：女
     */
    public static String getSexByIdcard(String idcard){
        int gender=getGenderByIdCard(idcard);
        return gender==1?"1":"2";
    }

}

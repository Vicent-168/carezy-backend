package com.shdata.health.carezy.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.shdata.health.carezy.common.constants.Constants;
import com.shdata.health.common.config.SystemConfig;
import com.shdata.health.common.exception.ParameterException;
import com.shdata.health.common.utils.AuthUtil;
import com.shdata.health.common.utils.SessionUtil;
import com.shdata.health.his.entity.Dept;
import com.shdata.health.his.entity.Role;
import com.shdata.health.his.entity.UserInfo;
import com.shdata.health.his.feign.HisyncFeignService;
import com.shdata.health.his.utils.DataUtil;

import java.util.List;

public class UserUtils extends AuthUtil {

    /**
     * 获取登陆用户ID
     */
    public static String getUserId() {
        return AuthUtil.getUserId();
    }

    /**
     * 获取登录用户姓名
     *
     * @return
     */
    public static String getUserName() {
        UserInfo user = getUserInfo();
        return user.getName();
    }

    /**
     * 根据用户id获取用户名 找不到返回null
     *
     * @param userId
     * @return
     */
    public static String getUserName(String userId) {
        UserInfo info = SpringUtil.getBean(HisyncFeignService.class).getUserById(userId);
        return info != null ? info.getName() : null;
    }


    /**
     * 获取用户科室id
     *
     * @return
     */
    public static String getDeptId() {
        UserInfo info = StrUtil.isNotBlank(getUserId()) ? SpringUtil.getBean(HisyncFeignService.class).getUserById(getUserId()) : null;
        return info != null ? info.getDeptId() : null;
    }

    /**
     * 获取登录用户信息
     */
    public static UserInfo getUserInfo() {
        String key = "session_user";
        UserInfo info = SessionUtil.get(key);
        if (info != null) {
            return info;
        }
        info = SpringUtil.getBean(HisyncFeignService.class).getUserById(getUserId());
        if (info != null) {
            SessionUtil.set(key, info);
        } else {
            throw new ParameterException("未登录");
        }
        return info;
    }

    /**
     * 获取登录用户机构ID
     */
    public static String getOrganId() {
        UserInfo userInfo = getUserInfo();
        return userInfo.getOrganId();
    }


    /**
     * 获取登录用户用户的机构权限
     */
    public static List<String> getUserOrganIds() {
        String key = "session_organ_ids";
        List<String> ids = SessionUtil.get(key);
        if (CollUtil.isNotEmpty(ids)) {
            return ids;
        }
        List<String> idList = SpringUtil.getBean(HisyncFeignService.class).getOrgansByOrganId(getUserId());
        if (CollUtil.isNotEmpty(idList)) {
            SessionUtil.set(key, idList);
        }
        return idList;
    }

    /**
     * 获取用户护士类型：
     * 1.普通护士
     * 2.护士长
     * 3.非护士
     */
    public static String getNurseType() {
        List<Role> roleList = DataUtil.findRolesByUserId(SystemConfig.systemId(), getUserId());
        if (CollUtil.isNotEmpty(roleList)) {
            String code = "3";
            for (Role role : roleList) {
                if (role.getCode().equals(Constants.ROLE_NURSE)) {
                    code = "1";
                } else if (role.getCode().equals(Constants.ROLE_HEAD_NURSE)) {
                    return "2";
                }
            }
            return code;
        }
        return "3";
    }

    /**
     * 获取登录用户权限病区
     */
    public static List<Dept> getLoginWards() {
        return DataUtil.findWardListByUserId(getUserId());
    }

    /**
     * 获取登录用户权限科室
     */
    public static List<Dept> getLoginDepts() {
        return DataUtil.findDeptListByUserId(getUserId());
    }

}

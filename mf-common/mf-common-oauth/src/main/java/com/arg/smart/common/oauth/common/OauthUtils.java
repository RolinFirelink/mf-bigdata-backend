package com.arg.smart.common.oauth.common;

import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.utils.SpringBeanFactory;
import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.common.oauth.annotation.RequiresRoles;
import com.arg.smart.common.oauth.cache.UserPermissionCache;
import com.arg.smart.common.oauth.cache.UserRoleCache;
import com.arg.smart.common.oauth.api.entity.UserRole;
import com.arg.smart.common.oauth.entity.RedisAccessToken;
import com.arg.smart.common.oauth.entity.WeChatToken;
import com.arg.smart.common.oauth.service.TokenService;
import com.arg.smart.common.oauth.service.impl.WeChatTokenServiceImpl;
import com.arg.smart.common.oauth.service.impl.WebTokenServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: OAUTH工具方法
 * @date: 2022/12/6 17:23
 */
public class OauthUtils {


    /**
     * 生成6位数验证码
     *
     * @return
     */
    public static String buildCode() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        return String.valueOf((int) (random.nextDouble() * 900000 + 100000));
    }

    /**
     * 校验角色
     *
     * @param requiresRoles 角色限制
     * @return
     */
    public static boolean checkRoles(RequiresRoles requiresRoles) {
        UserRoleCache userRoleCache = SpringBeanFactory.getBean("userRoleCache");
        List<UserRole> list = userRoleCache.getFromCacheAndDB(AuthInfoUtils.getCurrentUserId(), AuthInfoUtils.getCurrentClientId());
        Set<String> set = list.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        //如果用户为超户，直接返回
        if (null != set && set.contains(SerConstant.SUPER_ROLE)) {
            return true;
        }
        return checkValue(requiresRoles.logical(), requiresRoles.value(), set);
    }

    /**
     * 校验权限
     *
     * @param requiresPermissions 权限限制
     * @return
     */
    public static boolean checkPermission(RequiresPermissions requiresPermissions) {
        UserPermissionCache userPermissionCache = SpringBeanFactory.getBean("userPermissionCache");
        Set<String> set = userPermissionCache.getFromCacheAndDB(AuthInfoUtils.getCurrentUserId(), AuthInfoUtils.getCurrentClientId());
        //如果用户拥有所有权限直接返回true
        if (null != set && set.contains(SerConstant.ALL_PERMISSION)) {
            return true;
        }
        return checkValue(requiresPermissions.logical(), requiresPermissions.value(), set);
    }

    /**
     * 校验结果
     *
     * @param logical
     * @param values
     * @param set
     * @return
     */
    private static boolean checkValue(Logical logical, String[] values, Set<String> set) {
        //未设置权限值，校验通过
        if (values == null) {
            return true;
        }
        //未获取到用户权限，校验不通过
        if (set == null) {
            return false;
        }
        switch (logical) {
            case AND:
                for (String value : values) {
                    if (!set.contains(value)) {
                        return false;
                    }
                }
                return true;
            case OR:
                for (String value : values) {
                    if (set.contains(value)) {
                        return true;
                    }
                }
            default:
                return false;
        }
    }

    /**
     * 根据token获取tokenService
     *
     * @param token
     * @return
     */
    public static TokenService getTokenService(String token) {
        if (token.startsWith(SerConstant.WX_PREFIX)) {
            return SpringBeanFactory.getBean(WeChatTokenServiceImpl.class);
        }
        return SpringBeanFactory.getBean(WebTokenServiceImpl.class);

    }

    /**
     * 获取token获取token对象
     *
     * @param token
     * @return
     */
    public static Object getToken(String token) {
        return getToken(getTokenService(token), token);
    }

    public static Object getToken(TokenService tokenService, String token) {
        return tokenService.getToken(token);
    }

    /**
     * 删除token
     *
     * @param token
     */
    public static void delToken(String token) {
        delToken(getTokenService(token), token);
    }

    public static void delToken(TokenService tokenService, String token) {
        tokenService.delToken(token);
    }

    /**
     * 删除refreshToken
     *
     * @param refreshToken
     */
    public static void delRefreshToken(String refreshToken) {
        delRefreshToken(getTokenService(refreshToken), refreshToken);
    }

    public static void delRefreshToken(TokenService tokenService, String refreshToken) {
        tokenService.delRefreshToken(refreshToken);
    }

    /**
     * 登出指定token
     *
     * @param token token
     * @return 返回 token关联的sessionId
     */
    public static String logout(String token) {
        TokenService tokenService = getTokenService(token);
        Object accessToken = getToken(tokenService, token);
        if (accessToken == null) {
            return null;
        }
        delToken(tokenService, token);
        String sessionId = null;
        if (accessToken instanceof RedisAccessToken) {
            RedisAccessToken redisAccessToken = (RedisAccessToken) accessToken;
            sessionId = redisAccessToken.getTokenSessionId();
            delRefreshToken(tokenService, redisAccessToken.getRefreshToken());
        } else if (accessToken instanceof WeChatToken) {
            WeChatToken weChatToken = (WeChatToken) accessToken;
            sessionId = weChatToken.getSession_key();
            delRefreshToken(tokenService, weChatToken.getRefresh_token());
        }
        return sessionId;
    }
}

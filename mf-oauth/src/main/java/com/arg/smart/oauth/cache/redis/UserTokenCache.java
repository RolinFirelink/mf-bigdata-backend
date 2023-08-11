package com.arg.smart.oauth.cache.redis;

import com.arg.smart.common.core.enums.DeviceType;
import com.arg.smart.common.oauth.common.OauthUtils;
import com.arg.smart.common.redis.common.RedisPrefix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cgli
 * 用户token缓存信息
 * @date: 2020/2/22 13:13
 */
@Component
@Slf4j
public class UserTokenCache {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    long expire = 30;


    @Resource
    RedisSessionDAO redisSessionDAO;
    /**
     * 删除用户设备id 同时删除设备下token信息
     *
     * @param deviceType 设备类型
     * @param deviceId   设备id
     * @param userId     用户id
     */
    public void delUserTokenCache(DeviceType deviceType, String deviceId, String userId) {
        //如果不互斥，忽略用户id，直接通过设备id进行token关系缓存
//        if (!loginMutex) {
//            userId = "";
//        }
        delDeviceTokenCache(deviceId);
        //如果当前用户为空不删除用户设备对应关系，因为并未存储
        if (StringUtils.isEmpty(userId)) {
            return;
        }
        redisTemplate.delete(RedisPrefix.buildUser2DeviceKey(userId, deviceType));
    }

    public void delDeviceTokenCache(String deviceId) {
        delTokenList(deviceId);
        try {
            redisSessionDAO.delete(redisSessionDAO.readSession(deviceId));
        } catch (Exception ex) {
            log.error("删除session异常", ex);
        }
    }

    /**
     * web页面和APP端同一个用户 只允许在一个设备登录
     * web页面和APP端登录状态允许同时存在
     *
     * @param deviceType 设备类型
     * @param deviceId   设备ID web端为sessionid app端为认证app的token
     * @param userId     用户id
     * @param token      token值
     */
    public void addUserTokenCache(DeviceType deviceType, String deviceId, String userId, String token) {
        String oldDeviceId = getUserDevice(deviceType, userId);
        if (StringUtils.isEmpty(oldDeviceId)) {
            setUserDevice(deviceType, userId, deviceId, token);
            return;
        }
        if (deviceId.equals(oldDeviceId)) {
            setToken(deviceId, token);
            return;
        }
        delUserDevice(deviceType, userId, oldDeviceId);
        setUserDevice(deviceType, userId, deviceId, token);
    }

    /**
     * 设置用户设备以及相关token信息
     *
     * @param deviceType
     * @param userId
     * @param deviceId
     * @param token
     */
    private void setUserDevice(DeviceType deviceType, String userId, String deviceId, String token) {
        setUserDevice(deviceType, userId, deviceId);
        setToken(deviceId, token);
    }

    /**
     * 缓存保存30天 时间可以适当缩短
     * 当用户关闭浏览器重新登录会重新设置该信息
     *
     * @param deviceType
     * @param userId
     * @param deviceId
     */
    private void setUserDevice(DeviceType deviceType, String userId, String deviceId) {
        redisTemplate.opsForValue().set(RedisPrefix.buildUser2DeviceKey(userId, deviceType), deviceId, expire, TimeUnit.DAYS);
    }

    /**
     * 获取用户设备id
     *
     * @param deviceType
     * @param userId
     * @return
     */
    public String getUserDevice(DeviceType deviceType, String userId) {
        return (String) redisTemplate.opsForValue().get(RedisPrefix.buildUser2DeviceKey(userId, deviceType));
    }

    /**
     * 删除用户设备id 同时删除设备下token信息
     *
     * @param deviceType
     * @param userId
     */
    public void delUserDevice(DeviceType deviceType, String userId) {
        log.info("deviceType"+deviceType+",userId:"+userId);
        String deviceId = getUserDevice(deviceType, userId);
        if (StringUtils.isEmpty(deviceId)) {
            return;
        }
        delUserDevice(deviceType, userId, deviceId);
    }

    private void delUserDevice(DeviceType deviceType, String userId, String deviceId) {
        delTokenList(deviceId);
        redisTemplate.delete(RedisPrefix.buildUser2DeviceKey(userId, deviceType));
    }

    /**
     * 一个设备可能对应多个token  存储token列表信息
     *
     * @param deviceId
     * @param token
     */
    private void setToken(String deviceId, String token) {
        redisTemplate.opsForList().leftPush(RedisPrefix.buildDevice2TokenKey(deviceId), token);
        redisTemplate.expire(RedisPrefix.buildDevice2TokenKey(deviceId), expire, TimeUnit.DAYS);
    }

    /**
     * 删除token列表
     *
     * @param deviceId
     */
    private void delTokenList(String deviceId) {
        List<Object> list = redisTemplate.opsForList().range(RedisPrefix.buildDevice2TokenKey(deviceId), 0, -1);
        for (Object obj : list) {
            OauthUtils.logout(obj.toString());
        }
        redisTemplate.delete(RedisPrefix.buildDevice2TokenKey(deviceId));
    }
}

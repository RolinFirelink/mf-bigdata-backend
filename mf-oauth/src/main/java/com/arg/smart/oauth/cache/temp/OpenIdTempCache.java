package com.arg.smart.oauth.cache.temp;

import com.arg.smart.common.redis.temp.BaseTempCache;
import com.arg.smart.common.redis.common.RedisPrefix;
import com.arg.smart.oauth.mapper.SsoUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cgli
 * @date: 2020/2/29 15:07
 */
@Component
public class OpenIdTempCache extends BaseTempCache<String> {
    @Resource
    SsoUserMapper ssoUserMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildOpenId2userIdKey(key[0]);
    }

    @Override
    protected String getFromDB(String... key) {
        return ssoUserMapper.getUserIdByOpenId(key[0]);
    }
}

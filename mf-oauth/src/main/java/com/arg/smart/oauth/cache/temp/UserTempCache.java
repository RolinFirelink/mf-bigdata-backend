package com.arg.smart.oauth.cache.temp;

import com.arg.smart.common.redis.temp.BaseTempCache;
import com.arg.smart.oauth.mapper.SsoUserMapper;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.common.redis.common.RedisPrefix;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cgli
 * @date: 2020/2/14 17:46
 */
@Component
public class UserTempCache extends BaseTempCache<SsoUser> {
    @Resource
    SsoUserMapper ssoUserMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildUserDetailKey(key[0]);
    }

    @Override
    protected SsoUser getFromDB(String... key) {
        return ssoUserMapper.getUserById(key[0]);
    }

}

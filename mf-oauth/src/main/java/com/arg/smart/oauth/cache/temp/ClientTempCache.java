package com.arg.smart.oauth.cache.temp;

import com.arg.smart.common.redis.temp.BaseTempCache;
import com.arg.smart.common.redis.common.RedisPrefix;
import com.arg.smart.oauth.mapper.ClientMapper;
import com.arg.smart.oauth.entity.OAuthClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cgli
 * @date: 2020/2/16 15:54
 */
@Component
public class ClientTempCache extends BaseTempCache<OAuthClient> {
    @Resource
    ClientMapper clientMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildClientKey(key[0]);
    }

    @Override
    protected OAuthClient getFromDB(String... key) {
        return clientMapper.getClientById(key[0]);
    }
}

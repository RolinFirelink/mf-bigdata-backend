package com.arg.smart.oauth.cache.temp;

import com.arg.smart.common.redis.temp.BaseTempCache;
import com.arg.smart.common.redis.common.RedisPrefix;
import com.arg.smart.oauth.mapper.SsoUserMapper;
import com.arg.smart.oauth.entity.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author cgli
 * @date: 2020/2/14 18:56
 */
@Component
@Slf4j
public class Account2IdTempCache extends BaseTempCache<String> {
    @Resource
    SsoUserMapper ssoUserMapper;

    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildAccount2IdKey(key[0]);
    }

    @Override
    protected String getFromDB(String... key) {
        SsoUser user = ssoUserMapper.getUserByAccount(key[0]);
        if (user == null) {
            log.info(MessageFormat.format("错误:账号{0}未找到对应用户!", key));
            return null;
        }
        return user.getId();
    }

}

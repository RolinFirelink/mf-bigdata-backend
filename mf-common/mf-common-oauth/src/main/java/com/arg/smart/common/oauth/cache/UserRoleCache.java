package com.arg.smart.common.oauth.cache;

import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.api.remote.RemoteUserService;
import com.arg.smart.common.redis.common.RedisPrefix;
import com.arg.smart.common.redis.temp.BaseTempCache;
import com.arg.smart.common.oauth.api.entity.UserRole;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cgli
 * @description: 用户角色缓存
 * @date: 2022/12/6 22:38
 */
@Component("userRoleCache")
public class UserRoleCache extends BaseTempCache<List<UserRole>> {

    @Resource
    RemoteUserService remoteUserService;

    /**
     * key [0] userId [1] clientId
     *
     * @param key
     * @return
     */
    @Override
    protected String buildKey(String... key) {
        return RedisPrefix.buildUser2RolesKey(key[0], key[1]);
    }

    /**
     * 只查询缓存不查库
     *
     * @param key
     * @return
     */
    @Override
    protected List<UserRole> getFromDB(String... key) {
        Result<List<UserRole>> result = remoteUserService.getRoles(RPCConstants.INNER, key[0], key[1]);
        if (result == null || !result.isSuccess()) {
            return null;
        }
        return result.getData();
    }
}
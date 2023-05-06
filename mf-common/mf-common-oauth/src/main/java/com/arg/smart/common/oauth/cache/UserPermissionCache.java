package com.arg.smart.common.oauth.cache;

import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.api.remote.RemoteUserService;
import com.arg.smart.common.redis.common.RedisPrefix;
import com.arg.smart.common.redis.temp.BaseTempCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author cgli
 * @description: 用户权限缓存
 * @date: 2022/12/6 22:44
 */
@Component("userPermissionCache")
public class UserPermissionCache extends BaseTempCache<Set<String>> {

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
        return RedisPrefix.buildUser2PermissionsKey(key[0], key[1]);
    }

    /**
     * 不查询库直接返回null
     *
     * @param key
     * @return
     */
    @Override
    protected Set<String> getFromDB(String... key) {
        Result<Set<String>> result = remoteUserService.getPermissions(RPCConstants.INNER, key[0], key[1]);
        if (result == null || !result.isSuccess()) {
            return null;
        }
        return result.getData();
    }
}
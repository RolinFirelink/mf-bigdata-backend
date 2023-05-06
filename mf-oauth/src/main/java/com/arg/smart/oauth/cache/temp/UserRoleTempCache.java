package com.arg.smart.oauth.cache.temp;

import com.arg.smart.common.oauth.cache.UserRoleCache;
import com.arg.smart.common.oauth.api.entity.UserRole;
import com.arg.smart.oauth.mapper.SsoUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cgli
 * @description: 用户角色临时缓存
 * @date: 2022/12/5 22:00
 */
@Component("userRoleTempCache")
public class UserRoleTempCache extends UserRoleCache {
    @Resource
    SsoUserMapper ssoUserMapper;

    /**
     * key [0] userId [1] clientId
     *
     * @param key
     * @return
     */
    @Override
    protected List<UserRole> getFromDB(String... key) {
        return ssoUserMapper.getUserRoles(key[0], key[1]);
    }
}

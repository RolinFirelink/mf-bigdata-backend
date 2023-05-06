package com.arg.smart.common.oauth.api.remote;

import com.arg.smart.common.core.constants.Constants;
import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.constants.ServiceConstants;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.api.entity.UserInfo;
import com.arg.smart.common.oauth.api.entity.UserRole;
import com.arg.smart.common.oauth.api.fallback.RemoteUserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author cgli
 * @description: RPC用户服务
 * @date: 2021/12/1
 */
@FeignClient(contextId = "remoteUserService", value = ServiceConstants.OAUTH_SERVICE, fallbackFactory = RemoteUserFallback.class)
public interface RemoteUserService {
    /**
     * 根据token获取用户信息
     *
     * @param token
     * @param origin
     * @return
     */
    @GetMapping("/user/info")
    Result<UserInfo> getUserInfo(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @RequestHeader(Constants.AUTHENTICATION) String token);

    @GetMapping("/user/{id}")
    Result<UserInfo> getUserById(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @PathVariable("id") String id);

    /**
     * 获取用户角色
     *
     * @param origin
     * @param userId
     * @param clientId
     * @return
     */
    @GetMapping("/user/roles")
    Result<List<UserRole>> getRoles(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @RequestParam("userId") String userId, @RequestParam("clientId") String clientId);

    /**
     * 获取用户权限
     *
     * @param origin
     * @param userId
     * @param clientId
     * @return
     */
    @GetMapping("/user/permissions")
    Result<Set<String>> getPermissions(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @RequestParam("userId") String userId, @RequestParam("clientId") String clientId);
}
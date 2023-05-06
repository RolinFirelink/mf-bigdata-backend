package com.arg.smart.oauth.service.impl;

import com.arg.smart.oauth.cache.temp.ClientTempCache;
import com.arg.smart.oauth.entity.OAuthClient;
import com.arg.smart.oauth.service.ClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author cgli
 * @date: 2020/2/16 16:11
 */
@Service
public class ClientServiceImpl implements ClientService {
    @Resource
    ClientTempCache clientTempCache;

    /**
     * 根据客户端id从缓存和数据库中获取客户端信息
     *
     * @param clientId
     * @return
     */
    @Override
    public OAuthClient getClientById(String clientId) {
        return clientTempCache.getFromCacheAndDB(clientId);
    }
}

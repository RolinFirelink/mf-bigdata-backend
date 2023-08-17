package com.arg.smart.web.cms.service;

import java.util.Map;

/**
 * 远程文章服务
 */
public interface RemoteArticleService {
    Map<String, Object> indexAction(Integer id, Integer len, Integer content);
}

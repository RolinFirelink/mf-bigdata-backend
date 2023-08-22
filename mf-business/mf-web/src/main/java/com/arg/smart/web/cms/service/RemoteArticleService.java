package com.arg.smart.web.cms.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 远程文章服务
 */
public interface RemoteArticleService {
    List<JSONObject> fetch(Long fromId, Integer len);
}

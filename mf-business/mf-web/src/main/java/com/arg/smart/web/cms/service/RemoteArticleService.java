package com.arg.smart.web.cms.service;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.log.annotation.Log;

import java.util.List;
import java.util.Map;

/**
 * 远程文章服务
 */
public interface RemoteArticleService {

    List<JSONObject> fetch(Long fromId, Integer len);

   Integer analyticalTendencies2(String str);
}

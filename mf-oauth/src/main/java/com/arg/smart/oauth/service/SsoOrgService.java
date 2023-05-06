package com.arg.smart.oauth.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.oauth.entity.SsoOrg;
import com.arg.smart.oauth.req.ReqSsoOrg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 组织结构表
 * @author cgli
 * @date: 2022-09-20
 * @Version: V1.0.0
 */
public interface SsoOrgService extends IService<SsoOrg> {
    Result<SsoOrg> insertOrg(SsoOrg ssoOrg);

    Result<SsoOrg> updateOrg(SsoOrg ssoOrg);

    List<SsoOrg> queryOrg(ReqSsoOrg reqSsoOrg);

    boolean removeOrg(String id);
}

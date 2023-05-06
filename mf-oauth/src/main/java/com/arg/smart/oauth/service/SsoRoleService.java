package com.arg.smart.oauth.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.oauth.entity.SsoRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 角色信息表
 * @author cgli
 * @date: 2022-09-20
 * @Version: V1.0.0
 */
public interface SsoRoleService extends IService<SsoRole> {
    Result<SsoRole> insertRole(SsoRole ssoRole);

    Result<SsoRole> updateRole(SsoRole ssoRole);

    boolean deleteRole(String id);

    boolean roleCodeExist(String clientId, String roleId, String roleCode);

    List<String> getRoleMenus(String roleId);
}

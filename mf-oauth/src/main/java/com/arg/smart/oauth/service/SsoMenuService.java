package com.arg.smart.oauth.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.oauth.entity.SsoMenu;
import com.arg.smart.oauth.req.ReqSsoMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 菜单权限表
 * @author cgli
 * @date: 2022-09-21
 * @Version: V1.0.0
 */
public interface SsoMenuService extends IService<SsoMenu> {
    Result<SsoMenu> insertMenu(SsoMenu ssoMenu);

    List<SsoMenu> queryMenu(ReqSsoMenu reqSsoMenu, String userId);

    Result<SsoMenu> updateMenu(SsoMenu ssoMenu);

    Result<Boolean> deleteMenu(String menuId);
}

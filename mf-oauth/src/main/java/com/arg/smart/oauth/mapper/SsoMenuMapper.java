package com.arg.smart.oauth.mapper;

import com.arg.smart.oauth.entity.SsoMenu;
import com.arg.smart.oauth.req.ReqSsoMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Description: 菜单权限表
 * @author cgli
 * @date: 2022-09-21
 * @Version: V1.0.0
 */
public interface SsoMenuMapper extends BaseMapper<SsoMenu> {
    int insertMenu(SsoMenu ssoMenu);


    Integer queryMaxMenuLevel(@Param("reqSsoMenu") ReqSsoMenu reqSsoMenu, @Param("userId") String userId);

    List<SsoMenu> queryMenu(@Param("reqSsoMenu") ReqSsoMenu reqSsoMenu, @Param("levels") List<Integer> levels, @Param("userId") String userId);

    /**
     * 获取按钮权限用户
     * @param menuId
     * @return
     */
    List<String> queryMenuUser(@Param("menuId") String menuId);

    @Delete("delete from sso_role_menu where menu_id=#{menuId}")
    int deleteMenuRoles(@Param("menuId") String menuId);
}

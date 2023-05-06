package com.arg.smart.oauth.mapper;

import com.arg.smart.oauth.entity.SsoRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: 角色信息表
 * @author cgli
 * @date: 2022-09-20
 * @Version: V1.0.0
 */
public interface SsoRoleMapper extends BaseMapper<SsoRole> {
    int insertRoleMenus(@Param("roleId") String roleId, @Param("menuList") List<String> menuList);

    @Delete("delete from sso_role_menu where role_id=#{roleId}")
    int deleteRoleMenus(@Param("roleId") String roleId);

    int roleCodeExist(@Param("clientId") String clientId, @Param("roleId") String roleId, @Param("roleCode") String roleCode);

    /**
     * 获取角色下所有的用户
     *
     * @param roleId
     * @return
     */
    @Select("select user_id from sso_user_role where role_id=#{roleId}")
    List<String> getRoleUser(String roleId);

    @Select("select menu_id from sso_role_menu where role_id=#{roleId}")
    List<String> getRoleMenus(String roleId);
}

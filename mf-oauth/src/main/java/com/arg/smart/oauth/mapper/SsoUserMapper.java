package com.arg.smart.oauth.mapper;

import com.arg.smart.common.oauth.api.entity.UserInfo;
import com.arg.smart.common.oauth.api.entity.UserRole;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.req.ReqSsoUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author cgli
 * @date: 2020/2/13 17:13
 */
public interface SsoUserMapper extends BaseMapper<SsoUser> {

    /**
     * 根据账号查询用户信息 账号为account,phone,email,userId任意一种
     *
     * @param account
     * @return
     */
    SsoUser getUserByAccount(@Param("account") String account);

    /**
     * 通过ID获取用户
     *
     * @param userId
     * @return
     */
    @Select("select * from sso_user where id = #{userId}")
    SsoUser getUserById(@Param("userId") String userId);

    /**
     * 通过用户ID获取用户角色
     *
     * @param userId
     * @return
     */
    List<UserRole> getUserRoles(@Param("userId") String userId, @Param("clientId") String clientId);

    /**
     * 通过用户ID获取按钮权限
     *
     * @param userId
     * @param clientId
     * @return
     */
    String getUserPermissions(@Param("userId") String userId, @Param("clientId") String clientId);

    /**
     * 根据微信openId获取用户id
     *
     * @param openid
     * @return
     */
    @Select("select id from sso_user where openid=#{openId}")
    String getUserIdByOpenId(String openid);

    /**
     * 判断帐号是否存在该客户端权限
     *
     * @param userId
     * @param clientId
     * @return
     */
    @Select("select count(0) from sso_client_user where client_id=#{clientId} and user_id=#{userId}")
    Integer isUserClientExist(@Param("userId") String userId, @Param("clientId") String clientId);

    /**
     * 判断帐号是否存在（帐号可以是邮箱、用户名、手机号）
     *
     * @param account
     * @param userId  排除自己
     * @return
     */
    Integer isAccountExist(@Param("account") String account, @Param("userId") String userId);

    List<UserInfo> getUserList(ReqSsoUser reqSsoUser);

    int insertUserRole(@Param("userId") String userId, @Param("roles") List<String> roles);

    @Delete("delete from sso_user_role where user_id = #{userId}")
    int deleteUserRole(String userId);

    int insertUserOrg(@Param("userId") String userId, @Param("orgList") List<String> orgList);

    @Delete("delete from sso_org_user where user_id = #{userId}")
    int deleteUserOrg(String userId);

    int insertUserClient(@Param("userId") String userId, @Param("clientList") List<String> clientList);
}

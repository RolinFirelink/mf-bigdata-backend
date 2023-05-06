package com.arg.smart.oauth.mapper;

import com.arg.smart.oauth.entity.OAuthClient;
import org.apache.ibatis.annotations.Select;

/**
 * @author cgli
 * @date: 2020/2/16 16:05
 */
public interface ClientMapper {
    @Select("select * from sso_client_details where client_id = #{clientId}")
    OAuthClient getClientById(String clientId);
}

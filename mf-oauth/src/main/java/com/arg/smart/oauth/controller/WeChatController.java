package com.arg.smart.oauth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.exception.OAuthValidateException;
import com.arg.smart.common.core.utils.Utils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.common.oauth.common.OauthUtils;
import com.arg.smart.common.oauth.common.SerConstant;
import com.arg.smart.common.oauth.api.entity.UserInfo;
import com.arg.smart.common.oauth.entity.AccessToken;
import com.arg.smart.common.oauth.entity.RedisAccessToken;
import com.arg.smart.common.oauth.entity.WeChatToken;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.service.LoginService;
import com.arg.smart.oauth.service.OAuth2Service;
import com.arg.smart.oauth.service.SsoUserService;
import com.arg.smart.oauth.service.WeChatService;
import com.arg.smart.common.oauth.validator.WeChatTokenValidator;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cgli
 * @description: 小程序请求接口
 * @date: 2021/12/14 9:35
 */
@Api(tags = "小程序接口")
@RestController
@RequestMapping("/wx")
@Slf4j
public class WeChatController {
    @Resource
    WeChatService weChatService;
    @Resource
    LoginService loginService;
    @Resource
    OAuth2Service oAuth2Service;
    @Resource
    WeChatTokenValidator weChatTokenValidator;
    @Resource
    WxMaService wxMaService;

    @Resource
    SsoUserService ssoUserService;

    @PostMapping("/wechatLogin")
    @ApiOperation("微信登录")
    @ApiImplicitParams({@ApiImplicitParam(name = SerConstant.QR_CODE, value = "微信认证code", paramType = "query", required = true)})
    public Result<AccessToken> wechatLogin(String code) throws WxErrorException {
        WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
        String openid = session.getOpenid();
        SsoUser ssoUser = ssoUserService.getUserByOpenId(openid);
        if (ssoUser == null) {
            ssoUser = new SsoUser();
            ssoUser.setOpenid(openid);
            //锁定
            ssoUser.setStatus(1);
            ssoUserService.save(ssoUser);
            return Result.ok(null, "注册成功,请等待管理员通过");
        }
        if (ssoUser.getStatus() == 1) {
            return Result.fail("用户状态锁定中，请等待管理员通过");
        }
        return Result.ok(new AccessToken(weChatService.buildWeChatToken(openid, session.getSessionKey(), ssoUser.getId())));
    }

    @PostMapping("/wechatRegister")
    @ApiOperation("小程序注册")
    public Result<SsoUser> wechatRegister(@RequestBody SsoUser ssoUser) {
        //查找是否存在相同account
        SsoUser userByAccount = ssoUserService.getUserByAccount(ssoUser.getAccount());
        if (userByAccount != null) {
            return Result.fail("该用户名已存在，请更换其他用户名");
        }
        //查找是否有相同手机号
        String phone = ssoUser.getPhone();
        if (StringUtils.isNotBlank(phone)) {
            SsoUser ssoUser1 = ssoUserService.getUserByPhone(phone);
            if (ssoUser1 != null) {
                return Result.fail("该手机号用户已存在");
            }
        } else {
            ssoUser.setPhone(null);
        }
        //不启用
        ssoUser.setStatus(1);
        Result<SsoUser> ssoUserResult = ssoUserService.insertUser(ssoUser);
        if (ssoUserResult.isSuccess()) {
            return Result.ok(ssoUser, "注册成功，等待管理员通过审核");
        } else {
            return Result.fail("注册失败");
        }
    }

    @GetMapping("/bind/check")
    @ApiOperation("检查微信是否绑定 如果已绑定返回accessToken")
    @ApiImplicitParams({
            @ApiImplicitParam(name = SerConstant.QR_CODE, value = "微信认证code", paramType = "query", required = true)
    }
    )
    public AccessToken checkBind(String code) {
        WxMaJscode2SessionResult session = getSession(code);
        String openid = session.getOpenid();
        String userId = weChatService.getUserIdByOpenId(openid);
        if (!StringUtils.isEmpty(userId)) {
            return new AccessToken(weChatService.buildWeChatToken(openid, session.getSessionKey(), userId));
        }
        log.error("微信:" + openid + ",未绑定成功");
        throw new OAuthValidateException("错误:微信未绑定");
    }

    /**
     * 获取微信session
     *
     * @param code 微信认证code
     * @return
     */
    private WxMaJscode2SessionResult getSession(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new OAuthValidateException("错误:认证code不允许为空");
        }
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            if (session == null) {
                throw new OAuthValidateException("错误:未获取到用户session");
            }
            String openid = session.getOpenid();
            if (StringUtils.isEmpty(openid)) {
                throw new OAuthValidateException("错误:未获取openid");
            }
            if (StringUtils.isEmpty(session.getSessionKey())) {
                throw new OAuthValidateException("错误:未获取sessionKey");
            }
            return session;
        } catch (WxErrorException e) {
            throw new OAuthValidateException(e.getMessage());
        }
    }

    @PostMapping("/bind")
    @ApiOperation("绑定账号和微信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = OAuth.OAUTH_CODE, value = "微信认证code", paramType = "query", required = true),
            @ApiImplicitParam(name = OAuth.OAUTH_USERNAME, value = "账号，手机，email grant_type=password时必须", paramType = "query", required = true),
            @ApiImplicitParam(name = OAuth.OAUTH_PASSWORD, value = "密码 grant_type=password时必须", paramType = "query", required = true)
    })
    @Log(title = "绑定微信", operateType = OperateType.LOGIN)
    public AccessToken bindWeChat(HttpServletRequest request) {
        String code = request.getParameter(OAuth.OAUTH_CODE);
        WxMaJscode2SessionResult session = getSession(code);
        String openid = session.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            throw new OAuthValidateException("错误:未获取openid");
        }
        Result<String> loginResult = loginService.login(request);
        if (loginResult.isSuccess() && weChatService.bindWeChat(openid, loginResult.getData())) {
            return weChatService.convertToken(weChatService
                    .buildWeChatToken(openid, session.getSessionKey(), loginResult.getData()));
        }
        throw new OAuthValidateException("错误:微信账号绑定失败");
    }


    @ApiOperation("获取用户信息")
    @GetMapping("/userInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = OAuth.HeaderType.AUTHORIZATION, value = "认证token，header和access_token参数两种方式任意一种即可，格式为Bearer+token组合，例如Bearer39a5304bc77c655afbda6b967e5346fa", paramType = "header"),
            @ApiImplicitParam(name = OAuth.OAUTH_ACCESS_TOKEN, value = "token值 header和access_token参数两种方式任意一种即可", paramType = "query")
    })
    @Log(title = "微信获取用户信息", operateType = OperateType.QUERY)
    public Result<UserInfo> getUserInfo(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        Result<WeChatToken> result = weChatTokenValidator.validate(request);
        if (!result.isSuccess()) {
            throw new OAuthValidateException(result.getMsg());
        }
        return Result.ok(oAuth2Service.getUserInfo(result.getData().getUserId()));
    }

    @PostMapping("/login/pwd")
    @ApiOperation("通过密码绑定账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = SerConstant.QR_CODE, value = "微信认证code", paramType = "query", required = true),
            @ApiImplicitParam(name = SerConstant.NICKNAME, value = "微信昵称", paramType = "query"),
            @ApiImplicitParam(name = SerConstant.PHONE, value = "手机", paramType = "query", required = true),
            @ApiImplicitParam(name = SerConstant.PASSWORD, value = "短信验证码", paramType = "query", required = true)
    })
    @Log(title = "微信登录", operateType = OperateType.LOGIN)
    public AccessToken pwdLogin(String code, String nickname, String phone, String password) {
        WxMaJscode2SessionResult session = getSession(code);
        String openid = session.getOpenid();
        String userId = weChatService.getUserIdByOpenId(openid);
        if (!StringUtils.isEmpty(userId)) {
            return new AccessToken(weChatService
                    .buildWeChatToken(openid, session.getSessionKey(), userId));
        }
        //TODO 此处clientId暂时写死后期可能需要修改
        Result<String> loginResult = loginService.login(phone, password, SerConstant.LoginType.短信登录, "system", "false");
        if (StringUtils.isEmpty(nickname)) {
            nickname = Utils.phoneMasking(phone);
        }
        if (loginResult.isSuccess() && weChatService.bindWeChat(openid, loginResult.getData(), nickname)) {
            return new AccessToken(weChatService
                    .buildWeChatToken(openid, session.getSessionKey(), loginResult.getData()));
        }
        throw new OAuthValidateException("错误:绑定微信失败");
    }


    @ApiOperation("获取sessionKey 与手机登录联合使用")
    @GetMapping("/session")
    @ApiImplicitParams({
            @ApiImplicitParam(name = SerConstant.QR_CODE, value = "微信认证code", paramType = "query", required = true)
    })
    public Map<String, String> getSessionKey(String code) {
        WxMaJscode2SessionResult session = getSession(code);
        loginService.sessionKeyTempCache(session.getSessionKey(), session.getOpenid());
        Map<String, String> map = new HashMap<>();
        map.put("sessionKey", session.getSessionKey());
        return map;
    }

    @ApiOperation("通过手机号绑定账号 先调用/sessionKey方法")
    @PostMapping("/login/phone")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionKey", value = "微信登录返回的sessionKey", paramType = "query", required = true),
            @ApiImplicitParam(name = "nickname", value = "微信昵称", paramType = "query"),
            @ApiImplicitParam(name = "encryptedData", value = "微信获取手机号数据", paramType = "query", required = true),
            @ApiImplicitParam(name = "iv", value = "微信获取手机号iv", paramType = "query", required = true)
    })
    public AccessToken phoneLogin(String sessionKey, String nickname, String encryptedData, String iv) {
        String openid = loginService.getOpenIdBySessionKey(sessionKey);
        if (StringUtils.isEmpty(openid)) {
            log.error("sessionKey:" + sessionKey + ",手机号绑定失败");
            throw new OAuthValidateException("错误:sessionKey有误");
        }
        String userId = weChatService.getUserIdByOpenId(openid);
        if (!StringUtils.isEmpty(userId)) {
            return new AccessToken(weChatService.buildWeChatToken(openid, sessionKey, userId));
        }
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        String phone = phoneNoInfo.getPhoneNumber();
        //TODO 此处clientId暂时写死后期可能需要修改
        Result<String> loginResult = loginService.login(phone, sessionKey, SerConstant.LoginType.微信登录, "system", "false");
        if (StringUtils.isEmpty(nickname)) {
            nickname = Utils.phoneMasking(phone);
        }
        if (loginResult.isSuccess() && weChatService.bindWeChat(openid, loginResult.getData(), nickname)) {
            return new AccessToken(weChatService.buildWeChatToken(openid, sessionKey, loginResult.getData()));
        }
        throw new OAuthValidateException("错误:绑定微信失败");
    }
}
package com.arg.smart.test.controller;


import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.api.entity.UserInfo;
import com.arg.smart.common.oauth.api.remote.RemoteUserService;
import com.arg.smart.test.entity.TestParam;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cgli
 * @description: 测试中心
 * @date: 2021/12/3 17:12
 */
@RestController
@Api(tags = "测试接口")
public class TestController {
    @Resource
    RemoteUserService remoteUserService;

    @GetMapping("/user")
    public Result<UserInfo> getUserInfo(HttpServletRequest request) {
        String token = AuthInfoUtils.getAccessToken(request);
        return remoteUserService.getUserInfo(RPCConstants.INNER, token);
    }

    @GetMapping("/curUser")
    public Result<UserInfo> getCurUserInfo() {
        return remoteUserService.getUserById(RPCConstants.INNER, "1");
    }

    @PostMapping("/testParam")
    public Result<TestParam> testParam(@RequestBody TestParam param) {
        return Result.ok(param);
    }
}

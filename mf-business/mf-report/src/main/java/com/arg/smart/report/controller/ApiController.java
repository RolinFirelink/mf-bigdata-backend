package com.arg.smart.report.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.arg.smart.report.common.base.BaseController;
import com.arg.smart.report.common.domain.AjaxResult;
import com.arg.smart.report.entity.SysUser;
import com.arg.smart.report.service.ISysUserService;
import com.arg.smart.report.utils.SaTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class ApiController extends BaseController {
    @Autowired
    private ISysUserService iSysUserService;

    @ApiOperation(value = "登陆", notes = "登陆")
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult APIlogin(@RequestBody SysUser user, HttpServletRequest request) {

        // 判断是否登陆
        if (StpUtil.isLogin()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userinfo", SaTokenUtil.getUser());
            map.put("token", StpUtil.getTokenInfo());
            return success().put("data", map);
        } else {
            if (StrUtil.isNotBlank(user.getUsername()) && StrUtil.isNotBlank(user.getPassword())) {
                SysUser sysUser = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, user.getUsername()).eq(SysUser::getPassword, SecureUtil.md5(user.getUsername())).last("LIMIT 1"));
                if (sysUser != null) {
                    StpUtil.login(sysUser.getId());
                    SaTokenUtil.setUser(sysUser);
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", sysUser);
                    map.put("token", StpUtil.getTokenInfo());
                    return success().put("data", map);
                } else {
                    return error(500, "账户或者密码错误");
                }
            } else {
                return error(500, "账户密码不能为空");
            }
        }
    }

    @ApiOperation(value = "登陆", notes = "登陆")
    @GetMapping("/logout")
    @ResponseBody
    public AjaxResult logout() {
        // 判断是否登陆
        StpUtil.logout();
        return success();

    }


    @ApiOperation(value = "获取oss地址", notes = "获取oss地址")
    @GetMapping("/getOssInfo")
    @ResponseBody
    public AjaxResult getOssInfo() {
        // 判断是否登陆
        StpUtil.logout();
        return success();
    }

}

package com.arg.smart.oauth.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.utils.TreeUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.oauth.entity.SsoMenu;
import com.arg.smart.oauth.req.ReqSsoMenu;
import com.arg.smart.oauth.service.OAuth2Service;
import com.arg.smart.oauth.service.SsoMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 菜单表
 * @author cgli
 * @date: 2022-09-21
 * @Version: V1.0.0
 */
@Slf4j
@Api(tags = "菜单表")
@RestController
@RequestMapping("/menu")
public class SsoMenuController {
    @Resource
    SsoMenuService ssoMenuService;
    @Resource
    OAuth2Service oAuth2Service;

    /**
     * 分页列表查询
     *
     * @param reqSsoMenu
     * @return
     */
    @ApiOperation(value = "菜单表-分页列表查询", notes = "菜单表-分页列表查询")
    @GetMapping
    @RequiresPermissions("sys:menu:query")
    public Result<List<SsoMenu>> queryList(ReqSsoMenu reqSsoMenu) {
        return queryMenu(reqSsoMenu, null);
    }

    @ApiOperation(value = "获取菜单树")
    @GetMapping("/tree")
    public Result<List<SsoMenu>> queryMenuTree(ReqSsoMenu reqSsoMenu) {
        return queryMenu(reqSsoMenu, oAuth2Service.getCurrentUser());
    }

    @ApiOperation("获取角色树-左侧菜单")
    @GetMapping("/roleTree")
    public Result<List<SsoMenu>> queryRoleMenuTree() {
        String currentUser = oAuth2Service.getCurrentUser();
        log.info("currentUser"+currentUser);
        return queryMenu(new ReqSsoMenu().setClientId(AuthInfoUtils.getCurrentClientId()).setNoButton(true), currentUser);
    }

    private Result<List<SsoMenu>> queryMenu(ReqSsoMenu reqSsoMenu, String userId) {
        List<SsoMenu> list = ssoMenuService.queryMenu(reqSsoMenu, userId);
        List<SsoMenu> menuTrees = new ArrayList<>();
        TreeUtils.buildTree("", list, menuTrees, SsoMenu.class);
        return Result.ok(menuTrees, "菜单表-查询成功!");
    }

    /**
     * 添加
     *
     * @param ssoMenu
     * @return
     */
    @Log(title = "菜单表-添加", operateType = OperateType.INSERT)
    @ApiOperation(value = "菜单表-添加", notes = "菜单表-添加")
    @PostMapping
    @RequiresPermissions("sys:menu:insert")
    public Result<SsoMenu> add(@RequestBody SsoMenu ssoMenu) {
        return ssoMenuService.insertMenu(ssoMenu);
    }

    /**
     * 编辑
     *
     * @param ssoMenu
     * @return
     */
    @Log(title = "菜单表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation(value = "菜单表-编辑", notes = "菜单表-编辑")
    @PutMapping
    @RequiresPermissions("sys:menu:update")
    public Result<SsoMenu> edit(@RequestBody SsoMenu ssoMenu) {
        return ssoMenuService.updateMenu(ssoMenu);
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @Log(title = "菜单表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation(value = "菜单表-通过id删除", notes = "菜单表-通过id删除")
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:menu:delete")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        return ssoMenuService.deleteMenu(id);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "菜单表-通过id查询", notes = "菜单表-通过id查询")
    @GetMapping("/{id}")
    @RequiresPermissions("sys:menu:query")
    public Result<SsoMenu> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        SsoMenu ssoMenu = ssoMenuService.getById(id);
        return Result.ok(ssoMenu, "菜单表-查询成功!");
    }
}

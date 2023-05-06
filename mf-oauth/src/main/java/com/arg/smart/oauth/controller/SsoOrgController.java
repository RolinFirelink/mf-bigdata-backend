package com.arg.smart.oauth.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.utils.TreeUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.oauth.entity.SsoOrg;
import com.arg.smart.oauth.req.ReqSsoOrg;
import com.arg.smart.oauth.service.SsoOrgService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 组织结构表
 * @author cgli
 * @date: 2022-09-20
 * @Version: V1.0.0
 */
@Slf4j
@Api(tags = "组织结构表")
@RestController
@RequestMapping("/org")
public class SsoOrgController {
    @Resource
    private SsoOrgService ssoOrgService;

    /**
     * 分页列表查询
     *
     * @param reqSsoOrg
     * @param reqPage
     * @return
     */
    @ApiOperation(value = "组织结构表-分页列表查询", notes = "组织结构表-分页列表查询")
    @GetMapping
    @RequiresPermissions("sys:org:query")
    public Result<PageResult<SsoOrg>> queryPageList(ReqSsoOrg reqSsoOrg, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(ssoOrgService.list()), "组织结构表-查询成功!");
    }

    @ApiOperation(value = "获取组织结构")
    @GetMapping("/tree")
    public Result<List<SsoOrg>> queryOrgTree(ReqSsoOrg reqSsoOrg) {
        List<SsoOrg> list = ssoOrgService.queryOrg(reqSsoOrg);
        List<SsoOrg> orgList = new ArrayList<>();
        TreeUtils.buildTree("", list, orgList, SsoOrg.class);
        return Result.ok(orgList, "组织结构表-查询成功!");
    }

    /**
     * 添加
     *
     * @param ssoOrg
     * @return
     */
    @Log(title = "组织结构表-添加", operateType = OperateType.INSERT)
    @ApiOperation(value = "组织结构表-添加", notes = "组织结构表-添加")
    @PostMapping
    @RequiresPermissions("sys:org:insert")
    public Result<SsoOrg> add(@RequestBody SsoOrg ssoOrg) {
        return ssoOrgService.insertOrg(ssoOrg);
    }

    /**
     * 编辑
     *
     * @param ssoOrg
     * @return
     */
    @Log(title = "组织结构表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation(value = "组织结构表-编辑", notes = "组织结构表-编辑")
    @PutMapping
    @RequiresPermissions("sys:org:update")
    public Result<SsoOrg> edit(@RequestBody SsoOrg ssoOrg) {
        return ssoOrgService.updateOrg(ssoOrg);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @Log(title = "组织结构表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation(value = "组织结构表-通过id删除", notes = "组织结构表-通过id删除")
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:org:delete")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (ssoOrgService.removeOrg(id)) {
            return Result.ok("组织结构表-删除成功!");
        }
        return Result.fail("错误:组织结构表-删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "组织结构表-通过id查询", notes = "组织结构表-通过id查询")
    @GetMapping("/{id}")
    @RequiresPermissions("sys:org:query")
    public Result<SsoOrg> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        SsoOrg ssoOrg = ssoOrgService.getById(id);
        return Result.ok(ssoOrg, "组织结构表-查询成功!");
    }
}

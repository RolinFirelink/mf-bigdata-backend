package com.arg.smart.sys.controller;

import com.arg.smart.common.code.api.remote.RemoteCodeService;
import com.arg.smart.common.code.api.req.ReqCode;
import com.arg.smart.common.code.api.vo.CodeVo;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.exception.MyRuntimeException;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.sys.entity.CodeBuild;
import com.arg.smart.sys.req.ReqCodeBuild;
import com.arg.smart.sys.service.CodeBuildService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 代码构建
 * @author cgli
 * @date: 2023-04-11
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "代码构建")
@RestController
@RequestMapping("/codeBuild")
public class CodeBuildController {
    @Resource
    private CodeBuildService codeBuildService;
    @Resource
    private RemoteCodeService remoteCodeService;

    /**
     * 分页列表查询
     *
     * @param reqCodeBuild 代码构建请求参数
     * @return 返回代码构建-分页列表
     */
    @ApiOperation(value = "代码构建-分页列表查询", notes = "代码构建-分页列表查询")
    @GetMapping
    public Result<PageResult<CodeBuild>> queryPageList(ReqCodeBuild reqCodeBuild, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(codeBuildService.list()), "代码构建-查询成功!");
    }

    /**
     * 添加
     *
     * @param codeBuild 代码构建对象
     * @return 返回代码构建-添加结果
     */
    @Log(title = "代码构建-添加", operateType = OperateType.INSERT)
    @ApiOperation("代码构建-添加")
    @PostMapping
    public Result<CodeBuild> add(@RequestBody CodeBuild codeBuild) {
        return codeBuildService.insertCodeBuild(codeBuild);
    }

    @Log(title = "查看代码", operateType = OperateType.QUERY)
    @ApiOperation("查看代码")
    @GetMapping("/view")
    public Result<List<CodeVo>> query(ReqCode reqCode) {
        return remoteCodeService.getCode(reqCode);
    }

    @Log(title = "下载代码", operateType = OperateType.QUERY)
    @ApiOperation("下载代码")
    @GetMapping("/download")
    public void downloadCode(ReqCode reqCode, HttpServletResponse response) throws IOException {
        Result<byte[]> result = remoteCodeService.downloadCode(reqCode);
        if (!result.isSuccess()) {
            throw new MyRuntimeException(result.getMsg());
        }
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=mfish-code.zip");
        response.addHeader("Content-Length", result.getData().length + "");
        response.setContentType("application/x-zip-compressed; charset=UTF-8");
        IOUtils.write(result.getData(), response.getOutputStream());
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回代码构建-删除结果
     */
    @Log(title = "代码构建-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("代码构建-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (codeBuildService.removeById(id)) {
            return Result.ok(true, "代码构建-删除成功!");
        }
        return Result.fail(false, "错误:代码构建-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回代码构建-删除结果
     */
    @Log(title = "代码构建-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("代码构建-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.codeBuildService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "代码构建-批量删除成功!");
        }
        return Result.fail(false, "错误:代码构建-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回代码构建对象
     */
    @ApiOperation("代码构建-通过id查询")
    @GetMapping("/{id}")
    public Result<CodeBuild> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        CodeBuild codeBuild = codeBuildService.getById(id);
        return Result.ok(codeBuild, "代码构建-查询成功!");
    }
}

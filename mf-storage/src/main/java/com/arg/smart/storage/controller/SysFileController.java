package com.arg.smart.storage.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.file.entity.StorageInfo;
import com.arg.smart.common.file.handler.StorageHandler;
import com.arg.smart.storage.req.ReqSysFile;
import com.arg.smart.storage.service.StorageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: 文件存储
 * @author cgli
 * @date: 2023-03-02
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "文件存储")
@RestController
@RequestMapping("/sysFile")
public class SysFileController {
    @Resource
    private StorageService storageService;
    @Resource
    private StorageHandler storageHandler;

    /**
     * 分页列表查询
     *
     * @param reqSysFile 文件存储请求参数
     * @return 返回文件存储-分页列表
     */
    @ApiOperation(value = "文件存储-分页列表查询", notes = "文件存储-分页列表查询")
    @GetMapping
    @RequiresPermissions("sys:file:query")
    public Result<PageResult<StorageInfo>> queryPageList(ReqSysFile reqSysFile, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<StorageInfo>()
                .eq(StorageInfo::getDelFlag, 0)
                .like(reqSysFile.getFileName() != null, StorageInfo::getFileName, reqSysFile.getFileName())
                .like(reqSysFile.getFileType() != null, StorageInfo::getFileType, reqSysFile.getFileType())
                .orderByDesc(StorageInfo::getCreateTime);
        return Result.ok(new PageResult<>(storageService.list(queryWrapper)), "文件存储-查询成功!");
    }

    @Log(title = "设置文件状态", operateType = OperateType.UPDATE)
    @ApiOperation("设置文件状态 0 公开 1 私密")
    @PutMapping("/status")
    @RequiresPermissions("sys:file:status")
    public Result<StorageInfo> setStatus(@RequestBody StorageInfo storageInfo) {
        StorageInfo oldFile = storageService.getById(storageInfo.getId());
        if (oldFile == null) {
            return Result.fail(storageInfo, "错误:未找到文件!");
        }
        if (storageService.updateById(new StorageInfo().setId(storageInfo.getId())
                .setIsPrivate(storageInfo.getIsPrivate()).setFileUrl(storageHandler
                        .buildFileUrl(oldFile.getFilePath() + "/" + oldFile.getFileKey(), storageInfo.getIsPrivate())))) {
            return Result.ok(storageInfo, "文件状态设置成功!");
        }
        return Result.fail(storageInfo, "错误:文件状态设置失败!");
    }

    /**
     * 通过id逻辑删除
     *
     * @param id 唯一ID
     * @return 返回文件存储-删除结果
     */
    @Log(title = "文件存储-通过id逻辑删除", operateType = OperateType.DELETE)
    @ApiOperation("文件存储-通过id删除")
    @DeleteMapping("/logic/{id}")
    @RequiresPermissions("sys:file:delete")
    public Result<Boolean> logicDelete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (storageService.updateById(new StorageInfo().setId(id).setDelFlag(1))) {
            return Result.ok(true, "文件存储-删除成功!");
        }
        return Result.fail(false, "错误:文件存储-删除失败!");
    }

    @Log(title = "文件存储-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("文件存储-通过id删除")
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:file:delete")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        StorageInfo storageInfo = storageService.getById(id);
        if (storageInfo == null) {
            return Result.fail(false, "错误:未找到文件!");
        }
        if (storageService.removeById(new StorageInfo().setId(id))) {
            storageHandler.delete(storageInfo.getFilePath() + "/" + storageInfo.getFileKey());
            return Result.ok(true, "文件存储-删除成功!");
        }
        return Result.fail(false, "错误:文件存储-删除失败!");
    }
}

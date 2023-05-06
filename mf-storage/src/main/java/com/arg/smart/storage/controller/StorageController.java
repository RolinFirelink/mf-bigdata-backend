package com.arg.smart.storage.controller;

import com.arg.smart.common.core.exception.OAuthValidateException;
import com.arg.smart.common.core.utils.ServletUtils;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.file.handler.StorageHandler;
import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.common.oauth.validator.TokenValidator;
import com.arg.smart.common.file.entity.StorageInfo;
import com.arg.smart.storage.service.StorageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @description: 文件缓存
 * @author cgli
 * @date: 2023/1/5 15:42
 */
@Api(tags = "文件缓存操作")
@RestController
@RequestMapping("/file")
@Slf4j
public class StorageController {
    @Resource
    StorageHandler storageHandler;
    @Resource
    StorageService storageService;
    @Resource
    TokenValidator tokenValidator;

    @ApiOperation("文件新增")
    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true),
            @ApiImplicitParam(name = "fileName", value = "文件名称 默认为空字符串"),
            @ApiImplicitParam(name = "path", value = "定义特殊文件路径 默认为空字符串"),
            @ApiImplicitParam(name = "isPrivate", value = "是否私有文件，私有文件需要带token才允许访问 1是 0否 默认是"),
    })
    @RequiresPermissions("sys:file:upload")
    public Result<StorageInfo> upload(@RequestParam("file") MultipartFile file
            , @RequestParam(name = "fileName", defaultValue = "") String fileName
            , @RequestParam(name = "path", defaultValue = "") String path
            , @RequestParam(name = "isPrivate", defaultValue = "1") Integer isPrivate) throws IOException {
        String originalFilename = fileName;
        if (StringUtils.isEmpty(originalFilename)) {
            originalFilename = file.getOriginalFilename();
        }
        StorageInfo info = storageHandler.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename, path, isPrivate);
        if (storageService.save(info)) {
            return Result.ok(info, "文件新增成功");
        }
        return Result.fail(info, "错误:文件新增失败");
    }

    @ApiOperation("文件获取")
    @GetMapping("/{key:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> fetch(@ApiParam(name = "key", value = "文件key") @PathVariable String key) {
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }
        StorageInfo storageInfo = storageService.getOne(new LambdaQueryWrapper<StorageInfo>().eq(StorageInfo::getFileKey, key));
        if (storageInfo == null || (storageInfo.getDelFlag() != null && storageInfo.getDelFlag().equals(1))) {
            return ResponseEntity.notFound().build();
        }
        //如果文件是私有文件需要校验token后访问
        if (storageInfo.getIsPrivate() != null && storageInfo.getIsPrivate().equals(1)) {
            Result result = tokenValidator.validator(ServletUtils.getRequest());
            if (!result.isSuccess()) {
                throw new OAuthValidateException(result.getMsg());
            }
        }
        return storageHandler.fetch(key, storageInfo.getFileName(), storageInfo.getFilePath(), storageInfo.getFileType(), storageInfo.getFileSize());
    }

}
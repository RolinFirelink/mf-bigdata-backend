package com.arg.smart.sys.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.entity.CodeBuild;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 代码构建
 * @author cgli
 * @date: 2023-04-11
 * @version: V1.0.0
 */
public interface CodeBuildService extends IService<CodeBuild> {
    Result<CodeBuild> insertCodeBuild(CodeBuild codeBuild);
}

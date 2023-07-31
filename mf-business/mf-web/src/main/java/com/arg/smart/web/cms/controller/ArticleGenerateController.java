package com.arg.smart.web.cms.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cms.entity.ArticleCategory;
import com.arg.smart.web.cms.service.ArticleGenerationService;
import com.arg.smart.web.product.req.ReqProductPrice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zsj
 * @description: 文章生成
 * @date: 2023-07-29
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "文章生成")
@RestController
@RequestMapping("/cms/generate")
public class ArticleGenerateController {

    @Resource
    private ArticleGenerationService articleGenerationService;

    /**
     * 生成价格日报
     */
    @Log(title = "生成价格日报", operateType = OperateType.INSERT)
    @ApiOperation("生成价格日报")
    @PostMapping("/public/priceDailyReport/{flag}")
    public Result<Integer> generatePriceDailyReport(@PathVariable("flag") Integer flag) {
        if (articleGenerationService.generatePriceDaily(flag)) {
            return Result.ok(flag, "价格日报生成成功!");
        }
        return Result.fail(flag, "错误:价格日报生成失败！");
    }
}

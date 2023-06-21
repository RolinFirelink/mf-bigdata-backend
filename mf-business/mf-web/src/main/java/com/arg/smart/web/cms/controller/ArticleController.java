package com.arg.smart.web.cms.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.req.ReqArticle;
import com.arg.smart.web.cms.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author cgli
 * @description: 文章内容
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "文章内容")
@RestController
@RequestMapping("/cms/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    /**
     * 按分类查询最新的文章标题列表
     * @param categoryId 分类ID
     * @param count 条数
     */
    @ApiOperation(value = "PC端-农业咨询", notes = "PC端-农业咨询")
    @GetMapping("/public/{categoryId}/{count}")
    public Result<List<Article>> listTitles(@PathVariable("categoryId") Long categoryId, @PathVariable("count") Integer count) {
        return Result.ok(articleService.listTitles(categoryId,count), "文章内容-查询成功!");
    }

    /**
     * PC端分页获取农业咨询
     *
     * @param reqArticle 文章查询参数
     * @param reqPage 分页参数
     * @return 农业咨询列表
     */
    @ApiOperation(value = "PC端-农业咨询", notes = "PC端-农业咨询")
    @GetMapping("/public/pageList")
    public Result<PageResult<Article>> pageList(ReqArticle reqArticle, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(articleService.pageList(reqArticle), "文章内容-查询成功!");
    }


    /**
     * 分页列表查询
     *
     * @param reqArticle 文章内容请求参数
     * @return 返回文章内容-分页列表
     */
    @ApiOperation(value = "文章内容-分页列表查询", notes = "文章内容-分页列表查询")
    @GetMapping
    public Result<PageResult<Article>> queryPageList(ReqArticle reqArticle, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(articleService.listArticle(reqArticle), "文章内容-查询成功!");
    }

    /**
     * 添加
     *
     * @param article 文章内容对象
     * @return 返回文章内容-添加结果
     */
    @Log(title = "文章内容-添加", operateType = OperateType.INSERT)
    @ApiOperation("文章内容-添加")
    @PostMapping
    public Result<Article> add(@RequestBody Article article) {
        if (articleService.saveArticle(article)) {
            return Result.ok(article, "文章内容-添加成功!");
        }
        return Result.fail(article, "错误:文章内容-添加失败!");
    }

    /**
     * 编辑
     *
     * @param article 文章内容对象
     * @return 返回文章内容-编辑结果
     */
    @Log(title = "文章内容-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("文章内容-编辑")
    @PutMapping
    public Result<Article> edit(@RequestBody Article article) {
        if (articleService.updateArticle(article)) {
            return Result.ok(article, "文章内容-编辑成功!");
        }
        return Result.fail(article, "错误:文章内容-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回文章内容-删除结果
     */
    @Log(title = "文章内容-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("文章内容-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (articleService.removeArticle(id)) {
            return Result.ok(true, "文章内容-删除成功!");
        }
        return Result.fail(false, "错误:文章内容-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回文章内容-删除结果
     */
    @Log(title = "文章内容-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("文章内容-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.articleService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "文章内容-批量删除成功!");
        }
        return Result.fail(false, "错误:文章内容-批量删除失败!");
    }

    /**
     * 通过id查询文章
     *
     * @param id 唯一ID
     * @return 返回文章对象
     */
    @ApiOperation("文章-通过id查询")
    @GetMapping("/{id}")
    public Result<Article> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable Long id) {
        Article article = articleService.getById(id);
        return Result.ok(article, "文章内容-查询成功!");
    }

    /**
     * 通过id查询文章内容
     *
     * @param id 唯一ID
     * @return 返回文章内容
     */
    @ApiOperation("文章内容-通过id查询")
    @GetMapping("/content/{id}")
    public Result<String> getContent(@ApiParam(name = "id", value = "唯一性ID") @PathVariable Long id) {
        String content = articleService.getContent(id);
        return Result.ok(content, "文章内容-查询成功!");
    }
}

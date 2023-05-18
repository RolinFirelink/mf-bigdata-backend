package com.arg.smart.web.cms.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cms.entity.Article;
import com.arg.smart.web.cms.req.ReqArticle;
import com.arg.smart.web.cms.service.ArticleService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 文章内容
 * @author cgli
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
	 * 分页列表查询
	 *
	 * @param reqArticle 文章内容请求参数
	 * @return 返回文章内容-分页列表
	 */
	@ApiOperation(value = "文章内容-分页列表查询", notes = "文章内容-分页列表查询")
	@GetMapping
	public Result<PageResult<Article>> queryPageList(ReqArticle reqArticle, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(articleService.list()), "文章内容-查询成功!");
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
		if (articleService.save(article)) {
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
		if (articleService.updateById(article)) {
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
		if (articleService.removeById(id)) {
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
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回文章内容对象
	 */
	@ApiOperation("文章内容-通过id查询")
	@GetMapping("/{id}")
	public Result<Article> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Article article = articleService.getById(id);
		return Result.ok(article, "文章内容-查询成功!");
	}
}
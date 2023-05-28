package com.arg.smart.web.cms.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cms.entity.ArticleCategory;
import com.arg.smart.web.cms.req.ReqArticleCategory;
import com.arg.smart.web.cms.service.ArticleCategoryService;
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
 * @description: 文章
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "文章")
@RestController
@RequestMapping("/articleCategory")
public class ArticleCategoryController {
	@Resource
	private ArticleCategoryService articleCategoryService;
	/**
	 * 树形列表查询
	 *
	 * @param reqArticleCategory 文章请求参数
	 * @return 返回文章-树形列表（全部）
	 */
	@ApiOperation(value = "文章-分页列表查询", notes = "文章-分页列表查询")
	@GetMapping
	public Result<List<ArticleCategory>> queryTreeList(ReqArticleCategory reqArticleCategory, ReqPage reqPage) {
	    return Result.ok((articleCategoryService.listCategory(reqArticleCategory)), "文章-查询成功!");
	}
	/**
	 * 添加
	 *
	 * @param articleCategory 文章对象
	 * @return 返回文章-添加结果
	 */
	@Log(title = "文章-添加", operateType = OperateType.INSERT)
	@ApiOperation("文章-添加")
	@PostMapping
	public Result<ArticleCategory> add(@RequestBody ArticleCategory articleCategory) {
		if (articleCategoryService.save(articleCategory)) {
			return Result.ok(articleCategory, "文章-添加成功!");
		}
        return Result.fail(articleCategory, "错误:文章-添加失败!");
	}
	/**
	 * 编辑
	 *
	 * @param articleCategory 文章对象
	 * @return 返回文章-编辑结果
	 */
	@Log(title = "文章-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("文章-编辑")
	@PutMapping
	public Result<ArticleCategory> edit(@RequestBody ArticleCategory articleCategory) {
		if (articleCategoryService.updateById(articleCategory)) {
		    return Result.ok(articleCategory, "文章-编辑成功!");
		}
		return Result.fail(articleCategory, "错误:文章-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回文章-删除结果
	 */
	@Log(title = "文章-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("文章-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (articleCategoryService.removeById(id)) {
			return Result.ok(true, "文章-删除成功!");
		}
		return Result.fail(false, "错误:文章-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回文章-删除结果
	 */
	@Log(title = "文章-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("文章-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.articleCategoryService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "文章-批量删除成功!");
		}
		return Result.fail(false, "错误:文章-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回文章对象
	 */
	@ApiOperation("文章-通过id查询")
	@GetMapping("/{id}")
	public Result<ArticleCategory> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ArticleCategory articleCategory = articleCategoryService.getById(id);
		return Result.ok(articleCategory, "文章-查询成功!");
	}
}

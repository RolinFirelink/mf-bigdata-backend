package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.MaterialCategory;
import com.arg.smart.web.product.req.ReqMaterialCategory;
import com.arg.smart.web.product.service.MaterialCategoryService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @description: 产品类型表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品类型表")
@RestController
@RequestMapping("/materialCategory")
public class MaterialCategoryController {
	@Resource
	private MaterialCategoryService materialCategoryService;

	/**
	 * 1、不带参数
	 * 分页查询产品的一级分类
	 * 2、带参数
	 * 查询所有
	 *
	 * @param reqMaterialCategory 产品类型表请求参数
	 * @return 返回产品类型表-分页一级分类列表
	 */
	@ApiOperation(value = "产品类型表-一级分类分页列表查询", notes = "产品类型表-一级分类分页列表查询")
	@GetMapping
	public Result<PageResult<MaterialCategory>> queryPageList(ReqMaterialCategory reqMaterialCategory, ReqPage reqPage) {
		String name = reqMaterialCategory.getName();
//		if(name != null){
			return Result.ok(materialCategoryService.listCategoryByName(name));
//		}
//		PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
//		return Result.ok(materialCategoryService.listCategory(), "产品类型表-查询成功!");
	}

	/**
	 * 根据父ID查询所有
	 *
	 * @param parentId 产品分类父ID
	 * @return 返回产品类型-列表
	 */
	@ApiOperation(value = "产品类型-根据父ID查询", notes = "产品类型-根据父ID查询")
	@GetMapping("listByParentId/{parentId}")
	public Result<List<MaterialCategory>> listByParentId(@PathVariable("parentId") Long parentId) {
		return Result.ok(materialCategoryService.listByParentId(parentId), "产品类型表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param materialCategory 产品类型表对象
	 * @return 返回产品类型表-添加结果
	 */
	@Log(title = "产品类型表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品类型表-添加")
	@PostMapping
	public Result<MaterialCategory> add(@RequestBody MaterialCategory materialCategory) {
		if (materialCategoryService.save(materialCategory)) {
			return Result.ok(materialCategory, "产品类型表-添加成功!");
		}
        return Result.fail(materialCategory, "错误:产品类型表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param materialCategory 产品类型表对象
	 * @return 返回产品类型表-编辑结果
	 */
	@Log(title = "产品类型表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品类型表-编辑")
	@PutMapping
	public Result<MaterialCategory> edit(@RequestBody MaterialCategory materialCategory) {
		if (materialCategoryService.updateById(materialCategory)) {
		    return Result.ok(materialCategory, "产品类型表-编辑成功!");
		}
		return Result.fail(materialCategory, "错误:产品类型表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品类型表-删除结果
	 */
	@Log(title = "产品类型表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品类型表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (materialCategoryService.removeById(id)) {
			return Result.ok(true, "产品类型表-删除成功!");
		}
		return Result.fail(false, "错误:产品类型表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品类型表-删除结果
	 */
	@Log(title = "产品类型表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品类型表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.materialCategoryService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品类型表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品类型表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品类型表对象
	 */
	@ApiOperation("产品类型表-通过id查询")
	@GetMapping("/{id}")
	public Result<MaterialCategory> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MaterialCategory materialCategory = materialCategoryService.getById(id);
		return Result.ok(materialCategory, "产品类型表-查询成功!");
	}
}

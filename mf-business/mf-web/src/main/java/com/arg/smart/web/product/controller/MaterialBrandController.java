package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.Material;
import com.arg.smart.web.product.entity.MaterialBrand;
import com.arg.smart.web.product.req.ReqMaterialBrand;
import com.arg.smart.web.product.service.MaterialBrandService;
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
 * @description: 产品品牌表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品品牌表")
@RestController
@RequestMapping("/materialBrand")
public class MaterialBrandController {
	@Resource
	private MaterialBrandService materialBrandService;

	/**
	 * 查询所有品牌的ID和名字
	 *
	 * @return 返回品牌ID和名字-列表
	 */
	@ApiOperation(value = "品牌-列表查询", notes = "品牌表-列表查询")
	@GetMapping("/options")
	public Result<List<MaterialBrand>> getOptions() {
		return Result.ok(materialBrandService.getOptions(), "产品品牌表-查询成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqMaterialBrand 产品品牌表请求参数
	 * @return 返回产品品牌表-分页列表
	 */
	@ApiOperation(value = "产品品牌表-分页列表查询", notes = "产品品牌表-分页列表查询")
	@GetMapping
	public Result<PageResult<MaterialBrand>> queryPageList(ReqMaterialBrand reqMaterialBrand, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(materialBrandService.list(reqMaterialBrand)), "产品品牌表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param materialBrand 产品品牌表对象
	 * @return 返回产品品牌表-添加结果
	 */
	@Log(title = "产品品牌表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品品牌表-添加")
	@PostMapping
	public Result<MaterialBrand> add(@RequestBody MaterialBrand materialBrand) {
		if (materialBrandService.save(materialBrand)) {
			return Result.ok(materialBrand, "产品品牌表-添加成功!");
		}
        return Result.fail(materialBrand, "错误:产品品牌表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param materialBrand 产品品牌表对象
	 * @return 返回产品品牌表-编辑结果
	 */
	@Log(title = "产品品牌表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品品牌表-编辑")
	@PutMapping
	public Result<MaterialBrand> edit(@RequestBody MaterialBrand materialBrand) {
		if (materialBrandService.updateById(materialBrand)) {
		    return Result.ok(materialBrand, "产品品牌表-编辑成功!");
		}
		return Result.fail(materialBrand, "错误:产品品牌表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品品牌表-删除结果
	 */
	@Log(title = "产品品牌表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品品牌表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (materialBrandService.removeById(id)) {
			return Result.ok(true, "产品品牌表-删除成功!");
		}
		return Result.fail(false, "错误:产品品牌表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品品牌表-删除结果
	 */
	@Log(title = "产品品牌表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品品牌表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.materialBrandService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品品牌表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品品牌表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品品牌表对象
	 */
	@ApiOperation("产品品牌表-通过id查询")
	@GetMapping("/{id}")
	public Result<MaterialBrand> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MaterialBrand materialBrand = materialBrandService.getById(id);
		return Result.ok(materialBrand, "产品品牌表-查询成功!");
	}
}

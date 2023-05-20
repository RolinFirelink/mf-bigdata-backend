package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.MaterialStorage;
import com.arg.smart.web.product.req.ReqMaterialStorage;
import com.arg.smart.web.product.service.MaterialStorageService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 产品库存表
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品库存表")
@RestController
@RequestMapping("/materialStorage")
public class MaterialStorageController {
	@Resource
	private MaterialStorageService materialStorageService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMaterialStorage 产品库存表请求参数
	 * @return 返回产品库存表-分页列表
	 */
	@ApiOperation(value = "产品库存表-分页列表查询", notes = "产品库存表-分页列表查询")
	@GetMapping
	public Result<PageResult<MaterialStorage>> queryPageList(ReqMaterialStorage reqMaterialStorage, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(materialStorageService.list()), "产品库存表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param materialStorage 产品库存表对象
	 * @return 返回产品库存表-添加结果
	 */
	@Log(title = "产品库存表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品库存表-添加")
	@PostMapping
	public Result<MaterialStorage> add(@RequestBody MaterialStorage materialStorage) {
		if (materialStorageService.save(materialStorage)) {
			return Result.ok(materialStorage, "产品库存表-添加成功!");
		}
        return Result.fail(materialStorage, "错误:产品库存表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param materialStorage 产品库存表对象
	 * @return 返回产品库存表-编辑结果
	 */
	@Log(title = "产品库存表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品库存表-编辑")
	@PutMapping
	public Result<MaterialStorage> edit(@RequestBody MaterialStorage materialStorage) {
		if (materialStorageService.updateById(materialStorage)) {
		    return Result.ok(materialStorage, "产品库存表-编辑成功!");
		}
		return Result.fail(materialStorage, "错误:产品库存表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品库存表-删除结果
	 */
	@Log(title = "产品库存表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品库存表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (materialStorageService.removeById(id)) {
			return Result.ok(true, "产品库存表-删除成功!");
		}
		return Result.fail(false, "错误:产品库存表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品库存表-删除结果
	 */
	@Log(title = "产品库存表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品库存表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.materialStorageService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品库存表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品库存表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品库存表对象
	 */
	@ApiOperation("产品库存表-通过id查询")
	@GetMapping("/{id}")
	public Result<MaterialStorage> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MaterialStorage materialStorage = materialStorageService.getById(id);
		return Result.ok(materialStorage, "产品库存表-查询成功!");
	}
}

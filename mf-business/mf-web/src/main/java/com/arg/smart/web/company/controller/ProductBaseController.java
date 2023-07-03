package com.arg.smart.web.company.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @description: 产品基地
 * @author lwy
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品基地")
@RestController
@RequestMapping("/productBase")
public class ProductBaseController {
	@Resource
	private ProductBaseService productBaseService;

	/**
	 * 分页列表查询
	 *
	 * @param reqProductBase 产品基地请求参数
	 * @return 返回产品基地-分页列表
	 */
	@ApiOperation(value = "产品基地-分页列表查询", notes = "产品基地-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductBase>> queryPageList(ReqProductBase reqProductBase, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productBaseService.list()), "产品基地-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productBase 产品基地对象
	 * @return 返回产品基地-添加结果
	 */
	@Log(title = "产品基地-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品基地-添加")
	@PostMapping
	public Result<ProductBase> add(@RequestBody ProductBase productBase) {
		if (productBaseService.save(productBase)) {
			return Result.ok(productBase, "产品基地-添加成功!");
		}
        return Result.fail(productBase, "错误:产品基地-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productBase 产品基地对象
	 * @return 返回产品基地-编辑结果
	 */
	@Log(title = "产品基地-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品基地-编辑")
	@PutMapping
	public Result<ProductBase> edit(@RequestBody ProductBase productBase) {
		if (productBaseService.updateById(productBase)) {
		    return Result.ok(productBase, "产品基地-编辑成功!");
		}
		return Result.fail(productBase, "错误:产品基地-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品基地-删除结果
	 */
	@Log(title = "产品基地-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品基地-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productBaseService.removeById(id)) {
			return Result.ok(true, "产品基地-删除成功!");
		}
		return Result.fail(false, "错误:产品基地-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品基地-删除结果
	 */
	@Log(title = "产品基地-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品基地-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productBaseService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品基地-批量删除成功!");
		}
		return Result.fail(false, "错误:产品基地-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品基地对象
	 */
	@ApiOperation("产品基地-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductBase> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductBase productBase = productBaseService.getById(id);
		return Result.ok(productBase, "产品基地-查询成功!");
	}
}

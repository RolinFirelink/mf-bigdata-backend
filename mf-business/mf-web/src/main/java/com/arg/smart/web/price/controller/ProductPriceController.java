package com.arg.smart.web.price.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.price.entity.ProductPrice;
import com.arg.smart.web.price.req.ReqProductPrice;
import com.arg.smart.web.price.service.ProductPriceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-12
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品价格表")
@RestController
@RequestMapping("/productPrice")
public class ProductPriceController {
	@Resource
	private ProductPriceService productPriceService;

	/**
	 * 分页列表查询
	 *
	 * @param reqProductPrice 产品价格表请求参数
	 * @return 返回产品价格表-分页列表
	 */
	@ApiOperation(value = "产品价格表-分页列表查询", notes = "产品价格表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductPrice>> queryPageList(ReqProductPrice reqProductPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(productPriceService.list(reqProductPrice), "产品价格表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productPrice 产品价格表对象
	 * @return 返回产品价格表-添加结果
	 */
	@Log(title = "产品价格表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品价格表-添加")
	@PostMapping
	public Result<ProductPrice> add(@RequestBody ProductPrice productPrice) {
		if (productPriceService.save(productPrice)) {
			return Result.ok(productPrice, "产品价格表-添加成功!");
		}
        return Result.fail(productPrice, "错误:产品价格表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productPrice 产品价格表对象
	 * @return 返回产品价格表-编辑结果
	 */
	@Log(title = "产品价格表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品价格表-编辑")
	@PutMapping
	public Result<ProductPrice> edit(@RequestBody ProductPrice productPrice) {
		if (productPriceService.updateById(productPrice)) {
		    return Result.ok(productPrice, "产品价格表-编辑成功!");
		}
		return Result.fail(productPrice, "错误:产品价格表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品价格表-删除结果
	 */
	@Log(title = "产品价格表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品价格表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productPriceService.removeById(id)) {
			return Result.ok(true, "产品价格表-删除成功!");
		}
		return Result.fail(false, "错误:产品价格表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品价格表-删除结果
	 */
	@Log(title = "产品价格表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品价格表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productPriceService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品价格表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品价格表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品价格表对象
	 */
	@ApiOperation("产品价格表-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductPrice> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductPrice productPrice = productPriceService.getById(id);
		return Result.ok(productPrice, "产品价格表-查询成功!");
	}
}

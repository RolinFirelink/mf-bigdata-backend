package com.arg.smart.web.docking.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.docking.entity.ProductPriceData;
import com.arg.smart.web.docking.req.ReqProductPriceData;
import com.arg.smart.web.docking.service.ProductPriceDataService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 惠农网产品价格
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "惠农网产品价格")
@RestController
@RequestMapping("/productPriceData")
public class ProductPriceDataController {
	@Resource
	private ProductPriceDataService productPriceDataService;

	/**
	 * 分页列表查询
	 *
	 * @param reqProductPrice 惠农网产品价格请求参数
	 * @return 返回惠农网产品价格-分页列表
	 */
	@ApiOperation(value = "惠农网产品价格-分页列表查询", notes = "惠农网产品价格-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductPriceData>> queryPageList(ReqProductPriceData reqProductPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productPriceDataService.list(reqProductPrice)), "惠农网产品价格-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productPrice 惠农网产品价格对象
	 * @return 返回惠农网产品价格-添加结果
	 */
	@Log(title = "惠农网产品价格-添加", operateType = OperateType.INSERT)
	@ApiOperation("惠农网产品价格-添加")
	@PostMapping
	public Result<ProductPriceData> add(@RequestBody ProductPriceData productPrice) {
		if (productPriceDataService.save(productPrice)) {
			return Result.ok(productPrice, "惠农网产品价格-添加成功!");
		}
        return Result.fail(productPrice, "错误:惠农网产品价格-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productPrice 惠农网产品价格对象
	 * @return 返回惠农网产品价格-编辑结果
	 */
	@Log(title = "惠农网产品价格-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("惠农网产品价格-编辑")
	@PutMapping
	public Result<ProductPriceData> edit(@RequestBody ProductPriceData productPrice) {
		if (productPriceDataService.updateById(productPrice)) {
		    return Result.ok(productPrice, "惠农网产品价格-编辑成功!");
		}
		return Result.fail(productPrice, "错误:惠农网产品价格-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回惠农网产品价格-删除结果
	 */
	@Log(title = "惠农网产品价格-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("惠农网产品价格-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productPriceDataService.removeById(id)) {
			return Result.ok(true, "惠农网产品价格-删除成功!");
		}
		return Result.fail(false, "错误:惠农网产品价格-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回惠农网产品价格-删除结果
	 */
	@Log(title = "惠农网产品价格-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("惠农网产品价格-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productPriceDataService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "惠农网产品价格-批量删除成功!");
		}
		return Result.fail(false, "错误:惠农网产品价格-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回惠农网产品价格对象
	 */
	@ApiOperation("惠农网产品价格-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductPriceData> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductPriceData productPrice = productPriceDataService.getById(id);
		return Result.ok(productPrice, "惠农网产品价格-查询成功!");
	}
}

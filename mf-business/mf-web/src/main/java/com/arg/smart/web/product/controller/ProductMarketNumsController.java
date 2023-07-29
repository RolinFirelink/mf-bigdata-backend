package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.ProductMarketNums;
import com.arg.smart.web.product.req.ReqProductMarketNums;
import com.arg.smart.web.product.service.ProductMarketNumsService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 批发市场采购量表
 * @author cgli
 * @date: 2023-07-29
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "批发市场采购量表")
@RestController
@RequestMapping("/productMarketNums")
public class ProductMarketNumsController {
	@Resource
	private ProductMarketNumsService productMarketNumsService;

	/**
	 * 添加
	 *
	 * @return 爬虫结果
	 */
	@Log(title = "从惠农网采购大厅中爬取数据", operateType = OperateType.INSERT)
	@ApiOperation("从惠农网采购大厅中爬取数据")
	@PostMapping("/purchaseCrawler")
	public Result<String> purchaseSave() {
		if (productMarketNumsService.saveByPurchase()) {
			return Result.ok("爬虫添加成功");
		}
		return Result.fail("爬虫添加失败");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProductMarketNums 批发市场采购量表请求参数
	 * @return 返回批发市场采购量表-分页列表
	 */
	@ApiOperation(value = "批发市场采购量表-分页列表查询", notes = "批发市场采购量表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductMarketNums>> queryPageList(ReqProductMarketNums reqProductMarketNums, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productMarketNumsService.list()), "批发市场采购量表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productMarketNums 批发市场采购量表对象
	 * @return 返回批发市场采购量表-添加结果
	 */
	@Log(title = "批发市场采购量表-添加", operateType = OperateType.INSERT)
	@ApiOperation("批发市场采购量表-添加")
	@PostMapping
	public Result<ProductMarketNums> add(@RequestBody ProductMarketNums productMarketNums) {
		if (productMarketNumsService.save(productMarketNums)) {
			return Result.ok(productMarketNums, "批发市场采购量表-添加成功!");
		}
        return Result.fail(productMarketNums, "错误:批发市场采购量表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productMarketNums 批发市场采购量表对象
	 * @return 返回批发市场采购量表-编辑结果
	 */
	@Log(title = "批发市场采购量表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("批发市场采购量表-编辑")
	@PutMapping
	public Result<ProductMarketNums> edit(@RequestBody ProductMarketNums productMarketNums) {
		if (productMarketNumsService.updateById(productMarketNums)) {
		    return Result.ok(productMarketNums, "批发市场采购量表-编辑成功!");
		}
		return Result.fail(productMarketNums, "错误:批发市场采购量表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回批发市场采购量表-删除结果
	 */
	@Log(title = "批发市场采购量表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("批发市场采购量表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productMarketNumsService.removeById(id)) {
			return Result.ok(true, "批发市场采购量表-删除成功!");
		}
		return Result.fail(false, "错误:批发市场采购量表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回批发市场采购量表-删除结果
	 */
	@Log(title = "批发市场采购量表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("批发市场采购量表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productMarketNumsService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "批发市场采购量表-批量删除成功!");
		}
		return Result.fail(false, "错误:批发市场采购量表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回批发市场采购量表对象
	 */
	@ApiOperation("批发市场采购量表-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductMarketNums> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductMarketNums productMarketNums = productMarketNumsService.getById(id);
		return Result.ok(productMarketNums, "批发市场采购量表-查询成功!");
	}
}

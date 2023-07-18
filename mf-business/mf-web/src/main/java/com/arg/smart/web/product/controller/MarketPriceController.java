package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.MarketPrice;
import com.arg.smart.web.product.req.ReqMarketPrice;
import com.arg.smart.web.product.service.MarketPriceService;
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
 * @description: 批发市场价格
 * @author cgli
 * @date: 2023-07-09
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "批发市场价格")
@RestController
@RequestMapping("/marketPrice")
public class MarketPriceController {
	@Resource
	private MarketPriceService marketPriceService;

	/**
	 * 大屏获取批发市场价格列比奥
	 */
	@ApiOperation(value = "大屏获取批发市场价格",notes = "大屏获取批发市场价格")
	@GetMapping("/public/list")
	public Result<List<MarketPrice>> queryList(ReqMarketPrice reqMarketPrice){
		return Result.ok(marketPriceService.list(reqMarketPrice));
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqMarketPrice 批发市场价格请求参数
	 * @return 返回批发市场价格-分页列表
	 */
	@ApiOperation(value = "批发市场价格-分页列表查询", notes = "批发市场价格-分页列表查询")
	@GetMapping
	public Result<PageResult<MarketPrice>> queryPageList(ReqMarketPrice reqMarketPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(marketPriceService.list()), "批发市场价格-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param marketPrice 批发市场价格对象
	 * @return 返回批发市场价格-添加结果
	 */
	@Log(title = "批发市场价格-添加", operateType = OperateType.INSERT)
	@ApiOperation("批发市场价格-添加")
	@PostMapping
	public Result<MarketPrice> add(@RequestBody MarketPrice marketPrice) {
		if (marketPriceService.save(marketPrice)) {
			return Result.ok(marketPrice, "批发市场价格-添加成功!");
		}
        return Result.fail(marketPrice, "错误:批发市场价格-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param marketPrice 批发市场价格对象
	 * @return 返回批发市场价格-编辑结果
	 */
	@Log(title = "批发市场价格-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("批发市场价格-编辑")
	@PutMapping
	public Result<MarketPrice> edit(@RequestBody MarketPrice marketPrice) {
		if (marketPriceService.updateById(marketPrice)) {
		    return Result.ok(marketPrice, "批发市场价格-编辑成功!");
		}
		return Result.fail(marketPrice, "错误:批发市场价格-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回批发市场价格-删除结果
	 */
	@Log(title = "批发市场价格-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("批发市场价格-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (marketPriceService.removeById(id)) {
			return Result.ok(true, "批发市场价格-删除成功!");
		}
		return Result.fail(false, "错误:批发市场价格-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回批发市场价格-删除结果
	 */
	@Log(title = "批发市场价格-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("批发市场价格-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.marketPriceService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "批发市场价格-批量删除成功!");
		}
		return Result.fail(false, "错误:批发市场价格-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回批发市场价格对象
	 */
	@ApiOperation("批发市场价格-通过id查询")
	@GetMapping("/{id}")
	public Result<MarketPrice> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MarketPrice marketPrice = marketPriceService.getById(id);
		return Result.ok(marketPrice, "批发市场价格-查询成功!");
	}
}

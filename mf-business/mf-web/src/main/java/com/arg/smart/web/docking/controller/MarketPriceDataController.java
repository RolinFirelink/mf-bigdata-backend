package com.arg.smart.web.docking.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.docking.entity.MarketPriceData;
import com.arg.smart.web.docking.req.ReqMarketPriceData;
import com.arg.smart.web.docking.service.MarketPriceDataService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 惠农网批发市场数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "惠农网批发市场数据")
@RestController
@RequestMapping("/marketPriceData")
public class MarketPriceDataController {
	@Resource
	private MarketPriceDataService marketPriceDataService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMarketPrice 惠农网批发市场数据请求参数
	 * @return 返回惠农网批发市场数据-分页列表
	 */
	@ApiOperation(value = "惠农网批发市场数据-分页列表查询", notes = "惠农网批发市场数据-分页列表查询")
	@GetMapping
	public Result<PageResult<MarketPriceData>> queryPageList(ReqMarketPriceData reqMarketPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(marketPriceDataService.list(reqMarketPrice)), "惠农网批发市场数据-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param marketPrice 惠农网批发市场数据对象
	 * @return 返回惠农网批发市场数据-添加结果
	 */
	@Log(title = "惠农网批发市场数据-添加", operateType = OperateType.INSERT)
	@ApiOperation("惠农网批发市场数据-添加")
	@PostMapping
	public Result<MarketPriceData> add(@RequestBody MarketPriceData marketPrice) {
		if (marketPriceDataService.save(marketPrice)) {
			return Result.ok(marketPrice, "惠农网批发市场数据-添加成功!");
		}
        return Result.fail(marketPrice, "错误:惠农网批发市场数据-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param marketPrice 惠农网批发市场数据对象
	 * @return 返回惠农网批发市场数据-编辑结果
	 */
	@Log(title = "惠农网批发市场数据-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("惠农网批发市场数据-编辑")
	@PutMapping
	public Result<MarketPriceData> edit(@RequestBody MarketPriceData marketPrice) {
		if (marketPriceDataService.updateById(marketPrice)) {
		    return Result.ok(marketPrice, "惠农网批发市场数据-编辑成功!");
		}
		return Result.fail(marketPrice, "错误:惠农网批发市场数据-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回惠农网批发市场数据-删除结果
	 */
	@Log(title = "惠农网批发市场数据-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("惠农网批发市场数据-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (marketPriceDataService.removeById(id)) {
			return Result.ok(true, "惠农网批发市场数据-删除成功!");
		}
		return Result.fail(false, "错误:惠农网批发市场数据-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回惠农网批发市场数据-删除结果
	 */
	@Log(title = "惠农网批发市场数据-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("惠农网批发市场数据-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.marketPriceDataService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "惠农网批发市场数据-批量删除成功!");
		}
		return Result.fail(false, "错误:惠农网批发市场数据-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回惠农网批发市场数据对象
	 */
	@ApiOperation("惠农网批发市场数据-通过id查询")
	@GetMapping("/{id}")
	public Result<MarketPriceData> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MarketPriceData marketPrice = marketPriceDataService.getById(id);
		return Result.ok(marketPrice, "惠农网批发市场数据-查询成功!");
	}
}

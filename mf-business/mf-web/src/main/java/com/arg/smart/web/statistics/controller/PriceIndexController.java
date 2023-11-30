package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.PriceIndex;
import com.arg.smart.web.statistics.req.ReqPriceIndex;
import com.arg.smart.web.statistics.service.PriceIndexService;
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
 * @description: 采购商价格指数
 * @author cgli
 * @date: 2023-08-09
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "采购商价格指数")
@RestController
@RequestMapping("/priceIndex")
public class PriceIndexController {
	@Resource
	private PriceIndexService priceIndexService;

	/**
	 * 采购商指数
	 *
	 * @param reqPriceIndex 采购商价格指数请求参数
	 * @return 返回大屏采购商价格指数图表数据
	 */
	@ApiOperation(value = "大屏采购商价格指数图表数据", notes = "大屏采购商价格指数图表数据")
	@GetMapping("/public/avg")
	public Result<List<PriceIndex>> publicAvg(ReqPriceIndex reqPriceIndex) {
		return Result.ok(priceIndexService.publicAvg(reqPriceIndex), "大屏采购商价格指数图表数据-查询成功!");
	}

	/**
	 * 采购商价格指数
	 *
	 * @param reqPriceIndex 采购商价格指数请求参数
	 * @return 返回大屏采购商价格指数图表数据
	 */
	@ApiOperation(value = "大屏采购商价格指数图表数据", notes = "大屏采购商价格指数图表数据")
	@GetMapping("/public")
	public Result<List<PriceIndex>> publicList(ReqPriceIndex reqPriceIndex) {
		return Result.ok(priceIndexService.list(reqPriceIndex), "大屏采购商价格指数图表数据-查询成功!");
	}

	/**
	 * 更新采购商指数
	 */
	@ApiOperation(value = "更新大屏采购商价格指数", notes = "更新大屏采购商价格指数")
	@GetMapping("/public/update")
	public Result<Boolean> updatePriceIndex() {
		priceIndexService.updatePriceIndex();
		return Result.ok(true,"更新大屏采购商价格指数-更新成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqPriceIndex 采购商价格指数请求参数
	 * @return 返回采购商价格指数-分页列表
	 */
	@ApiOperation(value = "采购商价格指数-分页列表查询", notes = "采购商价格指数-分页列表查询")
	@GetMapping
	public Result<PageResult<PriceIndex>> queryPageList(ReqPriceIndex reqPriceIndex, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(priceIndexService.listPage(reqPriceIndex), "采购商价格指数-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param priceIndex 采购商价格指数对象
	 * @return 返回采购商价格指数-添加结果
	 */
	@Log(title = "采购商价格指数-添加", operateType = OperateType.INSERT)
	@ApiOperation("采购商价格指数-添加")
	@PostMapping
	public Result<PriceIndex> add(@RequestBody PriceIndex priceIndex) {
		if (priceIndexService.save(priceIndex)) {
			return Result.ok(priceIndex, "采购商价格指数-添加成功!");
		}
        return Result.fail(priceIndex, "错误:采购商价格指数-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param priceIndex 采购商价格指数对象
	 * @return 返回采购商价格指数-编辑结果
	 */
	@Log(title = "采购商价格指数-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("采购商价格指数-编辑")
	@PutMapping
	public Result<PriceIndex> edit(@RequestBody PriceIndex priceIndex) {
		if (priceIndexService.updateById(priceIndex)) {
		    return Result.ok(priceIndex, "采购商价格指数-编辑成功!");
		}
		return Result.fail(priceIndex, "错误:采购商价格指数-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回采购商价格指数-删除结果
	 */
	@Log(title = "采购商价格指数-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("采购商价格指数-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (priceIndexService.removeById(id)) {
			return Result.ok(true, "采购商价格指数-删除成功!");
		}
		return Result.fail(false, "错误:采购商价格指数-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回采购商价格指数-删除结果
	 */
	@Log(title = "采购商价格指数-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("采购商价格指数-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.priceIndexService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "采购商价格指数-批量删除成功!");
		}
		return Result.fail(false, "错误:采购商价格指数-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回采购商价格指数对象
	 */
	@ApiOperation("采购商价格指数-通过id查询")
	@GetMapping("/{id}")
	public Result<PriceIndex> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		PriceIndex priceIndex = priceIndexService.getById(id);
		return Result.ok(priceIndex, "采购商价格指数-查询成功!");
	}
}

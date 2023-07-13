package com.arg.smart.web.order.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderService;
import com.arg.smart.web.order.vo.DurationQueryParam;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author cgli
 * @description: 订单数据主表
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "订单数据主表")
@RestController
@RequestMapping("/order")
public class OrderController {
	@Resource
	private OrderService orderService;

	/**
	 * 分页列表查询
	 *
	 * @param reqOrder 订单数据主表请求参数
	 * @return 返回订单数据主表-分页列表
	 */
	@ApiOperation(value = "订单数据主表-分页列表查询", notes = "订单数据主表-分页列表查询")
	@GetMapping
	public Result<PageResult<Order>> queryPageList(ReqOrder reqOrder, ReqPage reqPage) {
		PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
		return Result.ok(orderService.list(reqOrder), "订单数据主表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param order 订单数据主表对象
	 * @return 返回订单数据主表-添加结果
	 */
	@Log(title = "订单数据主表-添加", operateType = OperateType.INSERT)
	@ApiOperation("订单数据主表-添加")
	@PostMapping
	public Result<Order> add(@RequestBody Order order) {
		if (orderService.save(order)) {
			return Result.ok(order, "订单数据主表-添加成功!");
		}
		return Result.fail(order, "错误:订单数据主表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param order 订单数据主表对象
	 * @return 返回订单数据主表-编辑结果
	 */
	@Log(title = "订单数据主表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("订单数据主表-编辑")
	@PutMapping
	public Result<Order> edit(@RequestBody Order order) {
		if (orderService.updateById(order)) {
			return Result.ok(order, "订单数据主表-编辑成功!");
		}
		return Result.fail(order, "错误:订单数据主表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回订单数据主表-删除结果
	 */
	@Log(title = "订单数据主表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("订单数据主表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (orderService.removeById(id)) {
			return Result.ok(true, "订单数据主表-删除成功!");
		}
		return Result.fail(false, "错误:订单数据主表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回订单数据主表-删除结果
	 */
	@Log(title = "订单数据主表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("订单数据主表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.orderService.removeByIds(Arrays.asList(ids.split(",")))) {
			return Result.ok(true, "订单数据主表-批量删除成功!");
		}
		return Result.fail(false, "错误:订单数据主表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回订单数据主表对象
	 */
	@Log(title = "订单主表-通过id查询", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-通过id查询")
	@GetMapping("/{id}")
	public Result<Order> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Order order = orderService.getById(id);
		return Result.ok(order, "订单数据主表-查询成功!");
	}

	/**
	 * 统计某时间段内订单产生的数量
	 *
	 * @param flag               模块类型
	 * @param durationQueryParam 时间段查询值对象
	 * @param category           订单类型
	 * @return Long
	 */
	@Log(title = "订单主表-统计某时间段内订单产生的数量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计某时间段内订单产生的数量")
	@GetMapping("/getOrderCountByTime")
	public Result<Long> getOrderCountByTime(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam) {
		return Result.ok(orderService.getOrderCountByTime(flag, category, durationQueryParam), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同运输方式的订单数量
	 *
	 * @param flag               模块类型
	 * @param category           订单类型
	 * @param durationQueryParam 时间段查询值对象
	 * @return List<Map < String, Object>>
	 */
	@Log(title = "订单主表-统计不同运输方式的订单数量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同运输方式的订单数量")
	@GetMapping("/getOrderCountByTransportMode")
	public Result<List<Map<String, Object>>> getOrderCountByTransportMode(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam) {
		return Result.ok(orderService.getOrderCountByTransportMode(flag, category, durationQueryParam), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同承运商运货量
	 *
	 * @param flag               模块类型
	 * @param category           订单类型
	 * @param durationQueryParam 时间段查询值对象
	 * @return Map<String, Object>
	 */
	@Log(title = "订单主表-统计不同承运商的运货量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同承运商的运货量")
	@GetMapping("/getOrderTransportationAmount")
	public Result<List<Map<String, Object>>> getOrderTransportationAmount(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam) {
		return Result.ok(orderService.getOrderTransportationAmount(flag, category, durationQueryParam), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同地区下单数量
	 *
	 * @param flag               模块类型
	 * @param category           订单类型
	 * @param durationQueryParam 时间段查询值对象
	 * @return List<Map < String, Object>>
	 */
	@Log(title = "订单主表-统计不同地区下单数量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同地区下单数量")
	@GetMapping("/getOrderAmountByArea")
	public Result<List<Map<String, Object>>> getOrderAmountByArea(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam) {
		return Result.ok(orderService.getOrderAmountByArea(flag, category, durationQueryParam), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同地区某产品平均生产/销售/采购价格
	 *
	 * @param param    时间段查询值对象
	 * @param category 订单类型
	 * @param goodId   需要查询的产品 ID
	 * @return List<Map < String, Object>>
	 */
	@Log(title = "订单主表-统计不同地区某产品平均价格", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同地区某产品平均价格")
	@GetMapping("/getProductAvgPriceByArea")
	public Result<List<Map<String, Object>>> getProductAvgPriceByArea(
			@ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "goodId", value = "需要查询的产品 ID") Long goodId
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam param) {
		return Result.ok(orderService.getProductAvgPriceByArea(category, goodId, param), "订单数据主表-查询成功!");
	}

	/**
	 * 统计承运商基本信息（不同承运商不同运输方式占比）
	 *
	 * @param category 订单类型
	 * @param param    时间段查询值对象
	 * @return Map<String, Map < String, Object>>
	 */
	@Log(title = "订单主表-统计承运商基本信息（不同承运商不同运输方式占比）", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计承运商基本信息")
	@GetMapping("/getCompanyCirculationInfo")
	public Result<Map<String, Map<String, Object>>> getCompanyCirculationInfo(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam param) {
		return Result.ok(orderService.getCompanyCirculationInfo(flag, category, param), "订单数据主表-查询成功!");
	}

	/**
	 * 根据订单类型查询订单详情
	 *
	 * @param flag     模块类型
	 * @param category 订单类型
	 * @param param    时间段查询值对象
	 * @return List<Order>
	 */
	@Log(title = "订单主表-根据订单类型查询订单详情", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-根据订单类型查询订单详情")
	@GetMapping("/getOrderByCategory")
	public Result<List<Order>> getOrderByCategory(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam param) {
		return Result.ok(orderService.getOrderByCategory(flag, category, param), "订单数据主表-查询成功!");
	}

	/**
	 * 统计订单信息列表
	 *
	 * @param flag  模块编号
	 * @param param 时间段查询值对象
	 * @return List<Map < String, Object>>
	 */
	@Log(title = "订单主表-统计订单信息列表（订单单号、运输方式、承运商、发货时间等信息）", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计订单信息列表")
	@GetMapping("/getOrderInfo")
	public Result<List<Map<String, Object>>> getOrderInfo(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam param) {
		return Result.ok(orderService.getOrderInfo(flag, param), "订单数据主表-查询成功!");
	}

	@Log(title = "订单主表-统计不同模块销售指数", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同模块销售指数")
	@GetMapping("/getSalesPending")
	public Result<List<Map<String, Object>>> getSalesPending(
			@ApiParam(name = "date", value = "查询日期") @DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
		return Result.ok(orderService.getSalesPending(date), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同品种产品预计上市产量
	 *
	 * @param flag       模块编号
	 * @param materialId 产品编号
	 * @return List<Integer>
	 */
	@Log(title = "订单主表-统计不同品种产品预计上市产量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同品种产品预计上市产量")
	@GetMapping("/getMarketEstimates")
	public Result<List<Integer>> getMarketEstimates(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "materialId", value = "产品编号") Long materialId) {
		return Result.ok(orderService.getMarketEstimatesByFlagAndMaterialId(flag, materialId), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同品种产品不同批次产量
	 *
	 * @param flag       模块编号
	 * @param materialId 产品编号
	 * @param batch      生产批次
	 * @return List<BigDecimal>
	 */
	@Log(title = "订单主表-统计不同品种产品不同批次产量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同品种产品不同批次产量")
	@GetMapping("/getBatchProduction")
	public Result<List<BigDecimal>> getBatchProduction(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "materialId", value = "产品编号") Long materialId
			, @ApiParam(name = "batch", value = "生产批次") Integer batch) {
		return Result.ok(orderService.getBatchProductionByFlagAndMaterialId(flag, materialId, batch), "订单数据主表-查询成功!");
	}

	/**
	 * 统计月生产订单数量
	 *
	 * @param flag               模块编号
	 * @param durationQueryParam 时间段查询值对象
	 * @param category           订单类型
	 * @return Long
	 */
	@Log(title = "订单主表-统计月生产订单数量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计月生产订单数量")
	@GetMapping("/getOrderCount")
	public Result<Long> getOrderCount(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam
			, @ApiParam(name = "category", value = "订单类型") Integer category) {
		return Result.ok(orderService.getOrderCountByFlagAndTimeAndCategory(flag, durationQueryParam, category), "订单数据主表-查询成功!");
	}

	/**
	 * 查询特定产品月生产订单详细信息
	 *
	 * @param flag               模块编号
	 * @param durationQueryParam 时间段查询值对象
	 * @param materialId         产品编号
	 * @return List<MaterialProduce>
	 */
	@Log(title = "订单主表-查询特定产品月生产订单详细信息", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-查询特定产品月生产订单详细信息")
	@GetMapping("/getOrderDetails")
	public Result<List<MaterialProduce>> getOrderDetails(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam
			, @ApiParam(name = "materialId", value = "产品编号") Long materialId) {
		return Result.ok(orderService.getOrderDetailsByFlagAndTimeAndMaterialId(flag, durationQueryParam, materialId), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同产品生产总额
	 *
	 * @param flag               模块编号
	 * @param durationQueryParam 时间段查询值对象
	 * @param materialId         产品编号
	 * @return List<BigDecimal>
	 */
	@Log(title = "订单主表-统计不同产品生产总额", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同产品生产总额")
	@GetMapping("/getProductionTotal")
	public Result<List<BigDecimal>> getProductionTotal(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam
			, @ApiParam(name = "materialId", value = "产品编号") Long materialId) {
		return Result.ok(orderService.getProductionTotalByFlagAndTimeAndMaterialId(flag, durationQueryParam, materialId), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同产品月出库量
	 *
	 * @param flag               模块编号
	 * @param durationQueryParam 时间段查询值对象
	 * @param materialId         产品编号
	 * @return List<Long>
	 */
	@Log(title = "订单主表-统计不同产品月出库量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同产品月出库量")
	@GetMapping("/getInventoryQuantity")
	public Result<List<Long>> getInventoryQuantity(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam
			, @ApiParam(name = "materialId", value = "产品编号") Long materialId) {
		return Result.ok(orderService.getInventoryQuantityByFlagAndTimeAndMaterialId(flag, durationQueryParam, materialId), "订单数据主表-查询成功!");
	}

	/**
	 * 查询月出库订单明细
	 *
	 * @param flag               模块编号
	 * @param durationQueryParam 时间段查询值对象
	 * @param materialId         产品编号
	 * @return List<OrderDetail>
	 */
	@Log(title = "订单主表-查询月出库订单明细", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-查询月出库订单明细")
	@GetMapping("/getMonthlyOrderDetails")
	public Result<List<OrderDetail>> getMonthlyOrderDetails(
			@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam
			, @ApiParam(name = "materialId", value = "产品编号") Long materialId) {
		return Result.ok(orderService.getMonthlyOrderDetailsByFlagAndTimeAndMaterialId(flag, durationQueryParam, materialId), "订单数据主表-查询成功!");
	}

	/**
	 * 统计月订单数量
	 *
	 * @param flag
	 * @param time
	 * @return
	 */
	@ApiOperation("订单数据主表-统计月订单数量")
	@GetMapping("/monthlyOrderQuantity/{flag}/{time}")
	public Result<Long> monthlyOrderQuantity(@ApiParam(name = "flag", value = "模块编号") Integer flag,
	                                         @ApiParam(name = "time", value = "完成时间") String time) {
		return Result.ok(orderService.CountTheMonthlyOrder(flag, time), "月订单数量查询完成");
	}
}

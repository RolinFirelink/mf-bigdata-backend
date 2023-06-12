package com.arg.smart.web.order.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.vo.OrderExcel;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderService;
import com.arg.smart.web.order.uitls.OrderDataListener;
import com.arg.smart.web.order.vo.DurationQueryParam;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @description: 订单数据主表
 * @author cgli
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
	 * 订单数据主表-Excel导入
	 *
	 * @param file 订单主表Excel数据
	 */
	@ApiOperation(value = "订单数据主表-Excel导入",notes = "订单数据主表-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), OrderExcel.class, new OrderDataListener(orderService)).sheet().doRead();
		return Result.ok(true,"上传数据成功");
	}



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
	public Result<Long> getOrderCountByTime(@ApiParam(name = "flag", value = "模块编号") Integer flag
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
	 * @return Map<String, Object>
	 */
	@Log(title = "订单主表-统计不同运输方式的订单数量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同运输方式的订单数量")
	@GetMapping("/getOrderCountByTransportMode")
	public Result<Map<String, Object>> getOrderCountByTransportMode(@ApiParam(name = "flag", value = "模块编号") Integer flag
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
	public Result<Map<String, Object>> getOrderTransportationAmount(@ApiParam(name = "flag", value = "模块编号") Integer flag
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
	 * @return Map<String, Object>
	 */
	@Log(title = "订单主表-统计不同地区下单数量", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同地区下单数量")
	@GetMapping("/getOrderAmountByArea")
	public Result<Map<String, Object>> getOrderAmountByArea(@ApiParam(name = "flag", value = "模块编号") Integer flag
			, @ApiParam(name = "category", value = "订单类型") Integer category
			, @ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam) {
		return Result.ok(orderService.getOrderAmountByArea(flag, category, durationQueryParam), "订单数据主表-查询成功!");
	}

	/**
	 * 统计不同地域某产品平均销售价格
	 *
	 * @param durationQueryParam 时间段查询值对象
	 * @param category           订单类型
	 * @param goodId             需要查询的产品 ID
	 * @return Map<String, Object>
	 */
	@Log(title = "订单主表-统计不同地区某产品平均价格", operateType = OperateType.QUERY)
	@ApiOperation("订单数据主表-统计不同地区某产品平均价格")
	@GetMapping("/getProductAvgPriceByArea")
	public Result<Map<String, Object>> getProductAvgPriceByArea(
			@ApiParam(name = "durationQueryParam", value = "时间段查询值对象") DurationQueryParam durationQueryParam
			, @ApiParam(name = "category", value = "订单类型") Integer category, Long goodId) {
		return Result.ok(orderService.getProductAvgPriceByArea(durationQueryParam, category, goodId), "订单数据主表-查询成功!");
	}

}

package com.arg.smart.web.order.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.entity.vo.OrderDetailExcel;
import com.arg.smart.web.order.entity.vo.OrderExcel;
import com.arg.smart.web.order.req.ReqOrderDetail;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.uitls.OrderDataListener;
import com.arg.smart.web.order.uitls.OrderDetailDataListener;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 订单数据明细表
 * @author cgli
 * @date: 2023-05-22
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "订单数据明细表")
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
	@Resource
	private OrderDetailService orderDetailService;

	/**
	 * 订单详情表-Excel导入
	 *
	 * @param file 订单主表Excel数据
	 */
	@ApiOperation(value = "订单详情表-Excel导入",notes = "订单详情表f-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), OrderDetailExcel.class, new OrderDetailDataListener(orderDetailService)).sheet().doRead();
		return Result.ok(true,"上传数据成功");
	}

	/**
	 * 根据订单获取产品列表
	 *
	 * @param orderId 订单Id
	 * @return 返回产品列表数据
	 */
	@ApiOperation(value = "订单数据明细表-分页列表查询", notes = "订单数据明细表-分页列表查询")
	@GetMapping("/listByOrderId/{orderId}")
	public Result<List<OrderDetail>> list(@PathVariable("orderId")Long orderId ) {
		return Result.ok(orderDetailService.list(orderId), "订单数据明细表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param orderDetail 订单数据明细表对象
	 * @return 返回订单数据明细表-添加结果
	 */
	@Log(title = "订单数据明细表-添加", operateType = OperateType.INSERT)
	@ApiOperation("订单数据明细表-添加")
	@PostMapping
	public Result<OrderDetail> add(@RequestBody OrderDetail orderDetail) {
		if (orderDetailService.save(orderDetail)) {
			return Result.ok(orderDetail, "订单数据明细表-添加成功!");
		}
        return Result.fail(orderDetail, "错误:订单数据明细表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param orderDetail 订单数据明细表对象
	 * @return 返回订单数据明细表-编辑结果
	 */
	@Log(title = "订单数据明细表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("订单数据明细表-编辑")
	@PutMapping
	public Result<OrderDetail> edit(@RequestBody OrderDetail orderDetail) {
		if (orderDetailService.updateById(orderDetail)) {
		    return Result.ok(orderDetail, "订单数据明细表-编辑成功!");
		}
		return Result.fail(orderDetail, "错误:订单数据明细表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回订单数据明细表-删除结果
	 */
	@Log(title = "订单数据明细表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("订单数据明细表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (orderDetailService.removeById(id)) {
			return Result.ok(true, "订单数据明细表-删除成功!");
		}
		return Result.fail(false, "错误:订单数据明细表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回订单数据明细表-删除结果
	 */
	@Log(title = "订单数据明细表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("订单数据明细表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.orderDetailService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "订单数据明细表-批量删除成功!");
		}
		return Result.fail(false, "错误:订单数据明细表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回订单数据明细表对象
	 */
	@ApiOperation("订单数据明细表-通过id查询")
	@GetMapping("/{id}")
	public Result<OrderDetail> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		OrderDetail orderDetail = orderDetailService.getById(id);
		return Result.ok(orderDetail, "订单数据明细表-查询成功!");
	}
}


package com.arg.smart.web.order.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

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
	 * 分页列表查询
	 *
	 * @param reqOrder 订单数据主表请求参数
	 * @return 返回订单数据主表-分页列表
	 */
	@ApiOperation(value = "订单数据主表-分页列表查询", notes = "订单数据主表-分页列表查询")
	@GetMapping
	public Result<PageResult<Order>> queryPageList(ReqOrder reqOrder, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(orderService.list(reqOrder)), "订单数据主表-查询成功!");
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
	@ApiOperation("订单数据主表-通过id查询")
	@GetMapping("/{id}")
	public Result<Order> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Order order = orderService.getById(id);
		return Result.ok(order, "订单数据主表-查询成功!");
	}
}

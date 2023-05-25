package com.arg.smart.web.customer.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.req.ReqCustomer;
import com.arg.smart.web.customer.service.CustomerService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "客户表")
@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Resource
	private CustomerService customerService;

	/**
	 * 分页列表查询
	 *
	 * @param reqCustomer 客户表请求参数
	 * @return 返回客户表-分页列表
	 */
	@ApiOperation(value = "客户表-分页列表查询", notes = "客户表-分页列表查询")
	@GetMapping
	public Result<PageResult<Customer>> queryPageList(ReqCustomer reqCustomer, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(customerService.list()), "客户表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param customer 客户表对象
	 * @return 返回客户表-添加结果
	 */
	@Log(title = "客户表-添加", operateType = OperateType.INSERT)
	@ApiOperation("客户表-添加")
	@PostMapping
	public Result<Customer> add(@RequestBody Customer customer) {
		if (customerService.save(customer)) {
			return Result.ok(customer, "客户表-添加成功!");
		}
        return Result.fail(customer, "错误:客户表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param customer 客户表对象
	 * @return 返回客户表-编辑结果
	 */
	@Log(title = "客户表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("客户表-编辑")
	@PutMapping
	public Result<Customer> edit(@RequestBody Customer customer) {
		if (customerService.updateById(customer)) {
		    return Result.ok(customer, "客户表-编辑成功!");
		}
		return Result.fail(customer, "错误:客户表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回客户表-删除结果
	 */
	@Log(title = "客户表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("客户表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (customerService.removeById(id)) {
			return Result.ok(true, "客户表-删除成功!");
		}
		return Result.fail(false, "错误:客户表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回客户表-删除结果
	 */
	@Log(title = "客户表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("客户表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.customerService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "客户表-批量删除成功!");
		}
		return Result.fail(false, "错误:客户表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回客户表对象
	 */
	@ApiOperation("客户表-通过id查询")
	@GetMapping("/{id}")
	public Result<Customer> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Customer customer = customerService.getById(id);
		return Result.ok(customer, "客户表-查询成功!");
	}
}

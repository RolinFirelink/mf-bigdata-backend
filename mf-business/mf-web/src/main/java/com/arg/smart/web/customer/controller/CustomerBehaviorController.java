package com.arg.smart.web.customer.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.customer.entity.CustomerBehavior;
import com.arg.smart.web.customer.req.ReqCustomerBehavior;
import com.arg.smart.web.customer.service.CustomerBehaviorService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 客户消费行为表
 * @author cgli
 * @date: 2023-05-17
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "客户消费行为表")
@RestController
@RequestMapping("/customerBehavior")
public class CustomerBehaviorController {
	@Resource
	private CustomerBehaviorService customerBehaviorService;

	/**
	 * 分页列表查询
	 *
	 * @param reqCustomerBehavior 客户消费行为表请求参数
	 * @return 返回客户消费行为表-分页列表
	 */
	@ApiOperation(value = "客户消费行为表-分页列表查询", notes = "客户消费行为表-分页列表查询")
	@GetMapping
	public Result<PageResult<CustomerBehavior>> queryPageList(ReqCustomerBehavior reqCustomerBehavior, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(customerBehaviorService.list()), "客户消费行为表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param customerBehavior 客户消费行为表对象
	 * @return 返回客户消费行为表-添加结果
	 */
	@Log(title = "客户消费行为表-添加", operateType = OperateType.INSERT)
	@ApiOperation("客户消费行为表-添加")
	@PostMapping
	public Result<CustomerBehavior> add(@RequestBody CustomerBehavior customerBehavior) {
		if (customerBehaviorService.save(customerBehavior)) {
			return Result.ok(customerBehavior, "客户消费行为表-添加成功!");
		}
        return Result.fail(customerBehavior, "错误:客户消费行为表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param customerBehavior 客户消费行为表对象
	 * @return 返回客户消费行为表-编辑结果
	 */
	@Log(title = "客户消费行为表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("客户消费行为表-编辑")
	@PutMapping
	public Result<CustomerBehavior> edit(@RequestBody CustomerBehavior customerBehavior) {
		if (customerBehaviorService.updateById(customerBehavior)) {
		    return Result.ok(customerBehavior, "客户消费行为表-编辑成功!");
		}
		return Result.fail(customerBehavior, "错误:客户消费行为表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回客户消费行为表-删除结果
	 */
	@Log(title = "客户消费行为表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("客户消费行为表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (customerBehaviorService.removeById(id)) {
			return Result.ok(true, "客户消费行为表-删除成功!");
		}
		return Result.fail(false, "错误:客户消费行为表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回客户消费行为表-删除结果
	 */
	@Log(title = "客户消费行为表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("客户消费行为表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.customerBehaviorService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "客户消费行为表-批量删除成功!");
		}
		return Result.fail(false, "错误:客户消费行为表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回客户消费行为表对象
	 */
	@ApiOperation("客户消费行为表-通过id查询")
	@GetMapping("/{id}")
	public Result<CustomerBehavior> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		CustomerBehavior customerBehavior = customerBehaviorService.getById(id);
		return Result.ok(customerBehavior, "客户消费行为表-查询成功!");
	}
}

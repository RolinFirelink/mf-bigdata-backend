package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.data.req.ReqSalesFlow;
import com.arg.smart.web.data.service.SalesFlowService;
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
 * @description: 销售流向
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "销售流向")
@RestController
@RequestMapping("/salesFlow")
public class SalesFlowController {
	@Resource
	private SalesFlowService salesFlowService;

	/**
	 * 大屏——销售流向
	 *
	 * @param reqSalesFlow 销售流向请求参数
	 * @return 返回大屏——销售流向
	 */
	@ApiOperation(value = "大屏——销售流向", notes = "大屏——销售流向")
	@GetMapping("/public")
	public Result<List<SalesFlow>> publicList(ReqSalesFlow reqSalesFlow) {
		return Result.ok(salesFlowService.list(reqSalesFlow), "销售流向-查询成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqSalesFlow 销售流向请求参数
	 * @return 返回销售流向-分页列表
	 */
	@ApiOperation(value = "销售流向-分页列表查询", notes = "销售流向-分页列表查询")
	@GetMapping
	public Result<PageResult<SalesFlow>> queryPageList(ReqSalesFlow reqSalesFlow, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(salesFlowService.pageList(reqSalesFlow), "销售流向-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param salesFlow 销售流向对象
	 * @return 返回销售流向-添加结果
	 */
	@Log(title = "销售流向-添加", operateType = OperateType.INSERT)
	@ApiOperation("销售流向-添加")
	@PostMapping
	public Result<SalesFlow> add(@RequestBody SalesFlow salesFlow) {
		if (salesFlowService.save(salesFlow)) {
			return Result.ok(salesFlow, "销售流向-添加成功!");
		}
        return Result.fail(salesFlow, "错误:销售流向-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param salesFlow 销售流向对象
	 * @return 返回销售流向-编辑结果
	 */
	@Log(title = "销售流向-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("销售流向-编辑")
	@PutMapping
	public Result<SalesFlow> edit(@RequestBody SalesFlow salesFlow) {
		if (salesFlowService.updateById(salesFlow)) {
		    return Result.ok(salesFlow, "销售流向-编辑成功!");
		}
		return Result.fail(salesFlow, "错误:销售流向-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回销售流向-删除结果
	 */
	@Log(title = "销售流向-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("销售流向-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (salesFlowService.removeById(id)) {
			return Result.ok(true, "销售流向-删除成功!");
		}
		return Result.fail(false, "错误:销售流向-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回销售流向-删除结果
	 */
	@Log(title = "销售流向-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("销售流向-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.salesFlowService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "销售流向-批量删除成功!");
		}
		return Result.fail(false, "错误:销售流向-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回销售流向对象
	 */
	@ApiOperation("销售流向-通过id查询")
	@GetMapping("/{id}")
	public Result<SalesFlow> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		SalesFlow salesFlow = salesFlowService.getById(id);
		return Result.ok(salesFlow, "销售流向-查询成功!");
	}
}

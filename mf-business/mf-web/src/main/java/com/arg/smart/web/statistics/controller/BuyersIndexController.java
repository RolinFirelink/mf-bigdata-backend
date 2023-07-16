package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.BuyersIndex;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.service.BuyersIndexService;
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
 * @description: 采购商指数
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "采购商指数")
@RestController
@RequestMapping("/buyersIndex")
public class BuyersIndexController {
	@Resource
	private BuyersIndexService buyersIndexService;

	/**
	 * 采购商指数
	 *
	 * @param reqBuyersIndex 采购商指数请求参数
	 * @return 返回大屏采购商指数图表数据
	 */
	@ApiOperation(value = "采购商指数-分页列表查询", notes = "采购商指数-分页列表查询")
	@GetMapping("/public")
	public Result<List<BuyersIndex>> publicList(ReqBuyersIndex reqBuyersIndex, ReqPage reqPage) {
		return Result.ok(buyersIndexService.list(reqBuyersIndex), "采购商指数-查询成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqBuyersIndex 采购商指数请求参数
	 * @return 返回采购商指数-分页列表
	 */
	@ApiOperation(value = "采购商指数-分页列表查询", notes = "采购商指数-分页列表查询")
	@GetMapping
	public Result<PageResult<BuyersIndex>> queryPageList(ReqBuyersIndex reqBuyersIndex, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(buyersIndexService.list()), "采购商指数-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param buyersIndex 采购商指数对象
	 * @return 返回采购商指数-添加结果
	 */
	@Log(title = "采购商指数-添加", operateType = OperateType.INSERT)
	@ApiOperation("采购商指数-添加")
	@PostMapping
	public Result<BuyersIndex> add(@RequestBody BuyersIndex buyersIndex) {
		if (buyersIndexService.save(buyersIndex)) {
			return Result.ok(buyersIndex, "采购商指数-添加成功!");
		}
        return Result.fail(buyersIndex, "错误:采购商指数-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param buyersIndex 采购商指数对象
	 * @return 返回采购商指数-编辑结果
	 */
	@Log(title = "采购商指数-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("采购商指数-编辑")
	@PutMapping
	public Result<BuyersIndex> edit(@RequestBody BuyersIndex buyersIndex) {
		if (buyersIndexService.updateById(buyersIndex)) {
		    return Result.ok(buyersIndex, "采购商指数-编辑成功!");
		}
		return Result.fail(buyersIndex, "错误:采购商指数-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回采购商指数-删除结果
	 */
	@Log(title = "采购商指数-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("采购商指数-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (buyersIndexService.removeById(id)) {
			return Result.ok(true, "采购商指数-删除成功!");
		}
		return Result.fail(false, "错误:采购商指数-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回采购商指数-删除结果
	 */
	@Log(title = "采购商指数-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("采购商指数-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.buyersIndexService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "采购商指数-批量删除成功!");
		}
		return Result.fail(false, "错误:采购商指数-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回采购商指数对象
	 */
	@ApiOperation("采购商指数-通过id查询")
	@GetMapping("/{id}")
	public Result<BuyersIndex> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		BuyersIndex buyersIndex = buyersIndexService.getById(id);
		return Result.ok(buyersIndex, "采购商指数-查询成功!");
	}
}

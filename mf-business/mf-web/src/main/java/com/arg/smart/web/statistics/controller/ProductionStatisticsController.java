package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.ProductionStatistics;
import com.arg.smart.web.product.req.ReqProductionStatistics;
import com.arg.smart.web.statistics.service.ProductionStatisticsService;
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
 * @description: 生产统计
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "生产统计")
@RestController
@RequestMapping("/productionStatistics")
public class ProductionStatisticsController {
	@Resource
	private ProductionStatisticsService productionStatisticsService;

	/**
	 * 大屏——生产宏观数据
	 *
	 * @param reqProductionStatistics 生产统计查询参数
	 * @return 返回大屏——生产宏观数据
	 */
	@ApiOperation(value = "生产统计-分页列表查询", notes = "生产统计-分页列表查询")
	@GetMapping("/public")
	public Result<List<ProductionStatistics>> publicList(ReqProductionStatistics reqProductionStatistics, ReqPage reqPage) {
		return Result.ok(productionStatisticsService.list(reqProductionStatistics), "生产统计-查询成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProductionStatistics 生产统计请求参数
	 * @return 返回生产统计-分页列表
	 */
	@ApiOperation(value = "生产统计-分页列表查询", notes = "生产统计-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductionStatistics>> queryPageList(ReqProductionStatistics reqProductionStatistics, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productionStatisticsService.list()), "生产统计-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productionStatistics 生产统计对象
	 * @return 返回生产统计-添加结果
	 */
	@Log(title = "生产统计-添加", operateType = OperateType.INSERT)
	@ApiOperation("生产统计-添加")
	@PostMapping
	public Result<ProductionStatistics> add(@RequestBody ProductionStatistics productionStatistics) {
		if (productionStatisticsService.save(productionStatistics)) {
			return Result.ok(productionStatistics, "生产统计-添加成功!");
		}
        return Result.fail(productionStatistics, "错误:生产统计-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productionStatistics 生产统计对象
	 * @return 返回生产统计-编辑结果
	 */
	@Log(title = "生产统计-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("生产统计-编辑")
	@PutMapping
	public Result<ProductionStatistics> edit(@RequestBody ProductionStatistics productionStatistics) {
		if (productionStatisticsService.updateById(productionStatistics)) {
		    return Result.ok(productionStatistics, "生产统计-编辑成功!");
		}
		return Result.fail(productionStatistics, "错误:生产统计-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回生产统计-删除结果
	 */
	@Log(title = "生产统计-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("生产统计-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productionStatisticsService.removeById(id)) {
			return Result.ok(true, "生产统计-删除成功!");
		}
		return Result.fail(false, "错误:生产统计-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回生产统计-删除结果
	 */
	@Log(title = "生产统计-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("生产统计-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productionStatisticsService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "生产统计-批量删除成功!");
		}
		return Result.fail(false, "错误:生产统计-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回生产统计对象
	 */
	@ApiOperation("生产统计-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductionStatistics> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductionStatistics productionStatistics = productionStatisticsService.getById(id);
		return Result.ok(productionStatistics, "生产统计-查询成功!");
	}
}

package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.ProvinceSaleStatistics;
import com.arg.smart.web.product.req.ReqProvinceSaleStatistics;
import com.arg.smart.web.statistics.service.ProvinceSaleStatisticsService;
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
 * @description: 省份销售数据
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "省份销售数据")
@RestController
@RequestMapping("/provinceSaleStatistics")
public class ProvinceSaleStatisticsController {
	@Resource
	private ProvinceSaleStatisticsService provinceSaleStatisticsService;

	/**
	 *大屏获取省份销售数据
	 *
	 * @param reqProvinceSaleStatistics 省份销售数据请求参数
	 * @return 返回大屏省份销售数据
	 */
	@ApiOperation(value = "大屏获取省份销售数据")
	@GetMapping("/public")
	public Result<List<ProvinceSaleStatistics>> publicList(ReqProvinceSaleStatistics reqProvinceSaleStatistics){
		return Result.ok(provinceSaleStatisticsService.list(reqProvinceSaleStatistics));
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProvinceSaleStatistics 省份销售数据请求参数
	 * @return 返回省份销售数据-分页列表
	 */
	@ApiOperation(value = "省份销售数据-分页列表查询", notes = "省份销售数据-分页列表查询")
	@GetMapping
	public Result<PageResult<ProvinceSaleStatistics>> queryPageList(ReqProvinceSaleStatistics reqProvinceSaleStatistics, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(provinceSaleStatisticsService.list()), "省份销售数据-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param provinceSaleStatistics 省份销售数据对象
	 * @return 返回省份销售数据-添加结果
	 */
	@Log(title = "省份销售数据-添加", operateType = OperateType.INSERT)
	@ApiOperation("省份销售数据-添加")
	@PostMapping
	public Result<ProvinceSaleStatistics> add(@RequestBody ProvinceSaleStatistics provinceSaleStatistics) {
		if (provinceSaleStatisticsService.save(provinceSaleStatistics)) {
			return Result.ok(provinceSaleStatistics, "省份销售数据-添加成功!");
		}
        return Result.fail(provinceSaleStatistics, "错误:省份销售数据-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param provinceSaleStatistics 省份销售数据对象
	 * @return 返回省份销售数据-编辑结果
	 */
	@Log(title = "省份销售数据-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("省份销售数据-编辑")
	@PutMapping
	public Result<ProvinceSaleStatistics> edit(@RequestBody ProvinceSaleStatistics provinceSaleStatistics) {
		if (provinceSaleStatisticsService.updateById(provinceSaleStatistics)) {
		    return Result.ok(provinceSaleStatistics, "省份销售数据-编辑成功!");
		}
		return Result.fail(provinceSaleStatistics, "错误:省份销售数据-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回省份销售数据-删除结果
	 */
	@Log(title = "省份销售数据-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("省份销售数据-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (provinceSaleStatisticsService.removeById(id)) {
			return Result.ok(true, "省份销售数据-删除成功!");
		}
		return Result.fail(false, "错误:省份销售数据-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回省份销售数据-删除结果
	 */
	@Log(title = "省份销售数据-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("省份销售数据-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.provinceSaleStatisticsService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "省份销售数据-批量删除成功!");
		}
		return Result.fail(false, "错误:省份销售数据-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回省份销售数据对象
	 */
	@ApiOperation("省份销售数据-通过id查询")
	@GetMapping("/{id}")
	public Result<ProvinceSaleStatistics> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProvinceSaleStatistics provinceSaleStatistics = provinceSaleStatisticsService.getById(id);
		return Result.ok(provinceSaleStatistics, "省份销售数据-查询成功!");
	}
}

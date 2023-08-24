package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.MarketConcentration;
import com.arg.smart.web.statistics.req.ReqMarketConcentration;
import com.arg.smart.web.statistics.service.MarketConcentrationService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 市场集中度
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "市场集中度")
@RestController
@RequestMapping("/marketConcentration")
public class MarketConcentrationController {
	@Resource
	private MarketConcentrationService marketConcentrationService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMarketConcentration 市场集中度请求参数
	 * @return 返回市场集中度-分页列表
	 */
	@ApiOperation(value = "市场集中度-分页列表查询", notes = "市场集中度-分页列表查询")
	@GetMapping
	public Result<PageResult<MarketConcentration>> queryPageList(ReqMarketConcentration reqMarketConcentration, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(marketConcentrationService.list()), "市场集中度-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param marketConcentration 市场集中度对象
	 * @return 返回市场集中度-添加结果
	 */
	@Log(title = "市场集中度-添加", operateType = OperateType.INSERT)
	@ApiOperation("市场集中度-添加")
	@PostMapping
	public Result<MarketConcentration> add(@RequestBody MarketConcentration marketConcentration) {
		if (marketConcentrationService.save(marketConcentration)) {
			return Result.ok(marketConcentration, "市场集中度-添加成功!");
		}
        return Result.fail(marketConcentration, "错误:市场集中度-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param marketConcentration 市场集中度对象
	 * @return 返回市场集中度-编辑结果
	 */
	@Log(title = "市场集中度-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("市场集中度-编辑")
	@PutMapping
	public Result<MarketConcentration> edit(@RequestBody MarketConcentration marketConcentration) {
		if (marketConcentrationService.updateById(marketConcentration)) {
		    return Result.ok(marketConcentration, "市场集中度-编辑成功!");
		}
		return Result.fail(marketConcentration, "错误:市场集中度-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回市场集中度-删除结果
	 */
	@Log(title = "市场集中度-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("市场集中度-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (marketConcentrationService.removeById(id)) {
			return Result.ok(true, "市场集中度-删除成功!");
		}
		return Result.fail(false, "错误:市场集中度-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回市场集中度-删除结果
	 */
	@Log(title = "市场集中度-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("市场集中度-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.marketConcentrationService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "市场集中度-批量删除成功!");
		}
		return Result.fail(false, "错误:市场集中度-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回市场集中度对象
	 */
	@ApiOperation("市场集中度-通过id查询")
	@GetMapping("/{id}")
	public Result<MarketConcentration> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MarketConcentration marketConcentration = marketConcentrationService.getById(id);
		return Result.ok(marketConcentration, "市场集中度-查询成功!");
	}

	//更新数据
	@ApiOperation("市场集中度-更新数据")
	@PostMapping("/updateData")
	public Result<Boolean> updateData() {
		marketConcentrationService.updateData();
		return Result.ok(true, "市场集中度-更新数据成功!");
	}
}

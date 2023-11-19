package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.MarketSize;
import com.arg.smart.web.data.req.ReqMarketSize;
import com.arg.smart.web.data.service.MarketSizeService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 市场规模
 * @author cgli
 * @date: 2023-10-29
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "市场规模")
@RestController
@RequestMapping("/marketSize")
public class MarketSizeController {
	@Resource
	private MarketSizeService marketSizeService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMarketSize 市场规模请求参数
	 * @return 返回市场规模-分页列表
	 */
	@ApiOperation(value = "市场规模-分页列表查询", notes = "市场规模-分页列表查询")
	@GetMapping
	public Result<PageResult<MarketSize>> queryPageList(ReqMarketSize reqMarketSize, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(marketSizeService.list(reqMarketSize)), "市场规模-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param marketSize 市场规模对象
	 * @return 返回市场规模-添加结果
	 */
	@Log(title = "市场规模-添加", operateType = OperateType.INSERT)
	@ApiOperation("市场规模-添加")
	@PostMapping
	public Result<MarketSize> add(@RequestBody MarketSize marketSize) {
		if (marketSizeService.save(marketSize)) {
			return Result.ok(marketSize, "市场规模-添加成功!");
		}
        return Result.fail(marketSize, "错误:市场规模-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param marketSize 市场规模对象
	 * @return 返回市场规模-编辑结果
	 */
	@Log(title = "市场规模-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("市场规模-编辑")
	@PutMapping
	public Result<MarketSize> edit(@RequestBody MarketSize marketSize) {
		if (marketSizeService.updateById(marketSize)) {
		    return Result.ok(marketSize, "市场规模-编辑成功!");
		}
		return Result.fail(marketSize, "错误:市场规模-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回市场规模-删除结果
	 */
	@Log(title = "市场规模-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("市场规模-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (marketSizeService.removeById(id)) {
			return Result.ok(true, "市场规模-删除成功!");
		}
		return Result.fail(false, "错误:市场规模-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回市场规模-删除结果
	 */
	@Log(title = "市场规模-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("市场规模-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.marketSizeService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "市场规模-批量删除成功!");
		}
		return Result.fail(false, "错误:市场规模-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回市场规模对象
	 */
	@ApiOperation("市场规模-通过id查询")
	@GetMapping("/{id}")
	public Result<MarketSize> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MarketSize marketSize = marketSizeService.getById(id);
		return Result.ok(marketSize, "市场规模-查询成功!");
	}
}

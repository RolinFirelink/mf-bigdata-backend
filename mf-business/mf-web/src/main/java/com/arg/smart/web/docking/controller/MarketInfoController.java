package com.arg.smart.web.docking.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.docking.entity.MarketInfo;
import com.arg.smart.web.docking.req.ReqMarketInfo;
import com.arg.smart.web.docking.service.MarketInfoService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 惠农网产品信息表
 * @author cgli
 * @date: 2023-09-26
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "惠农网产品信息表")
@RestController
@RequestMapping("/marketInfo")
public class MarketInfoController {
	@Resource
	private MarketInfoService marketInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMarketInfo 惠农网产品信息表请求参数
	 * @return 返回惠农网产品信息表-分页列表
	 */
	@ApiOperation(value = "惠农网产品信息表-分页列表查询", notes = "惠农网产品信息表-分页列表查询")
	@GetMapping
	public Result<PageResult<MarketInfo>> queryPageList(ReqMarketInfo reqMarketInfo, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(marketInfoService.list(reqMarketInfo)), "惠农网产品信息表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param marketInfo 惠农网产品信息表对象
	 * @return 返回惠农网产品信息表-添加结果
	 */
	@Log(title = "惠农网产品信息表-添加", operateType = OperateType.INSERT)
	@ApiOperation("惠农网产品信息表-添加")
	@PostMapping
	public Result<MarketInfo> add(@RequestBody MarketInfo marketInfo) {
		if (marketInfoService.save(marketInfo)) {
			return Result.ok(marketInfo, "惠农网产品信息表-添加成功!");
		}
        return Result.fail(marketInfo, "错误:惠农网产品信息表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param marketInfo 惠农网产品信息表对象
	 * @return 返回惠农网产品信息表-编辑结果
	 */
	@Log(title = "惠农网产品信息表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("惠农网产品信息表-编辑")
	@PutMapping
	public Result<MarketInfo> edit(@RequestBody MarketInfo marketInfo) {
		if (marketInfoService.updateById(marketInfo)) {
		    return Result.ok(marketInfo, "惠农网产品信息表-编辑成功!");
		}
		return Result.fail(marketInfo, "错误:惠农网产品信息表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回惠农网产品信息表-删除结果
	 */
	@Log(title = "惠农网产品信息表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("惠农网产品信息表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (marketInfoService.removeById(id)) {
			return Result.ok(true, "惠农网产品信息表-删除成功!");
		}
		return Result.fail(false, "错误:惠农网产品信息表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回惠农网产品信息表-删除结果
	 */
	@Log(title = "惠农网产品信息表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("惠农网产品信息表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.marketInfoService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "惠农网产品信息表-批量删除成功!");
		}
		return Result.fail(false, "错误:惠农网产品信息表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回惠农网产品信息表对象
	 */
	@ApiOperation("惠农网产品信息表-通过id查询")
	@GetMapping("/{id}")
	public Result<MarketInfo> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MarketInfo marketInfo = marketInfoService.getById(id);
		return Result.ok(marketInfo, "惠农网产品信息表-查询成功!");
	}
}

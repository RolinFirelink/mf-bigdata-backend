package com.arg.smart.web.statistics.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.MarketStatistics;
import com.arg.smart.web.statistics.entity.vo.MarketStatisticsExcel;
import com.arg.smart.web.statistics.req.ReqMarketStatistics;
import com.arg.smart.web.statistics.service.MarketStatisticsService;
import com.arg.smart.web.statistics.utils.MarketStatisticsDataListener;
import com.arg.smart.web.statistics.vo.MarketStatisticsVO;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 市场行情统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "市场行情统计表")
@RestController
@RequestMapping("/marketStatistics")
public class MarketStatisticsController {
	@Resource
	private MarketStatisticsService marketStatisticsService;

	/**
	 * 大屏——行情地图数据
	 */
	@ApiOperation(value = "大屏——行情地图数据",notes = "大屏——行情地图数据")
	@GetMapping("/public")
	public Result<List<MarketStatisticsVO>> publicList(ReqMarketStatistics reqMarketStatistics){
		return Result.ok(marketStatisticsService.list(reqMarketStatistics));
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqMarketStatistics 市场行情统计表请求参数
	 * @return 返回市场行情统计表-分页列表
	 */
	@ApiOperation(value = "市场行情统计表-分页列表查询", notes = "市场行情统计表-分页列表查询")
	@GetMapping
	public Result<PageResult<MarketStatistics>> queryPageList(ReqMarketStatistics reqMarketStatistics, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(marketStatisticsService.pageList(reqMarketStatistics)), "市场行情统计表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param marketStatistics 市场行情统计表对象
	 * @return 返回市场行情统计表-添加结果
	 */
	@Log(title = "市场行情统计表-添加", operateType = OperateType.INSERT)
	@ApiOperation("市场行情统计表-添加")
	@PostMapping
	public Result<MarketStatistics> add(@RequestBody MarketStatistics marketStatistics) {
		if (marketStatisticsService.save(marketStatistics)) {
			return Result.ok(marketStatistics, "市场行情统计表-添加成功!");
		}
        return Result.fail(marketStatistics, "错误:市场行情统计表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param marketStatistics 市场行情统计表对象
	 * @return 返回市场行情统计表-编辑结果
	 */
	@Log(title = "市场行情统计表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("市场行情统计表-编辑")
	@PutMapping
	public Result<MarketStatistics> edit(@RequestBody MarketStatistics marketStatistics) {
		if (marketStatisticsService.updateById(marketStatistics)) {
		    return Result.ok(marketStatistics, "市场行情统计表-编辑成功!");
		}
		return Result.fail(marketStatistics, "错误:市场行情统计表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回市场行情统计表-删除结果
	 */
	@Log(title = "市场行情统计表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("市场行情统计表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (marketStatisticsService.removeById(id)) {
			return Result.ok(true, "市场行情统计表-删除成功!");
		}
		return Result.fail(false, "错误:市场行情统计表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回市场行情统计表-删除结果
	 */
	@Log(title = "市场行情统计表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("市场行情统计表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.marketStatisticsService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "市场行情统计表-批量删除成功!");
		}
		return Result.fail(false, "错误:市场行情统计表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回市场行情统计表对象
	 */
	@ApiOperation("市场行情统计表-通过id查询")
	@GetMapping("/{id}")
	public Result<MarketStatistics> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MarketStatistics marketStatistics = marketStatisticsService.getById(id);
		return Result.ok(marketStatistics, "市场行情统计表-查询成功!");
	}

	/**
	 * 市场行情统计表-Excel导入
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "市场行情统计表-Excel导入",notes = "市场行情统计表-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), MarketStatisticsExcel.class, new MarketStatisticsDataListener(marketStatisticsService)).sheet().doRead();
		return Result.ok(true,"上传数据成功");
	}
}

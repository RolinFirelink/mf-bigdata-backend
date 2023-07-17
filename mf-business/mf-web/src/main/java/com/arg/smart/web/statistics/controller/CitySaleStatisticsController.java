package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.CitySaleStatistics;
import com.arg.smart.web.statistics.req.ReqCitySaleStatistics;
import com.arg.smart.web.statistics.service.CitySaleStatisticsService;
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
 * @description: 城市销售量表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "城市销售量表")
@RestController
@RequestMapping("/citySaleStatistics")
public class CitySaleStatisticsController {
	@Resource
	private CitySaleStatisticsService citySaleStatisticsService;

	/**
	 * 大屏——城市销售排行
	 */
	@ApiOperation(value="大屏——城市销售排行",notes = "大屏——城市销售排行")
	@GetMapping("/public")
	public Result<List<CitySaleStatistics>> publicList(ReqCitySaleStatistics reqCitySaleStatistics){
		return Result.ok(citySaleStatisticsService.list(reqCitySaleStatistics));
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqCitySaleStatistics 城市销售量表请求参数
	 * @return 返回城市销售量表-分页列表
	 */
	@ApiOperation(value = "城市销售量表-分页列表查询", notes = "城市销售量表-分页列表查询")
	@GetMapping
	public Result<PageResult<CitySaleStatistics>> queryPageList(ReqCitySaleStatistics reqCitySaleStatistics, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(citySaleStatisticsService.list()), "城市销售量表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param citySaleStatistics 城市销售量表对象
	 * @return 返回城市销售量表-添加结果
	 */
	@Log(title = "城市销售量表-添加", operateType = OperateType.INSERT)
	@ApiOperation("城市销售量表-添加")
	@PostMapping
	public Result<CitySaleStatistics> add(@RequestBody CitySaleStatistics citySaleStatistics) {
		if (citySaleStatisticsService.save(citySaleStatistics)) {
			return Result.ok(citySaleStatistics, "城市销售量表-添加成功!");
		}
        return Result.fail(citySaleStatistics, "错误:城市销售量表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param citySaleStatistics 城市销售量表对象
	 * @return 返回城市销售量表-编辑结果
	 */
	@Log(title = "城市销售量表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("城市销售量表-编辑")
	@PutMapping
	public Result<CitySaleStatistics> edit(@RequestBody CitySaleStatistics citySaleStatistics) {
		if (citySaleStatisticsService.updateById(citySaleStatistics)) {
		    return Result.ok(citySaleStatistics, "城市销售量表-编辑成功!");
		}
		return Result.fail(citySaleStatistics, "错误:城市销售量表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回城市销售量表-删除结果
	 */
	@Log(title = "城市销售量表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("城市销售量表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (citySaleStatisticsService.removeById(id)) {
			return Result.ok(true, "城市销售量表-删除成功!");
		}
		return Result.fail(false, "错误:城市销售量表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回城市销售量表-删除结果
	 */
	@Log(title = "城市销售量表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("城市销售量表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.citySaleStatisticsService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "城市销售量表-批量删除成功!");
		}
		return Result.fail(false, "错误:城市销售量表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回城市销售量表对象
	 */
	@ApiOperation("城市销售量表-通过id查询")
	@GetMapping("/{id}")
	public Result<CitySaleStatistics> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		CitySaleStatistics citySaleStatistics = citySaleStatisticsService.getById(id);
		return Result.ok(citySaleStatistics, "城市销售量表-查询成功!");
	}
}

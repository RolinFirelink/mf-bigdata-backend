package com.arg.smart.web.average.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.average.entity.AveragePrice;
import com.arg.smart.web.average.req.ReqAveragePrice;
import com.arg.smart.web.average.service.AveragePriceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 均价表
 * @author cgli
 * @date: 2023-06-01
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "均价表")
@RestController
@RequestMapping("/averagePrice")
public class AveragePriceController {
	@Resource
	private AveragePriceService averagePriceService;

	/**
	 * 分页列表查询
	 *
	 * @param reqAveragePrice 均价表请求参数
	 * @return 返回均价表-分页列表
	 */
	@ApiOperation(value = "均价表-分页列表查询", notes = "均价表-分页列表查询")
	@GetMapping
	public Result<PageResult<AveragePrice>> queryPageList(ReqAveragePrice reqAveragePrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(averagePriceService.list()), "均价表-查询成功!");
	}

	/**
	 * 定时添加均价数据
	 *
	 */
	@Log(title = "均价表-添加", operateType = OperateType.INSERT)
	@ApiOperation("均价表-添加")
	@Scheduled(cron = "0 0 0 * * ?") // 每天0点执行
	@PostMapping
	public void add() {
		while (!averagePriceService.timingSave()){
			log.info("定时添加均价任务执行失败,重新执行");
		}
	}

	/**
	 * 编辑
	 *
	 * @param averagePrice 均价表对象
	 * @return 返回均价表-编辑结果
	 */
	@Log(title = "均价表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("均价表-编辑")
	@PutMapping
	public Result<AveragePrice> edit(@RequestBody AveragePrice averagePrice) {
		if (averagePriceService.updateById(averagePrice)) {
		    return Result.ok(averagePrice, "均价表-编辑成功!");
		}
		return Result.fail(averagePrice, "错误:均价表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回均价表-删除结果
	 */
	@Log(title = "均价表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("均价表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (averagePriceService.removeById(id)) {
			return Result.ok(true, "均价表-删除成功!");
		}
		return Result.fail(false, "错误:均价表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回均价表-删除结果
	 */
	@Log(title = "均价表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("均价表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.averagePriceService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "均价表-批量删除成功!");
		}
		return Result.fail(false, "错误:均价表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回均价表对象
	 */
	@ApiOperation("均价表-通过id查询")
	@GetMapping("/{id}")
	public Result<AveragePrice> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		AveragePrice averagePrice = averagePriceService.getById(id);
		return Result.ok(averagePrice, "均价表-查询成功!");
	}
}

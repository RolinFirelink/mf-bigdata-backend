package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;

import com.arg.smart.web.statistics.entity.PurchasingHeat;
import com.arg.smart.web.statistics.req.ReqPurchasingHeat;
import com.arg.smart.web.statistics.service.PurchasingHeatService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 采购热度
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "采购热度")
@RestController
@RequestMapping("/purchasingHeat")
public class PurchasingHeatController {
	@Resource
	private PurchasingHeatService purchasingHeatService;

	/**
	 * 分页列表查询
	 *
	 * @param reqPurchasingHeat 采购热度请求参数
	 * @return 返回采购热度-分页列表
	 */
	@ApiOperation(value = "采购热度-分页列表查询", notes = "采购热度-分页列表查询")
	@GetMapping
	public Result<PageResult<PurchasingHeat>> queryPageList(ReqPurchasingHeat reqPurchasingHeat, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(purchasingHeatService.list()), "采购热度-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param purchasingHeat 采购热度对象
	 * @return 返回采购热度-添加结果
	 */
	@Log(title = "采购热度-添加", operateType = OperateType.INSERT)
	@ApiOperation("采购热度-添加")
	@PostMapping
	public Result<PurchasingHeat> add(@RequestBody PurchasingHeat purchasingHeat) {
		if (purchasingHeatService.save(purchasingHeat)) {
			return Result.ok(purchasingHeat, "采购热度-添加成功!");
		}
        return Result.fail(purchasingHeat, "错误:采购热度-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param purchasingHeat 采购热度对象
	 * @return 返回采购热度-编辑结果
	 */
	@Log(title = "采购热度-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("采购热度-编辑")
	@PutMapping
	public Result<PurchasingHeat> edit(@RequestBody PurchasingHeat purchasingHeat) {
		if (purchasingHeatService.updateById(purchasingHeat)) {
		    return Result.ok(purchasingHeat, "采购热度-编辑成功!");
		}
		return Result.fail(purchasingHeat, "错误:采购热度-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回采购热度-删除结果
	 */
	@Log(title = "采购热度-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("采购热度-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (purchasingHeatService.removeById(id)) {
			return Result.ok(true, "采购热度-删除成功!");
		}
		return Result.fail(false, "错误:采购热度-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回采购热度-删除结果
	 */
	@Log(title = "采购热度-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("采购热度-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.purchasingHeatService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "采购热度-批量删除成功!");
		}
		return Result.fail(false, "错误:采购热度-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回采购热度对象
	 */
	@ApiOperation("采购热度-通过id查询")
	@GetMapping("/{id}")
	public Result<PurchasingHeat> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		PurchasingHeat purchasingHeat = purchasingHeatService.getById(id);
		return Result.ok(purchasingHeat, "采购热度-查询成功!");
	}


	/**
	 *
	 * @param purchasingHeat
	 * @return
	 */
	@ApiOperation("采购热度-通过企业日销售数据计算热度，删除数据并批量添加")
	@PutMapping("/public/update")
	public Result<PurchasingHeat> updatePurchasingHeat(@RequestBody PurchasingHeat purchasingHeat) {
		purchasingHeatService.updatePurchasingHeat(purchasingHeat);
		return Result.ok(purchasingHeat, "采购热度-更新成功!");
	}
}

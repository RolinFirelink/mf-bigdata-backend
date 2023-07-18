package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.vo.SupplyHeatReponseData;
import com.arg.smart.web.data.req.ReqProductBaseDayData;
import com.arg.smart.web.data.service.ProductBaseDayDataService;
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
 * @description: 产品基地每日数据
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品基地每日数据")
@RestController
@RequestMapping("/productBaseDayData")
public class ProductBaseDayDataController {
	@Resource
	private ProductBaseDayDataService productBaseDayDataService;


	/**
	 * 供应热度
	 * @param reqProductBaseDayData 产品基地每日数据参数
	 * @return 返回各产地信息
	 */
	@ApiOperation("供应热度")
	@GetMapping("/public/supplyHeat")
	public Result<List<SupplyHeatReponseData>> getSupplyHeat( ReqProductBaseDayData reqProductBaseDayData){
		return Result.ok(productBaseDayDataService.getSupplyHeat(reqProductBaseDayData));

	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProductBaseDayData 产品基地每日数据请求参数
	 * @return 返回产品基地每日数据-分页列表
	 */
	@ApiOperation(value = "产品基地每日数据-分页列表查询", notes = "产品基地每日数据-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductBaseDayData>> queryPageList(ReqProductBaseDayData reqProductBaseDayData, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productBaseDayDataService.list()), "产品基地每日数据-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productBaseDayData 产品基地每日数据对象
	 * @return 返回产品基地每日数据-添加结果
	 */
	@Log(title = "产品基地每日数据-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品基地每日数据-添加")
	@PostMapping
	public Result<ProductBaseDayData> add(@RequestBody ProductBaseDayData productBaseDayData) {
		if (productBaseDayDataService.save(productBaseDayData)) {
			return Result.ok(productBaseDayData, "产品基地每日数据-添加成功!");
		}
        return Result.fail(productBaseDayData, "错误:产品基地每日数据-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productBaseDayData 产品基地每日数据对象
	 * @return 返回产品基地每日数据-编辑结果
	 */
	@Log(title = "产品基地每日数据-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品基地每日数据-编辑")
	@PutMapping
	public Result<ProductBaseDayData> edit(@RequestBody ProductBaseDayData productBaseDayData) {
		if (productBaseDayDataService.updateById(productBaseDayData)) {
		    return Result.ok(productBaseDayData, "产品基地每日数据-编辑成功!");
		}
		return Result.fail(productBaseDayData, "错误:产品基地每日数据-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品基地每日数据-删除结果
	 */
	@Log(title = "产品基地每日数据-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品基地每日数据-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productBaseDayDataService.removeById(id)) {
			return Result.ok(true, "产品基地每日数据-删除成功!");
		}
		return Result.fail(false, "错误:产品基地每日数据-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品基地每日数据-删除结果
	 */
	@Log(title = "产品基地每日数据-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品基地每日数据-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productBaseDayDataService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品基地每日数据-批量删除成功!");
		}
		return Result.fail(false, "错误:产品基地每日数据-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品基地每日数据对象
	 */
	@ApiOperation("产品基地每日数据-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductBaseDayData> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductBaseDayData productBaseDayData = productBaseDayDataService.getById(id);
		return Result.ok(productBaseDayData, "产品基地每日数据-查询成功!");
	}

}

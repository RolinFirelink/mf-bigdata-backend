package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.ProductionData;
import com.arg.smart.web.statistics.req.ReqProductionData;
import com.arg.smart.web.statistics.service.ProductionDataService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 地区生产宏观数据
 * @author cgli
 * @date: 2023-10-19
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "地区生产宏观数据")
@RestController
@RequestMapping("/productionData")
public class ProductionDataController {
	@Resource
	private ProductionDataService productionDataService;

	/**
	 * 分页列表查询
	 *
	 * @param reqProductionData 地区生产宏观数据请求参数
	 * @return 返回地区生产宏观数据-分页列表
	 */
	@ApiOperation(value = "地区生产宏观数据-分页列表查询", notes = "地区生产宏观数据-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductionData>> queryPageList(ReqProductionData reqProductionData, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productionDataService.list(reqProductionData)), "地区生产宏观数据-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productionData 地区生产宏观数据对象
	 * @return 返回地区生产宏观数据-添加结果
	 */
	@Log(title = "地区生产宏观数据-添加", operateType = OperateType.INSERT)
	@ApiOperation("地区生产宏观数据-添加")
	@PostMapping
	public Result<ProductionData> add(@RequestBody ProductionData productionData) {
		if (productionDataService.save(productionData)) {
			return Result.ok(productionData, "地区生产宏观数据-添加成功!");
		}
        return Result.fail(productionData, "错误:地区生产宏观数据-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productionData 地区生产宏观数据对象
	 * @return 返回地区生产宏观数据-编辑结果
	 */
	@Log(title = "地区生产宏观数据-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("地区生产宏观数据-编辑")
	@PutMapping
	public Result<ProductionData> edit(@RequestBody ProductionData productionData) {
		if (productionDataService.updateById(productionData)) {
		    return Result.ok(productionData, "地区生产宏观数据-编辑成功!");
		}
		return Result.fail(productionData, "错误:地区生产宏观数据-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回地区生产宏观数据-删除结果
	 */
	@Log(title = "地区生产宏观数据-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("地区生产宏观数据-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productionDataService.removeById(id)) {
			return Result.ok(true, "地区生产宏观数据-删除成功!");
		}
		return Result.fail(false, "错误:地区生产宏观数据-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回地区生产宏观数据-删除结果
	 */
	@Log(title = "地区生产宏观数据-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("地区生产宏观数据-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productionDataService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "地区生产宏观数据-批量删除成功!");
		}
		return Result.fail(false, "错误:地区生产宏观数据-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回地区生产宏观数据对象
	 */
	@ApiOperation("地区生产宏观数据-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductionData> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductionData productionData = productionDataService.getById(id);
		return Result.ok(productionData, "地区生产宏观数据-查询成功!");
	}
}

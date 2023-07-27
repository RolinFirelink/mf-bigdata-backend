package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.BaseProductProductionScale;
import com.arg.smart.web.data.entity.vo.ProductionScaleResponseData;
import com.arg.smart.web.data.req.ReqBaseProductProductionScale;
import com.arg.smart.web.data.service.BaseProductProductionScaleService;
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
 * @description: 基地产品生产规模数据表
 * @author cgli
 * @date: 2023-07-20
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "基地产品生产规模数据表")
@RestController
@RequestMapping("/baseProductProductionScale")
public class BaseProductProductionScaleController {
	@Resource
	private BaseProductProductionScaleService baseProductProductionScaleService;

	/**
	 * 大屏——生产分布数据
	 *
	 * @param reqBaseProductProductionScale 基地产品生产规模数据表请求参数
	 * @return 返回大屏——生产分布数据
	 */
	@ApiOperation(value = "大屏——生产分布数据", notes = "大屏——生产分布数据")
	@GetMapping("/public")
	public Result<List<ProductionScaleResponseData>> getProductionScale(ReqBaseProductProductionScale reqBaseProductProductionScale) {
		return Result.ok(baseProductProductionScaleService.getProductionScale(reqBaseProductProductionScale), "大屏——生产分布数据-查询成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqBaseProductProductionScale 基地产品生产规模数据表请求参数
	 * @return 返回基地产品生产规模数据表-分页列表
	 */
	@ApiOperation(value = "基地产品生产规模数据表-分页列表查询", notes = "基地产品生产规模数据表-分页列表查询")
	@GetMapping
	public Result<PageResult<BaseProductProductionScale>> queryPageList(ReqBaseProductProductionScale reqBaseProductProductionScale, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(baseProductProductionScaleService.pageList(reqBaseProductProductionScale), "基地产品生产规模数据表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param baseProductProductionScale 基地产品生产规模数据表对象
	 * @return 返回基地产品生产规模数据表-添加结果
	 */
	@Log(title = "基地产品生产规模数据表-添加", operateType = OperateType.INSERT)
	@ApiOperation("基地产品生产规模数据表-添加")
	@PostMapping
	public Result<BaseProductProductionScale> add(@RequestBody BaseProductProductionScale baseProductProductionScale) {
		if (baseProductProductionScaleService.save(baseProductProductionScale)) {
			return Result.ok(baseProductProductionScale, "基地产品生产规模数据表-添加成功!");
		}
        return Result.fail(baseProductProductionScale, "错误:基地产品生产规模数据表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param baseProductProductionScale 基地产品生产规模数据表对象
	 * @return 返回基地产品生产规模数据表-编辑结果
	 */
	@Log(title = "基地产品生产规模数据表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("基地产品生产规模数据表-编辑")
	@PutMapping
	public Result<BaseProductProductionScale> edit(@RequestBody BaseProductProductionScale baseProductProductionScale) {
		if (baseProductProductionScaleService.updateById(baseProductProductionScale)) {
		    return Result.ok(baseProductProductionScale, "基地产品生产规模数据表-编辑成功!");
		}
		return Result.fail(baseProductProductionScale, "错误:基地产品生产规模数据表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回基地产品生产规模数据表-删除结果
	 */
	@Log(title = "基地产品生产规模数据表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("基地产品生产规模数据表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (baseProductProductionScaleService.removeById(id)) {
			return Result.ok(true, "基地产品生产规模数据表-删除成功!");
		}
		return Result.fail(false, "错误:基地产品生产规模数据表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回基地产品生产规模数据表-删除结果
	 */
	@Log(title = "基地产品生产规模数据表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("基地产品生产规模数据表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.baseProductProductionScaleService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "基地产品生产规模数据表-批量删除成功!");
		}
		return Result.fail(false, "错误:基地产品生产规模数据表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回基地产品生产规模数据表对象
	 */
	@ApiOperation("基地产品生产规模数据表-通过id查询")
	@GetMapping("/{id}")
	public Result<BaseProductProductionScale> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		BaseProductProductionScale baseProductProductionScale = baseProductProductionScaleService.getById(id);
		return Result.ok(baseProductProductionScale, "基地产品生产规模数据表-查询成功!");
	}
}

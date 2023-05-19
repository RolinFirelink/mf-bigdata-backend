package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.req.ReqMaterialProduce;
import com.arg.smart.web.product.service.MaterialProduceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 产品生产表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品生产表")
@RestController
@RequestMapping("/materialProduce")
public class MaterialProduceController {
	@Resource
	private MaterialProduceService materialProduceService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMaterialProduce 产品生产表请求参数
	 * @return 返回产品生产表-分页列表
	 */
	@ApiOperation(value = "产品生产表-分页列表查询", notes = "产品生产表-分页列表查询")
	@GetMapping
	public Result<PageResult<MaterialProduce>> queryPageList(ReqMaterialProduce reqMaterialProduce, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(materialProduceService.list()), "产品生产表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param materialProduce 产品生产表对象
	 * @return 返回产品生产表-添加结果
	 */
	@Log(title = "产品生产表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品生产表-添加")
	@PostMapping
	public Result<MaterialProduce> add(@RequestBody MaterialProduce materialProduce) {
		if (materialProduceService.save(materialProduce)) {
			return Result.ok(materialProduce, "产品生产表-添加成功!");
		}
        return Result.fail(materialProduce, "错误:产品生产表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param materialProduce 产品生产表对象
	 * @return 返回产品生产表-编辑结果
	 */
	@Log(title = "产品生产表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品生产表-编辑")
	@PutMapping
	public Result<MaterialProduce> edit(@RequestBody MaterialProduce materialProduce) {
		if (materialProduceService.updateById(materialProduce)) {
		    return Result.ok(materialProduce, "产品生产表-编辑成功!");
		}
		return Result.fail(materialProduce, "错误:产品生产表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品生产表-删除结果
	 */
	@Log(title = "产品生产表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品生产表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (materialProduceService.removeById(id)) {
			return Result.ok(true, "产品生产表-删除成功!");
		}
		return Result.fail(false, "错误:产品生产表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品生产表-删除结果
	 */
	@Log(title = "产品生产表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品生产表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.materialProduceService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品生产表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品生产表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品生产表对象
	 */
	@ApiOperation("产品生产表-通过id查询")
	@GetMapping("/{id}")
	public Result<MaterialProduce> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MaterialProduce materialProduce = materialProduceService.getById(id);
		return Result.ok(materialProduce, "产品生产表-查询成功!");
	}
}

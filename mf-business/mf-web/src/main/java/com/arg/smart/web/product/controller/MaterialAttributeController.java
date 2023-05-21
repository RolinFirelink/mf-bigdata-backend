package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.MaterialAttribute;
import com.arg.smart.web.product.req.ReqMaterialAttribute;
import com.arg.smart.web.product.service.MaterialAttributeService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 产品属性表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品属性表")
@RestController
@RequestMapping("/materialAttribute")
public class MaterialAttributeController {
	@Resource
	private MaterialAttributeService materialAttributeService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMaterialAttribute 产品属性表请求参数
	 * @return 返回产品属性表-分页列表
	 */
	@ApiOperation(value = "产品属性表-分页列表查询", notes = "产品属性表-分页列表查询")
	@GetMapping
	public Result<PageResult<MaterialAttribute>> queryPageList(ReqMaterialAttribute reqMaterialAttribute, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(materialAttributeService.list()), "产品属性表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param materialAttribute 产品属性表对象
	 * @return 返回产品属性表-添加结果
	 */
	@Log(title = "产品属性表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品属性表-添加")
	@PostMapping
	public Result<MaterialAttribute> add(@RequestBody MaterialAttribute materialAttribute) {
		if (materialAttributeService.save(materialAttribute)) {
			return Result.ok(materialAttribute, "产品属性表-添加成功!");
		}
        return Result.fail(materialAttribute, "错误:产品属性表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param materialAttribute 产品属性表对象
	 * @return 返回产品属性表-编辑结果
	 */
	@Log(title = "产品属性表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品属性表-编辑")
	@PutMapping
	public Result<MaterialAttribute> edit(@RequestBody MaterialAttribute materialAttribute) {
		if (materialAttributeService.updateById(materialAttribute)) {
		    return Result.ok(materialAttribute, "产品属性表-编辑成功!");
		}
		return Result.fail(materialAttribute, "错误:产品属性表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品属性表-删除结果
	 */
	@Log(title = "产品属性表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品属性表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (materialAttributeService.removeById(id)) {
			return Result.ok(true, "产品属性表-删除成功!");
		}
		return Result.fail(false, "错误:产品属性表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品属性表-删除结果
	 */
	@Log(title = "产品属性表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品属性表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.materialAttributeService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品属性表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品属性表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品属性表对象
	 */
	@ApiOperation("产品属性表-通过id查询")
	@GetMapping("/{id}")
	public Result<MaterialAttribute> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MaterialAttribute materialAttribute = materialAttributeService.getById(id);
		return Result.ok(materialAttribute, "产品属性表-查询成功!");
	}
}

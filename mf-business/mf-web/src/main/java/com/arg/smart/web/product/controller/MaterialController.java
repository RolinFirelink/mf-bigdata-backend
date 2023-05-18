package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.Material;
import com.arg.smart.web.product.req.ReqMaterial;
import com.arg.smart.web.product.service.MaterialService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 产品表
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品表")
@RestController
@RequestMapping("/material")
public class MaterialController {
	@Resource
	private MaterialService materialService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMaterial 产品表请求参数
	 * @return 返回产品表-分页列表
	 */
	@ApiOperation(value = "产品表-分页列表查询", notes = "产品表-分页列表查询")
	@GetMapping
	public Result<PageResult<Material>> queryPageList(ReqMaterial reqMaterial, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(materialService.list()), "产品表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param material 产品表对象
	 * @return 返回产品表-添加结果
	 */
	@Log(title = "产品表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品表-添加")
	@PostMapping
	public Result<Material> add(@RequestBody Material material) {
		if (materialService.save(material)) {
			return Result.ok(material, "产品表-添加成功!");
		}
        return Result.fail(material, "错误:产品表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param material 产品表对象
	 * @return 返回产品表-编辑结果
	 */
	@Log(title = "产品表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品表-编辑")
	@PutMapping
	public Result<Material> edit(@RequestBody Material material) {
		if (materialService.updateById(material)) {
		    return Result.ok(material, "产品表-编辑成功!");
		}
		return Result.fail(material, "错误:产品表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品表-删除结果
	 */
	@Log(title = "产品表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (materialService.removeById(id)) {
			return Result.ok(true, "产品表-删除成功!");
		}
		return Result.fail(false, "错误:产品表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品表-删除结果
	 */
	@Log(title = "产品表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.materialService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品表对象
	 */
	@ApiOperation("产品表-通过id查询")
	@GetMapping("/{id}")
	public Result<Material> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Material material = materialService.getById(id);
		return Result.ok(material, "产品表-查询成功!");
	}
}

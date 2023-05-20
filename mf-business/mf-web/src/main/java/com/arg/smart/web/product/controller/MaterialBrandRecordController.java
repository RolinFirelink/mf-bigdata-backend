package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.MaterialBrandRecord;
import com.arg.smart.web.product.req.ReqMaterialBrandRecord;
import com.arg.smart.web.product.service.MaterialBrandRecordService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 品牌产品中间表
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "品牌产品中间表")
@RestController
@RequestMapping("/materialBrandRecord")
public class MaterialBrandRecordController {
	@Resource
	private MaterialBrandRecordService materialBrandRecordService;

	/**
	 * 分页列表查询
	 *
	 * @param reqMaterialBrandRecord 品牌产品中间表请求参数
	 * @return 返回品牌产品中间表-分页列表
	 */
	@ApiOperation(value = "品牌产品中间表-分页列表查询", notes = "品牌产品中间表-分页列表查询")
	@GetMapping
	public Result<PageResult<MaterialBrandRecord>> queryPageList(ReqMaterialBrandRecord reqMaterialBrandRecord, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(materialBrandRecordService.list()), "品牌产品中间表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param materialBrandRecord 品牌产品中间表对象
	 * @return 返回品牌产品中间表-添加结果
	 */
	@Log(title = "品牌产品中间表-添加", operateType = OperateType.INSERT)
	@ApiOperation("品牌产品中间表-添加")
	@PostMapping
	public Result<MaterialBrandRecord> add(@RequestBody MaterialBrandRecord materialBrandRecord) {
		if (materialBrandRecordService.save(materialBrandRecord)) {
			return Result.ok(materialBrandRecord, "品牌产品中间表-添加成功!");
		}
        return Result.fail(materialBrandRecord, "错误:品牌产品中间表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param materialBrandRecord 品牌产品中间表对象
	 * @return 返回品牌产品中间表-编辑结果
	 */
	@Log(title = "品牌产品中间表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("品牌产品中间表-编辑")
	@PutMapping
	public Result<MaterialBrandRecord> edit(@RequestBody MaterialBrandRecord materialBrandRecord) {
		if (materialBrandRecordService.updateById(materialBrandRecord)) {
		    return Result.ok(materialBrandRecord, "品牌产品中间表-编辑成功!");
		}
		return Result.fail(materialBrandRecord, "错误:品牌产品中间表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回品牌产品中间表-删除结果
	 */
	@Log(title = "品牌产品中间表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("品牌产品中间表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (materialBrandRecordService.removeById(id)) {
			return Result.ok(true, "品牌产品中间表-删除成功!");
		}
		return Result.fail(false, "错误:品牌产品中间表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回品牌产品中间表-删除结果
	 */
	@Log(title = "品牌产品中间表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("品牌产品中间表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.materialBrandRecordService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "品牌产品中间表-批量删除成功!");
		}
		return Result.fail(false, "错误:品牌产品中间表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回品牌产品中间表对象
	 */
	@ApiOperation("品牌产品中间表-通过id查询")
	@GetMapping("/{id}")
	public Result<MaterialBrandRecord> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		MaterialBrandRecord materialBrandRecord = materialBrandRecordService.getById(id);
		return Result.ok(materialBrandRecord, "品牌产品中间表-查询成功!");
	}
}

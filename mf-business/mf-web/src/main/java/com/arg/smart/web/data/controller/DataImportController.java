package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.DataImport;
import com.arg.smart.web.data.req.ReqDataImport;
import com.arg.smart.web.data.service.DataImportService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: sh_data_import
 * @author cgli
 * @date: 2023-06-05
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "sh_data_import")
@RestController
@RequestMapping("/dataImport")
public class
DataImportController {
	@Resource
	private DataImportService dataImportService;

	/**
	 * 分页列表查询
	 *
	 * @param reqDataImport sh_data_import请求参数
	 * @return 返回sh_data_import-分页列表
	 */
	@ApiOperation(value = "sh_data_import-分页列表查询", notes = "sh_data_import-分页列表查询")
	@GetMapping
	public Result<PageResult<DataImport>> queryPageList(ReqDataImport reqDataImport, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(dataImportService.list()), "sh_data_import-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param dataImport sh_data_import对象
	 * @return 返回sh_data_import-添加结果
	 */
	@Log(title = "sh_data_import-添加", operateType = OperateType.INSERT)
	@ApiOperation("sh_data_import-添加")
	@PostMapping
	public Result<DataImport> add(@RequestBody DataImport dataImport) {
		if (dataImportService.save(dataImport)) {
			return Result.ok(dataImport, "sh_data_import-添加成功!");
		}
        return Result.fail(dataImport, "错误:sh_data_import-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param dataImport sh_data_import对象
	 * @return 返回sh_data_import-编辑结果
	 */
	@Log(title = "sh_data_import-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("sh_data_import-编辑")
	@PutMapping
	public Result<DataImport> edit(@RequestBody DataImport dataImport) {
		if (dataImportService.updateById(dataImport)) {
		    return Result.ok(dataImport, "sh_data_import-编辑成功!");
		}
		return Result.fail(dataImport, "错误:sh_data_import-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回sh_data_import-删除结果
	 */
	@Log(title = "sh_data_import-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("sh_data_import-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (dataImportService.removeById(id)) {
			return Result.ok(true, "sh_data_import-删除成功!");
		}
		return Result.fail(false, "错误:sh_data_import-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回sh_data_import-删除结果
	 */
	@Log(title = "sh_data_import-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("sh_data_import-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.dataImportService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "sh_data_import-批量删除成功!");
		}
		return Result.fail(false, "错误:sh_data_import-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回sh_data_import对象
	 */
	@ApiOperation("sh_data_import-通过id查询")
	@GetMapping("/{id}")
	public Result<DataImport> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		DataImport dataImport = dataImportService.getById(id);
		return Result.ok(dataImport, "sh_data_import-查询成功!");
	}
}

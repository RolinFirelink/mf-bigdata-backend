package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.CompanySales;
import com.arg.smart.web.data.req.ReqCompanySales;
import com.arg.smart.web.data.service.CompanySalesService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 企业销售数据
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "企业销售数据")
@RestController
@RequestMapping("/companySales")
public class CompanySalesController {
	@Resource
	private CompanySalesService companySalesService;

	/**
	 * 分页列表查询
	 *
	 * @param reqCompanySales 企业销售数据请求参数
	 * @return 返回企业销售数据-分页列表
	 */
	@ApiOperation(value = "企业销售数据-分页列表查询", notes = "企业销售数据-分页列表查询")
	@GetMapping
	public Result<PageResult<CompanySales>> queryPageList(ReqCompanySales reqCompanySales, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(companySalesService.list()), "企业销售数据-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param companySales 企业销售数据对象
	 * @return 返回企业销售数据-添加结果
	 */
	@Log(title = "企业销售数据-添加", operateType = OperateType.INSERT)
	@ApiOperation("企业销售数据-添加")
	@PostMapping
	public Result<CompanySales> add(@RequestBody CompanySales companySales) {
		if (companySalesService.save(companySales)) {
			return Result.ok(companySales, "企业销售数据-添加成功!");
		}
        return Result.fail(companySales, "错误:企业销售数据-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param companySales 企业销售数据对象
	 * @return 返回企业销售数据-编辑结果
	 */
	@Log(title = "企业销售数据-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("企业销售数据-编辑")
	@PutMapping
	public Result<CompanySales> edit(@RequestBody CompanySales companySales) {
		if (companySalesService.updateById(companySales)) {
		    return Result.ok(companySales, "企业销售数据-编辑成功!");
		}
		return Result.fail(companySales, "错误:企业销售数据-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回企业销售数据-删除结果
	 */
	@Log(title = "企业销售数据-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("企业销售数据-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (companySalesService.removeById(id)) {
			return Result.ok(true, "企业销售数据-删除成功!");
		}
		return Result.fail(false, "错误:企业销售数据-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回企业销售数据-删除结果
	 */
	@Log(title = "企业销售数据-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("企业销售数据-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.companySalesService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "企业销售数据-批量删除成功!");
		}
		return Result.fail(false, "错误:企业销售数据-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回企业销售数据对象
	 */
	@ApiOperation("企业销售数据-通过id查询")
	@GetMapping("/{id}")
	public Result<CompanySales> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		CompanySales companySales = companySalesService.getById(id);
		return Result.ok(companySales, "企业销售数据-查询成功!");
	}
}

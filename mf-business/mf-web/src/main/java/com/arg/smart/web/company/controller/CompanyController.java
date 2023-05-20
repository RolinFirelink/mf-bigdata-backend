package com.arg.smart.web.company.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.service.CompanyService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 企业、供货商、销售商和承运商
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "企业、供货商、销售商和承运商")
@RestController
@RequestMapping("/company")
public class CompanyController {
	@Resource
	private CompanyService companyService;

	/**
	 * 分页列表查询
	 *
	 * @param reqCompany 企业、供货商、销售商和承运商请求参数
	 * @return 返回企业、供货商、销售商和承运商-分页列表
	 */
	@ApiOperation(value = "企业、供货商、销售商和承运商-分页列表查询", notes = "企业、供货商、销售商和承运商-分页列表查询")
	@GetMapping
	public Result<PageResult<Company>> queryPageList(ReqCompany reqCompany, ReqPage reqPage,@RequestParam int companyType) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(companyService.SelectListByCompanyType(companyType)), "企业、供货商、销售商和承运商-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param company 企业、供货商、销售商和承运商对象
	 * @return 返回企业、供货商、销售商和承运商-添加结果
	 */
	@Log(title = "企业、供货商、销售商和承运商-添加", operateType = OperateType.INSERT)
	@ApiOperation("企业、供货商、销售商和承运商-添加")
	@PostMapping
	public Result<Company> add(@RequestBody Company company) {
		if (companyService.save(company)) {
			return Result.ok(company, "企业、供货商、销售商和承运商-添加成功!");
		}
        return Result.fail(company, "错误:企业、供货商、销售商和承运商-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param company 企业、供货商、销售商和承运商对象
	 * @return 返回企业、供货商、销售商和承运商-编辑结果
	 */
	@Log(title = "企业、供货商、销售商和承运商-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("企业、供货商、销售商和承运商-编辑")
	@PutMapping
	public Result<Company> edit(@RequestBody Company company) {
		if (companyService.updateById(company)) {
		    return Result.ok(company, "企业、供货商、销售商和承运商-编辑成功!");
		}
		return Result.fail(company, "错误:企业、供货商、销售商和承运商-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回企业、供货商、销售商和承运商-删除结果
	 */
	@Log(title = "企业、供货商、销售商和承运商-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("企业、供货商、销售商和承运商-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (companyService.removeById(id)) {
			return Result.ok(true, "企业、供货商、销售商和承运商-删除成功!");
		}
		return Result.fail(false, "错误:企业、供货商、销售商和承运商-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回企业、供货商、销售商和承运商-删除结果
	 */
	@Log(title = "企业、供货商、销售商和承运商-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("企业、供货商、销售商和承运商-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.companyService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "企业、供货商、销售商和承运商-批量删除成功!");
		}
		return Result.fail(false, "错误:企业、供货商、销售商和承运商-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回企业、供货商、销售商和承运商对象
	 */
	@ApiOperation("企业、供货商、销售商和承运商-通过id查询")
	@GetMapping("/{id}")
	public Result<Company> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Company company = companyService.getById(id);
		return Result.ok(company, "企业、供货商、销售商和承运商-查询成功!");
	}
}

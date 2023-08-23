package com.arg.smart.web.company.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.entity.vo.CompanyExcel;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.service.CompanyService;
import com.arg.smart.web.company.uitls.CompanyDataListener;
import com.arg.smart.web.company.vo.CompanyVO;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	 * 企业数据主表-Excel导入
	 *
	 * @param file 企业主表Excel数据
	 */
	@ApiOperation(value = "订单数据主表-Excel导入",notes = "订单数据主表-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), CompanyExcel.class, new CompanyDataListener(companyService)).sheet().doRead();
		return Result.ok(true,"上传数据成功");
	}

	/**
	 * 根据公司类型获取公司选项
	 *
	 * @param companyType 公司类型
	 * @return 返回公司选项
	 */
	@ApiOperation(value = "企业、供货商、销售商和承运商-分页列表查询", notes = "企业、供货商、销售商和承运商-分页列表查询")
	@GetMapping("/options/{companyType}")
	public Result<List<Company>> getOptions(@PathVariable("companyType") Integer companyType) {
		return Result.ok(companyService.getOptionsByCompanyType(companyType), "企业、供货商、销售商和承运商-查询成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqCompany 企业、供货商、销售商和承运商请求参数
	 * @return 返回企业、供货商、销售商和承运商-分页列表
	 */
	@ApiOperation(value = "企业、供货商、销售商和承运商-分页列表查询", notes = "企业、供货商、销售商和承运商-分页列表查询")
	@GetMapping
	public Result<PageResult<Company>> queryPageList(ReqCompany reqCompany, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(companyService.SelectListByCondition(reqCompany)), "企业、供货商、销售商和承运商-查询成功!");
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
		if (companyService.saveCompany(company)) {
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
		if (companyService.updateCompanyById(company)) {
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

/**
 * 通过公司ID获取某个市各个区的公司信息
 * @param flag
 * @param cityName 城市名称
 * @return 城市和公司信息的映射
 */
	@ApiOperation("获取公司信息")
	@PostMapping("/company/{flag}")
	public Result<Map<String, List<CompanyVO>>> getCompanyVOByCity(
			@PathVariable("flag") Integer flag,
			@RequestBody String cityName) {
		Map<String, List<CompanyVO>> companyMap = companyService.getCompanyVOByCity(flag, cityName);
		return Result.ok(companyMap,"公司信息-查询成功");
	}
}

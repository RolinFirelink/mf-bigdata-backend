package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.CompanyRevenue;
import com.arg.smart.web.data.req.ReqCompanyRevenue;
import com.arg.smart.web.data.service.CompanyRevenueService;
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
 * @description: 公司营收
 * @author cgli
 * @date: 2023-10-29
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "公司营收")
@RestController
@RequestMapping("/companyRevenue")
public class CompanyRevenueController {
	@Resource
	private CompanyRevenueService companyRevenueService;

	@GetMapping("/public/companyRevenue/{year}")
	public Result<List<CompanyRevenue>> queryCompanyRevenue(@ApiParam(name = "year", value = "年") @PathVariable Integer year){
		return Result.ok(companyRevenueService.selectCompanyRevenue(year),"查询公司年营收成功！");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqCompanyRevenue 公司营收请求参数
	 * @return 返回公司营收-分页列表
	 */
	@ApiOperation(value = "公司营收-分页列表查询", notes = "公司营收-分页列表查询")
	@GetMapping
	public Result<PageResult<CompanyRevenue>> queryPageList(ReqCompanyRevenue reqCompanyRevenue, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(companyRevenueService.list(reqCompanyRevenue)), "公司营收-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param companyRevenue 公司营收对象
	 * @return 返回公司营收-添加结果
	 */
	@Log(title = "公司营收-添加", operateType = OperateType.INSERT)
	@ApiOperation("公司营收-添加")
	@PostMapping
	public Result<CompanyRevenue> add(@RequestBody CompanyRevenue companyRevenue) {
		if (companyRevenueService.save(companyRevenue)) {
			return Result.ok(companyRevenue, "公司营收-添加成功!");
		}
        return Result.fail(companyRevenue, "错误:公司营收-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param companyRevenue 公司营收对象
	 * @return 返回公司营收-编辑结果
	 */
	@Log(title = "公司营收-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("公司营收-编辑")
	@PutMapping
	public Result<CompanyRevenue> edit(@RequestBody CompanyRevenue companyRevenue) {
		if (companyRevenueService.updateById(companyRevenue)) {
		    return Result.ok(companyRevenue, "公司营收-编辑成功!");
		}
		return Result.fail(companyRevenue, "错误:公司营收-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回公司营收-删除结果
	 */
	@Log(title = "公司营收-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("公司营收-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (companyRevenueService.removeById(id)) {
			return Result.ok(true, "公司营收-删除成功!");
		}
		return Result.fail(false, "错误:公司营收-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回公司营收-删除结果
	 */
	@Log(title = "公司营收-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("公司营收-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.companyRevenueService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "公司营收-批量删除成功!");
		}
		return Result.fail(false, "错误:公司营收-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回公司营收对象
	 */
	@ApiOperation("公司营收-通过id查询")
	@GetMapping("/{id}")
	public Result<CompanyRevenue> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		CompanyRevenue companyRevenue = companyRevenueService.getById(id);
		return Result.ok(companyRevenue, "公司营收-查询成功!");
	}
}

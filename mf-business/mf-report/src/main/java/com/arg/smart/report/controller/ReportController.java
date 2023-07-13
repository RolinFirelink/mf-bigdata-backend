package com.arg.smart.report.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.report.entity.vo.ReportList;
import com.arg.smart.report.req.ReqReport;
import com.arg.smart.report.service.ReportService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.arg.smart.report.entity.Report;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 报表页面
 * @author cgli
 * @date: 2023-05-31
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "报表页面")
@RestController
@RequestMapping("/report")
public class ReportController {
	@Resource
	private ReportService reportService;


	/**
	 * PC端获取大数据页面列表（新）
	 */
	@ApiOperation(value = "大数据页面-PC端列表",notes = "大数据页面-PC端列表")
	@GetMapping("/public/{flag}")
	public Result<List<Report>> getReportList(@PathVariable("flag") Integer flag,String key){
		return Result.ok(reportService.getReportList(flag,key),"PC端报表页面列表-查询成功!");
	}

	/**
	 * PC端获取大数据页面列表
	 */
	@ApiOperation(value = "大数据页面-PC端列表",notes = "大数据页面-PC端列表")
	@GetMapping("/public")
	public Result<List<ReportList>> getReportList(){
		return Result.ok(reportService.getReportList(),"PC端报表页面列表-查询成功!");
	}


	/**
	 * 分页列表查询
	 *
	 * @param reqReport 报表页面请求参数
	 * @return 返回报表页面-分页列表
	 */
	@ApiOperation(value = "报表页面-分页列表查询", notes = "报表页面-分页列表查询")
	@GetMapping
	public Result<PageResult<Report>> queryPageList(ReqReport reqReport, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(reportService.list()), "报表页面-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param report 报表页面对象
	 * @return 返回报表页面-添加结果
	 */
	@Log(title = "报表页面-添加", operateType = OperateType.INSERT)
	@ApiOperation("报表页面-添加")
	@PostMapping
	public Result<Report> add(@RequestBody Report report) {
		if (reportService.save(report)) {
			return Result.ok(report, "报表页面-添加成功!");
		}
        return Result.fail(report, "错误:报表页面-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param report 报表页面对象
	 * @return 返回报表页面-编辑结果
	 */
	@Log(title = "报表页面-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("报表页面-编辑")
	@PutMapping
	public Result<Report> edit(@RequestBody Report report) {
		if (reportService.updateById(report)) {
		    return Result.ok(report, "报表页面-编辑成功!");
		}
		return Result.fail(report, "错误:报表页面-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回报表页面-删除结果
	 */
	@Log(title = "报表页面-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("报表页面-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (reportService.removeById(id)) {
			return Result.ok(true, "报表页面-删除成功!");
		}
		return Result.fail(false, "错误:报表页面-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回报表页面-删除结果
	 */
	@Log(title = "报表页面-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("报表页面-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.reportService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "报表页面-批量删除成功!");
		}
		return Result.fail(false, "错误:报表页面-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回报表页面对象
	 */
	@ApiOperation("报表页面-通过id查询")
	@GetMapping("/{id}")
	public Result<Report> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Report report = reportService.getById(id);
		return Result.ok(report, "报表页面-查询成功!");
	}
}

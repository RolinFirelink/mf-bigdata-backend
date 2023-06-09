package com.arg.smart.web.cargo.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cargo.entity.FreightProgress;
import com.arg.smart.web.cargo.req.ReqFreightProgress;
import com.arg.smart.web.cargo.service.FreightProgressService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 货运进度表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "货运进度表")
@RestController
@RequestMapping("/freightProgress")
public class FreightProgressController {
	@Resource
	private FreightProgressService freightProgressService;

	/**
	 * 分页列表查询
	 *
	 * @param reqFreightProgress 货运进度表请求参数
	 * @return 返回货运进度表-分页列表
	 */
	@ApiOperation(value = "货运进度表-分页列表查询", notes = "货运进度表-分页列表查询")
	@GetMapping
	public Result<PageResult<FreightProgress>> queryPageList(ReqFreightProgress reqFreightProgress, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(freightProgressService.selectListByCondition(reqFreightProgress)), "货运进度表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param freightProgress 货运进度表对象
	 * @return 返回货运进度表-添加结果
	 */
	@Log(title = "货运进度表-添加", operateType = OperateType.INSERT)
	@ApiOperation("货运进度表-添加")
	@PostMapping
	public Result<FreightProgress> add(@RequestBody FreightProgress freightProgress) {
		if (freightProgressService.save(freightProgress)) {
			return Result.ok(freightProgress, "货运进度表-添加成功!");
		}
        return Result.fail(freightProgress, "错误:货运进度表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param freightProgress 货运进度表对象
	 * @return 返回货运进度表-编辑结果
	 */
	@Log(title = "货运进度表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("货运进度表-编辑")
	@PutMapping
	public Result<FreightProgress> edit(@RequestBody FreightProgress freightProgress) {
		if (freightProgressService.updateById(freightProgress)) {
		    return Result.ok(freightProgress, "货运进度表-编辑成功!");
		}
		return Result.fail(freightProgress, "错误:货运进度表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回货运进度表-删除结果
	 */
	@Log(title = "货运进度表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("货运进度表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (freightProgressService.removeById(id)) {
			return Result.ok(true, "货运进度表-删除成功!");
		}
		return Result.fail(false, "错误:货运进度表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回货运进度表-删除结果
	 */
	@Log(title = "货运进度表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("货运进度表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.freightProgressService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "货运进度表-批量删除成功!");
		}
		return Result.fail(false, "错误:货运进度表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回货运进度表对象
	 */
	@ApiOperation("货运进度表-通过id查询")
	@GetMapping("/{id}")
	public Result<FreightProgress> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		FreightProgress freightProgress = freightProgressService.getById(id);
		return Result.ok(freightProgress, "货运进度表-查询成功!");
	}
}

package com.arg.smart.sys.controller;


import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.sys.entity.Region;
import com.arg.smart.sys.req.ReqRegion;
import com.arg.smart.sys.service.RegionService;

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
 * @description: 行政区域
 * @author cgli
 * @date: 2023-05-06
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "行政区域")
@RestController
@RequestMapping("/region")
public class RegionController {
	@Resource
	private RegionService regionService;


	/**
	 * 根据归属（上级）ID查询列表
	 *
	 * @param pid 归属ID
	 * @return 返回行政区域-分页列表
	 */
	@ApiOperation(value = "行政区域-分页列表查询", notes = "行政区域-分页列表查询")
	@GetMapping("/listByPid/{pid}")
	public Result<List<Region>> listByPid(@PathVariable("pid") String pid) {
		return Result.ok(regionService.listByPid(pid), "行政区域-查询成功!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqRegion 行政区域请求参数
	 * @return 返回行政区域-分页列表
	 */
	@ApiOperation(value = "行政区域-分页列表查询", notes = "行政区域-分页列表查询")
	@GetMapping
	public Result<PageResult<Region>> queryPageList(ReqRegion reqRegion, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(regionService.listRegion(reqRegion)), "行政区域-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param region 行政区域对象
	 * @return 返回行政区域-添加结果
	 */
	@Log(title = "行政区域-添加", operateType = OperateType.INSERT)
	@ApiOperation("行政区域-添加")
	@PostMapping
	public Result<Region> add(@RequestBody Region region) {
		if (regionService.save(region)) {
			return Result.ok(region, "行政区域-添加成功!");
		}
        return Result.fail(region, "错误:行政区域-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param region 行政区域对象
	 * @return 返回行政区域-编辑结果
	 */
	@Log(title = "行政区域-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("行政区域-编辑")
	@PutMapping
	public Result<Region> edit(@RequestBody Region region) {
		if (regionService.updateById(region)) {
		    return Result.ok(region, "行政区域-编辑成功!");
		}
		return Result.fail(region, "错误:行政区域-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回行政区域-删除结果
	 */
	@Log(title = "行政区域-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("行政区域-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (regionService.removeById(id)) {
			return Result.ok(true, "行政区域-删除成功!");
		}
		return Result.fail(false, "错误:行政区域-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回行政区域-删除结果
	 */
	@Log(title = "行政区域-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("行政区域-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.regionService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "行政区域-批量删除成功!");
		}
		return Result.fail(false, "错误:行政区域-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回行政区域对象
	 */
	@ApiOperation("行政区域-通过id查询")
	@GetMapping("/{id}")
	public Result<Region> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		Region region = regionService.getById(id);
		return Result.ok(region, "行政区域-查询成功!");
	}
}

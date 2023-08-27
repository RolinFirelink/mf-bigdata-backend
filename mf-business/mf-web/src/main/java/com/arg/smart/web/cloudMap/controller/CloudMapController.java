package com.arg.smart.web.cloudMap.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cloudMap.entity.CloudMap;
import com.arg.smart.web.cloudMap.req.ReqCloudMap;
import com.arg.smart.web.cloudMap.service.CloudMapService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 云图
 * @author cgli
 * @date: 2023-08-25
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "云图")
@RestController
@RequestMapping("/cloudMap")
public class CloudMapController {
	@Resource
	private CloudMapService cloudMapService;


	/**
	 * 分页列表查询
	 *
	 * @param reqCloudMap 云图请求参数
	 * @return 返回云图-分页列表
	 */
	@ApiOperation(value = "云图-PC端查询", notes = "云图-PC端查询")
	@GetMapping("/public")
	public Result<List<CloudMap>> publicList(ReqCloudMap reqCloudMap) {
		return Result.ok(cloudMapService.publicList(), "云图-PC端查询!");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqCloudMap 云图请求参数
	 * @return 返回云图-分页列表
	 */
	@ApiOperation(value = "云图-分页列表查询", notes = "云图-分页列表查询")
	@GetMapping
	public Result<PageResult<CloudMap>> queryPageList(ReqCloudMap reqCloudMap, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(cloudMapService.list()), "云图-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param cloudMap 云图对象
	 * @return 返回云图-添加结果
	 */
	@Log(title = "云图-添加", operateType = OperateType.INSERT)
	@ApiOperation("云图-添加")
	@PostMapping
	public Result<CloudMap> add(@RequestBody CloudMap cloudMap) {
		if (cloudMapService.save(cloudMap)) {
			return Result.ok(cloudMap, "云图-添加成功!");
		}
        return Result.fail(cloudMap, "错误:云图-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param cloudMap 云图对象
	 * @return 返回云图-编辑结果
	 */
	@Log(title = "云图-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("云图-编辑")
	@PutMapping
	public Result<CloudMap> edit(@RequestBody CloudMap cloudMap) {
		if (cloudMapService.updateById(cloudMap)) {
		    return Result.ok(cloudMap, "云图-编辑成功!");
		}
		return Result.fail(cloudMap, "错误:云图-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回云图-删除结果
	 */
	@Log(title = "云图-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("云图-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (cloudMapService.removeById(id)) {
			return Result.ok(true, "云图-删除成功!");
		}
		return Result.fail(false, "错误:云图-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回云图-删除结果
	 */
	@Log(title = "云图-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("云图-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.cloudMapService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "云图-批量删除成功!");
		}
		return Result.fail(false, "错误:云图-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回云图对象
	 */
	@ApiOperation("云图-通过id查询")
	@GetMapping("/{id}")
	public Result<CloudMap> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		CloudMap cloudMap = cloudMapService.getById(id);
		return Result.ok(cloudMap, "云图-查询成功!");
	}
}

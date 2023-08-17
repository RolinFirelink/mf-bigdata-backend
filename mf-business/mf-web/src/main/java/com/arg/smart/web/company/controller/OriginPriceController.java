package com.arg.smart.web.company.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.company.entity.OriginPrice;
import com.arg.smart.web.company.req.ReqOriginPrice;
import com.arg.smart.web.company.service.OriginPriceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 产地价格
 * @author cgli
 * @date: 2023-07-10
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产地价格")
@RestController
@RequestMapping("/originPrice")
public class OriginPriceController {
	@Resource
	private OriginPriceService originPriceService;

	/**
	 * 分页列表查询
	 *
	 * @param reqOriginPrice 产地价格请求参数
	 * @return 返回产地价格-分页列表
	 */
	@ApiOperation(value = "产地价格-分页列表查询", notes = "产地价格-分页列表查询")
	@GetMapping
	public Result<PageResult<OriginPrice>> queryPageList(ReqOriginPrice reqOriginPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
		return Result.ok(originPriceService.listPage(reqOriginPrice), "产地价格-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param originPrice 产地价格对象
	 * @return 返回产地价格-添加结果
	 */
	@Log(title = "产地价格-添加", operateType = OperateType.INSERT)
	@ApiOperation("产地价格-添加")
	@PostMapping
	public Result<OriginPrice> add(@RequestBody OriginPrice originPrice) {
		if (originPriceService.save(originPrice)) {
			return Result.ok(originPrice, "产地价格-添加成功!");
		}
        return Result.fail(originPrice, "错误:产地价格-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param originPrice 产地价格对象
	 * @return 返回产地价格-编辑结果
	 */
	@Log(title = "产地价格-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产地价格-编辑")
	@PutMapping
	public Result<OriginPrice> edit(@RequestBody OriginPrice originPrice) {
		if (originPriceService.updateById(originPrice)) {
		    return Result.ok(originPrice, "产地价格-编辑成功!");
		}
		return Result.fail(originPrice, "错误:产地价格-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产地价格-删除结果
	 */
	@Log(title = "产地价格-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产地价格-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (originPriceService.removeById(id)) {
			return Result.ok(true, "产地价格-删除成功!");
		}
		return Result.fail(false, "错误:产地价格-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产地价格-删除结果
	 */
	@Log(title = "产地价格-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产地价格-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.originPriceService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产地价格-批量删除成功!");
		}
		return Result.fail(false, "错误:产地价格-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产地价格对象
	 */
	@ApiOperation("产地价格-通过id查询")
	@GetMapping("/{id}")
	public Result<OriginPrice> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		OriginPrice originPrice = originPriceService.getById(id);
		return Result.ok(originPrice, "产地价格-查询成功!");
	}
}

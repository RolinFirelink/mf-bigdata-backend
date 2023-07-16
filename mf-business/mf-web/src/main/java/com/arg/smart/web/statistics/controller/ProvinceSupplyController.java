package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.ProvinceSupply;
import com.arg.smart.web.statistics.req.ReqProvinceSupply;
import com.arg.smart.web.statistics.service.ProvinceSupplyService;
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
 * @description: 省份供应表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "省份供应表")
@RestController
@RequestMapping("/provinceSupply")
public class ProvinceSupplyController {
	@Resource
	private ProvinceSupplyService provinceSupplyService;

	/**
	 * 大屏获取各市区所供应的各品种及供应量
	 *
	 * @param reqProvinceSupply 省份供应表请求参数
	 * @return 返回各市区所供应的各品种及供应量
	 */
	@ApiOperation(value = "省份供应表-分页列表查询", notes = "省份供应表-分页列表查询")
	@GetMapping("/public")
	public Result<List<ProvinceSupply>> publicList(ReqProvinceSupply reqProvinceSupply, ReqPage reqPage) {
		return Result.ok(provinceSupplyService.list(reqProvinceSupply), "大屏获取各市区所供应的各品种及供应量");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProvinceSupply 省份供应表请求参数
	 * @return 返回省份供应表-分页列表
	 */
	@ApiOperation(value = "省份供应表-分页列表查询", notes = "省份供应表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProvinceSupply>> queryPageList(ReqProvinceSupply reqProvinceSupply, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(provinceSupplyService.listPage(reqProvinceSupply), "省份供应表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param provinceSupply 省份供应表对象
	 * @return 返回省份供应表-添加结果
	 */
	@Log(title = "省份供应表-添加", operateType = OperateType.INSERT)
	@ApiOperation("省份供应表-添加")
	@PostMapping
	public Result<ProvinceSupply> add(@RequestBody ProvinceSupply provinceSupply) {
		if (provinceSupplyService.save(provinceSupply)) {
			return Result.ok(provinceSupply, "省份供应表-添加成功!");
		}
        return Result.fail(provinceSupply, "错误:省份供应表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param provinceSupply 省份供应表对象
	 * @return 返回省份供应表-编辑结果
	 */
	@Log(title = "省份供应表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("省份供应表-编辑")
	@PutMapping
	public Result<ProvinceSupply> edit(@RequestBody ProvinceSupply provinceSupply) {
		if (provinceSupplyService.updateById(provinceSupply)) {
		    return Result.ok(provinceSupply, "省份供应表-编辑成功!");
		}
		return Result.fail(provinceSupply, "错误:省份供应表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回省份供应表-删除结果
	 */
	@Log(title = "省份供应表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("省份供应表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (provinceSupplyService.removeById(id)) {
			return Result.ok(true, "省份供应表-删除成功!");
		}
		return Result.fail(false, "错误:省份供应表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回省份供应表-删除结果
	 */
	@Log(title = "省份供应表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("省份供应表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.provinceSupplyService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "省份供应表-批量删除成功!");
		}
		return Result.fail(false, "错误:省份供应表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回省份供应表对象
	 */
	@ApiOperation("省份供应表-通过id查询")
	@GetMapping("/{id}")
	public Result<ProvinceSupply> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProvinceSupply provinceSupply = provinceSupplyService.getById(id);
		return Result.ok(provinceSupply, "省份供应表-查询成功!");
	}
}

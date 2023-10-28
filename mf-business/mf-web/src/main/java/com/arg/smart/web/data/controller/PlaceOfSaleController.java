package com.arg.smart.web.data.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.PlaceOfSale;
import com.arg.smart.web.data.entity.vo.PlaceOfSaleDataExcel;
import com.arg.smart.web.data.req.ReqPlaceOfSale;
import com.arg.smart.web.data.service.PlaceOfSaleService;
import com.arg.smart.web.data.utils.PlaceOfSaleDataListener;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

/**
 * @description: 销售地表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "销售地表")
@RestController
@RequestMapping("/placeOfSale")
public class PlaceOfSaleController {
	@Resource
	private PlaceOfSaleService placeOfSaleService;

	/**
	 * 分页列表查询
	 *
	 * @param reqPlaceOfSale 销售地表请求参数
	 * @return 返回销售地表-分页列表
	 */
	@ApiOperation(value = "销售地表-分页列表查询", notes = "销售地表-分页列表查询")
	@GetMapping
	public Result<PageResult<PlaceOfSale>> queryPageList(ReqPlaceOfSale reqPlaceOfSale, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(placeOfSaleService.pagelist(reqPlaceOfSale)), "销售地表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param placeOfSale 销售地表对象
	 * @return 返回销售地表-添加结果
	 */
	@Log(title = "销售地表-添加", operateType = OperateType.INSERT)
	@ApiOperation("销售地表-添加")
	@PostMapping
	public Result<PlaceOfSale> add(@RequestBody PlaceOfSale placeOfSale) {
		if (placeOfSaleService.savePlaceOfSale(placeOfSale)) {
			return Result.ok(placeOfSale, "销售地表-添加成功!");
		}
        return Result.fail(placeOfSale, "错误:销售地表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param placeOfSale 销售地表对象
	 * @return 返回销售地表-编辑结果
	 */
	@Log(title = "销售地表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("销售地表-编辑")
	@PutMapping
	public Result<PlaceOfSale> edit(@RequestBody PlaceOfSale placeOfSale) {
		if (placeOfSaleService.updatePlaceOfSaleById(placeOfSale)) {
		    return Result.ok(placeOfSale, "销售地表-编辑成功!");
		}
		return Result.fail(placeOfSale, "错误:销售地表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回销售地表-删除结果
	 */
	@Log(title = "销售地表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("销售地表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (placeOfSaleService.removeById(id)) {
			return Result.ok(true, "销售地表-删除成功!");
		}
		return Result.fail(false, "错误:销售地表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回销售地表-删除结果
	 */
	@Log(title = "销售地表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("销售地表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.placeOfSaleService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "销售地表-批量删除成功!");
		}
		return Result.fail(false, "错误:销售地表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回销售地表对象
	 */
	@ApiOperation("销售地表-通过id查询")
	@GetMapping("/{id}")
	public Result<PlaceOfSale> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		PlaceOfSale placeOfSale = placeOfSaleService.getById(id);
		return Result.ok(placeOfSale, "销售地表-查询成功!");
	}

	/**
	 * 产品销售地表
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "产品销售地表-Excel导入", notes = "产品销售地表-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), PlaceOfSaleDataExcel.class, new PlaceOfSaleDataListener(placeOfSaleService)).sheet().doRead();
		return Result.ok(true, "上传数据成功");
	}
}

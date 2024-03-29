package com.arg.smart.web.company.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.company.entity.ProduceInfo;
import com.arg.smart.web.company.entity.vo.ProduceInfoExcel;
import com.arg.smart.web.company.req.ReqProduceInfo;
import com.arg.smart.web.company.service.ProduceInfoService;
import com.arg.smart.web.company.uitls.ProduceInfoDataListener;
import com.arg.smart.web.company.vo.ProductDataVO;
import com.beust.jcommander.converters.BigDecimalConverter;
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
import java.util.List;
import java.util.Map;

/**
 * @description: 企业生产信息表
 * @author cgli
 * @date: 2023-07-11
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "企业生产信息表")
@RestController
@RequestMapping("/produceInfo")
public class ProduceInfoController {
	@Resource
	private ProduceInfoService produceInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param reqProduceInfo 企业生产信息表请求参数
	 * @return 返回企业生产信息表-分页列表
	 */
	@ApiOperation(value = "企业生产信息表-分页列表查询", notes = "企业生产信息表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProduceInfo>> queryPageList(ReqProduceInfo reqProduceInfo, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(produceInfoService.list(reqProduceInfo), "企业生产信息表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param produceInfo 企业生产信息表对象
	 * @return 返回企业生产信息表-添加结果
	 */
	@Log(title = "企业生产信息表-添加", operateType = OperateType.INSERT)
	@ApiOperation("企业生产信息表-添加")
	@PostMapping
	public Result<ProduceInfo> add(@RequestBody ProduceInfo produceInfo) {
		if (produceInfoService.save(produceInfo)) {
			return Result.ok(produceInfo, "企业生产信息表-添加成功!");
		}
        return Result.fail(produceInfo, "错误:企业生产信息表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param produceInfo 企业生产信息表对象
	 * @return 返回企业生产信息表-编辑结果
	 */
	@Log(title = "企业生产信息表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("企业生产信息表-编辑")
	@PutMapping
	public Result<ProduceInfo> edit(@RequestBody ProduceInfo produceInfo) {
		if (produceInfoService.updateById(produceInfo)) {
		    return Result.ok(produceInfo, "企业生产信息表-编辑成功!");
		}
		return Result.fail(produceInfo, "错误:企业生产信息表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回企业生产信息表-删除结果
	 */
	@Log(title = "企业生产信息表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("企业生产信息表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (produceInfoService.removeById(id)) {
			return Result.ok(true, "企业生产信息表-删除成功!");
		}
		return Result.fail(false, "错误:企业生产信息表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回企业生产信息表-删除结果
	 */
	@Log(title = "企业生产信息表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("企业生产信息表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.produceInfoService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "企业生产信息表-批量删除成功!");
		}
		return Result.fail(false, "错误:企业生产信息表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回企业生产信息表对象
	 */
	@ApiOperation("企业生产信息表-通过id查询")
	@GetMapping("/{id}")
	public Result<ProduceInfo> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProduceInfo produceInfo = produceInfoService.getById(id);
		return Result.ok(produceInfo, "企业生产信息表-查询成功!");
	}

 /**
  * 查询各个城市品种的生产面积
 * @param flag 产品类型
 * @param productNames 主要品种列表或其他或总计
 * @return 返回包含城市和生产面积信息的映射
 */
	@ApiOperation("企业生产信息表-通过flag查询各个城市品种的生产面积")
	@GetMapping("/public/product/{flag}")
	public Result<Map<String, List<ProductDataVO>>> getCxForCity(
			@RequestParam("flag") Integer flag,
			@ApiParam(name = "productNames", value = "主要品种") @RequestBody String...productNames) {
		Map<String, List<ProductDataVO>>result = produceInfoService.getCXForCity(flag, productNames);
		return Result.ok(result, "查询成功");
	}

	/**
	 * 企业生产信息表-Excel导入
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "企业生产信息表-Excel导入",notes = "企业生产信息表-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), ProduceInfoExcel.class, new ProduceInfoDataListener(produceInfoService)).sheet().doRead();
		return Result.ok(true,"上传数据成功");
	}
}

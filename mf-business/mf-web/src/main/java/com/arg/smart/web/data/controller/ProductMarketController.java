package com.arg.smart.web.data.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.data.entity.ProductMarket;
import com.arg.smart.web.data.entity.vo.ProductMarketExcel;
import com.arg.smart.web.data.req.ReqProductMarket;
import com.arg.smart.web.data.service.ProductMarketService;
import com.arg.smart.web.data.utils.ProductMarketDataListener;
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
 * @description: 批发市场表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "批发市场表")
@RestController
@RequestMapping("/productMarket")
public class ProductMarketController {
	@Resource
	private ProductMarketService productMarketService;

	/**
	 * 分页列表查询
	 *
	 * @param reqProductMarket 批发市场表请求参数
	 * @return 返回批发市场表-分页列表
	 */
	@ApiOperation(value = "批发市场表-分页列表查询", notes = "批发市场表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductMarket>> queryPageList(ReqProductMarket reqProductMarket, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productMarketService.pageList(reqProductMarket)), "批发市场表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productMarket 批发市场表对象
	 * @return 返回批发市场表-添加结果
	 */
	@Log(title = "批发市场表-添加", operateType = OperateType.INSERT)
	@ApiOperation("批发市场表-添加")
	@PostMapping
	public Result<ProductMarket> add(@RequestBody ProductMarket productMarket) {
		if (productMarketService.saveProductMarket(productMarket)) {
			return Result.ok(productMarket, "批发市场表-添加成功!");
		}
        return Result.fail(productMarket, "错误:批发市场表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productMarket 批发市场表对象
	 * @return 返回批发市场表-编辑结果
	 */
	@Log(title = "批发市场表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("批发市场表-编辑")
	@PutMapping
	public Result<ProductMarket> edit(@RequestBody ProductMarket productMarket) {
		if (productMarketService.updateProductMarketById(productMarket)) {
		    return Result.ok(productMarket, "批发市场表-编辑成功!");
		}
		return Result.fail(productMarket, "错误:批发市场表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回批发市场表-删除结果
	 */
	@Log(title = "批发市场表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("批发市场表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productMarketService.removeById(id)) {
			return Result.ok(true, "批发市场表-删除成功!");
		}
		return Result.fail(false, "错误:批发市场表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回批发市场表-删除结果
	 */
	@Log(title = "批发市场表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("批发市场表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productMarketService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "批发市场表-批量删除成功!");
		}
		return Result.fail(false, "错误:批发市场表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回批发市场表对象
	 */
	@ApiOperation("批发市场表-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductMarket> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductMarket productMarket = productMarketService.getById(id);
		return Result.ok(productMarket, "批发市场表-查询成功!");
	}

	/**
	 * 产品市场表
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "产品市场表-Excel导入", notes = "产品市场表-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), ProductMarketExcel.class, new ProductMarketDataListener(productMarketService)).sheet().doRead();
		return Result.ok(true, "上传数据成功");
	}
}

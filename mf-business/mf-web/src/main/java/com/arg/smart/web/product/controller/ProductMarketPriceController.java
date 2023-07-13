package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.ProductMarketPrice;
import com.arg.smart.web.product.req.ReqProductMarketPrice;
import com.arg.smart.web.product.service.ProductMarketPriceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description: 产品批发价格表
 * @author cgli
 * @date: 2023-07-08
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品批发价格表")
@RestController
@RequestMapping("/productMarketPrice")
public class ProductMarketPriceController {
	@Resource
	private ProductMarketPriceService productMarketPriceService;

	/**
	 * 爬虫添加
	 *
	 * @return 返回爬虫添加结果
	 */
	@Log(title = "惠农网信息爬虫添加", operateType = OperateType.INSERT)
	@ApiOperation("惠农网信息爬虫添加")
	@PostMapping("/cnhnbAdd")
	public Result<String> cnhnbAdd() {
		// TODO 暂时使用该接口要求在本地有D:\pachong\new\chromedriver.exe文件且版本必须适配
		if (productMarketPriceService.cnhnbSave()) {
			return Result.ok("爬虫添加成功");
		}
		return Result.fail("爬虫添加失败");
	}

	/**
	 * 爬虫添加
	 *
	 * @return 返回爬虫添加结果
	 */
	@Log(title = "农产品商务信息反序列化添加", operateType = OperateType.INSERT)
	@ApiOperation("农产品商务信息反序列化添加")
	@PostMapping("/jsonAdd")
	public Result<String> jsonAdd(@RequestParam("file") MultipartFile file) {
		// TODO 调用该方法前需要手动将LogAspect中的AOP注解注释掉
		if (productMarketPriceService.jsonAdd(file)) {
			return Result.ok("反序列化添加成功");
		}
		return Result.fail("反序列化添加失败");
	}

	/**
	 * 爬虫添加
	 *
	 * @return 返回爬虫添加结果
	 */
	@Log(title = "农产品商务信息爬虫添加", operateType = OperateType.INSERT)
	@ApiOperation("农产品商务信息爬虫添加")
	@PostMapping("/mofcomAdd")
	public Result<String> mofcomAdd() {
		// TODO 暂时使用该接口要求在本地有D:\pachong\new\chromedriver.exe文件且版本必须适配
		if (productMarketPriceService.mofcomSave()) {
			return Result.ok("爬虫添加成功");
		}
		return Result.fail("爬虫添加失败");
	}

	/**
	 * 爬虫添加
	 *
	 * @return 返回爬虫添加结果
	 */
	@Log(title = "食品商务网爬虫添加", operateType = OperateType.INSERT)
	@ApiOperation("食品商务网爬虫添加")
	@PostMapping("/21foodAdd")
	public Result<String> foodAdd() {
		if (productMarketPriceService.foodSave()) {
			return Result.ok("爬虫添加成功");
		}
		return Result.fail("爬虫添加失败");
	}

	/**
	 * 爬虫添加
	 *
	 * @return 返回爬虫添加结果
	 */
	@Log(title = "农情网爬虫添加", operateType = OperateType.INSERT)
	@ApiOperation("农情网爬虫添加")
	@PostMapping("/nongQingAdd")
	public Result<String> nongQingAdd() {
		if (productMarketPriceService.nongQingSave()) {
			return Result.ok("爬虫添加成功");
		}
		return Result.fail("爬虫添加失败");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProductMarketPrice 产品批发价格表请求参数
	 * @return 返回产品批发价格表-分页列表
	 */
	@ApiOperation(value = "产品批发价格表-分页列表查询", notes = "产品批发价格表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductMarketPrice>> queryPageList(ReqProductMarketPrice reqProductMarketPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productMarketPriceService.list()), "产品批发价格表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productMarketPrice 产品批发价格表对象
	 * @return 返回产品批发价格表-添加结果
	 */
	@Log(title = "产品批发价格表-添加", operateType = OperateType.INSERT)
	@ApiOperation("产品批发价格表-添加")
	@PostMapping
	public Result<ProductMarketPrice> add(@RequestBody ProductMarketPrice productMarketPrice) {
		if (productMarketPriceService.save(productMarketPrice)) {
			return Result.ok(productMarketPrice, "产品批发价格表-添加成功!");
		}
        return Result.fail(productMarketPrice, "错误:产品批发价格表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productMarketPrice 产品批发价格表对象
	 * @return 返回产品批发价格表-编辑结果
	 */
	@Log(title = "产品批发价格表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("产品批发价格表-编辑")
	@PutMapping
	public Result<ProductMarketPrice> edit(@RequestBody ProductMarketPrice productMarketPrice) {
		if (productMarketPriceService.updateById(productMarketPrice)) {
		    return Result.ok(productMarketPrice, "产品批发价格表-编辑成功!");
		}
		return Result.fail(productMarketPrice, "错误:产品批发价格表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回产品批发价格表-删除结果
	 */
	@Log(title = "产品批发价格表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("产品批发价格表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productMarketPriceService.removeById(id)) {
			return Result.ok(true, "产品批发价格表-删除成功!");
		}
		return Result.fail(false, "错误:产品批发价格表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回产品批发价格表-删除结果
	 */
	@Log(title = "产品批发价格表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("产品批发价格表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productMarketPriceService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "产品批发价格表-批量删除成功!");
		}
		return Result.fail(false, "错误:产品批发价格表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回产品批发价格表对象
	 */
	@ApiOperation("产品批发价格表-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductMarketPrice> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductMarketPrice productMarketPrice = productMarketPriceService.getById(id);
		return Result.ok(productMarketPrice, "产品批发价格表-查询成功!");
	}
}
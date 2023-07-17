package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品价格表")
@RestController
@RequestMapping("/productPrice")
public class ProductPriceController {
	@Resource
	private ProductPriceService productPriceService;

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
		if (productPriceService.cnhnbSave()) {
			return Result.ok("爬虫添加成功");
		}
		return Result.fail("爬虫添加失败");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProductPrice 产品价格表请求参数
	 * @return 返回产品价格表-分页列表
	 */
	@ApiOperation(value = "产品价格表-分页列表查询", notes = "产品价格表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductPrice>> queryPageList(ReqProductPrice reqProductPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(new PageResult<>(productPriceService.queryList(reqProductPrice)), "产品价格表-查询成功!");
	}
}

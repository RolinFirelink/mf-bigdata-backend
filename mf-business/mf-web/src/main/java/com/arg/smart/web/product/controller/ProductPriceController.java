package com.arg.smart.web.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.report.PriceData;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cgli
 * @description: 产品价格表
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
     * PC端-分页列表查询
     *
     * @param reqProductPrice 产品价格表请求参数
     * @return 返回产品价格表-分页列表
     */
    @ApiOperation(value = "产品价格表-分页列表查询", notes = "产品价格表-分页列表查询")
    @GetMapping("/public")
    public Result<PageResult<ProductPrice>> queryPageList(ReqProductPrice reqProductPrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(productPriceService.queryList(reqProductPrice)), "产品价格表-查询成功!");
    }

    /**
     * 大屏-列表查询
     *
     * @param reqProductPrice 产品价格表请求参数
     * @return 返回产品价格表-大屏列表
     */
    @ApiOperation(value = "产品价格表-大屏列表查询", notes = "产品价格表-大屏列表查询")
    @GetMapping("/public/list")
    public Result<List<ProductPrice>> queryList(ReqProductPrice reqProductPrice) {
        return Result.ok(productPriceService.queryList(reqProductPrice), "产品价格表-查询成功!");
    }


    /**
     * PC端-获取产品价格报表数据(趋势图数据)
     *
     * @param reqProductPrice 产品
     * @return 返回产品报表数据
     */
    @ApiOperation(value = "PC端-获取产品价格报表数据", notes = "PC端-获取产品价格报表数据")
    @GetMapping("/public/trend")
    public Result<List<PriceData>> getPriceReportData(ReqProductPrice reqProductPrice) {
        return Result.ok(productPriceService.getPriceReportData(reqProductPrice), "PC端-获取产品价格报表数据-查询成功!");
    }
	@ApiOperation(value = "产品价格表-地区行情走势按月", notes = "产品价格表-地区行情走势按月")
	@GetMapping("/public/selectAvgPriceOfDate")
	public Result<Map<Integer,Map<String, BigDecimal>>> selectAvgPriceOfDate(
			@RequestParam(required = false)
			LocalDate startTime,
			@RequestParam(required = false)
			LocalDate endTime
	){
		if(endTime == null){
			endTime = LocalDate.now();
		}
		if(startTime == null){
			startTime = endTime.minusDays(30);
		}
		Map<Integer,Map<String, BigDecimal>> res = new HashMap<>();
		List<AvgPriceVO> prices = productPriceService.selectAvgPriceOfDate(startTime, endTime);
		for (AvgPriceVO p : prices) {
			Integer flag = p.getFlag();
			String time = p.getTime();
			BigDecimal price = p.getPrice();
			Map<String, BigDecimal> priceMap;
			if(res.containsKey(flag)){
				priceMap = res.get(flag);
			}else{
				priceMap = new HashMap<>();
				res.put(flag, priceMap);
			}
			priceMap.put(time, price);
		}
		return Result.ok(res);
	}

	@ApiOperation(value = "产品价格表-地区行情走势按季", notes = "产品价格表-地区行情走势按季")
	@GetMapping("/public/selectAvgPriceOfMonth")
	public Result<Map<Integer,Map<String, BigDecimal>>> selectAvgPriceOfMonth(
			@RequestParam(required = false)
			LocalDate startTime,
			@RequestParam(required = false)
			LocalDate endTime
	){
		if(endTime == null){
			endTime = LocalDate.now();
		}
		if(startTime == null){
			startTime = endTime.minusMonths(12);//近12个月跨度
		}
		Map<Integer,Map<String, BigDecimal>> res = new HashMap<>();
		List<AvgPriceVO> prices = productPriceService.selectAvgPriceOfMonth(startTime, endTime);

		for (AvgPriceVO p : prices) {
			Integer flag = p.getFlag();
			String time = p.getTime();
			BigDecimal price = p.getPrice();
			Map<String, BigDecimal> priceMap;
			if(res.containsKey(flag)){
				priceMap = res.get(flag);
			}else{
				priceMap = new HashMap<>();
				res.put(flag, priceMap);
			}
			priceMap.put(time, price);
		}
		return Result.ok(res);
	}
}

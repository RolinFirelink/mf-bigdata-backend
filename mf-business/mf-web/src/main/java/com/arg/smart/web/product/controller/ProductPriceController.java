package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.arg.smart.web.product.entity.vo.PriceTemp;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceMonthService;
import com.arg.smart.web.product.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
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

    @Resource
    private ProductPriceMonthService productPriceMonthService;

    /**
     * PC端——今天价格指数查询
     */
    @ApiOperation(value = "PC端——今天价格指数查询",notes="PC端今日价格指数查询")
    @GetMapping("/public/temp")
    public Result<List<PriceTemp>> getPriceTemp (){
       return Result.ok(productPriceService.getPriceTemp());
    }

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


    @ApiOperation(value = "产品价格表-地区行情走势按月", notes = "产品价格表-地区行情走势按月")
    @GetMapping("/public/selectAvgPriceOfDate")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfDate(
            @RequestParam(required = false)
                    LocalDate startTime,
            @RequestParam(required = false)
                    LocalDate endTime
    ) {
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusDays(30);
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfDate(startTime, endTime);
        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }

    @ApiOperation(value = "产品价格表-地区行情走势按季", notes = "产品价格表-地区行情走势按季")
    @GetMapping("/public/selectAvgPriceOfMonth")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfMonth(
            @RequestParam(required = false)
                    LocalDate startTime,
            @RequestParam(required = false)
                    LocalDate endTime
    ) {
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusMonths(12);//近12个月跨度
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfMonth(startTime, endTime);

        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }

    @ApiOperation(value = "产品价格表-地区行情走势按月", notes = "产品价格表-地区行情走势按季")
    @GetMapping("/public/selectAvgPriceOfHalfYear")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfHalfYear(
            @RequestParam(required = false)
                    LocalDate startTime,
            @RequestParam(required = false)
                    LocalDate endTime
    ) {
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusMonths(6);//近12个月跨度
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfHalfYear(startTime, endTime);

        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }

    @ApiOperation(value = "产品价格表-地区行情走势按月", notes = "产品价格表-地区行情走势按季")
    @GetMapping("/public/selectAvgPriceOfYear")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfYear(
            @RequestParam(required = false)
                    LocalDate startTime,
            @RequestParam(required = false)
                    LocalDate endTime
    ) {
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusMonths(6);//近12个月跨度
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfYear(startTime, endTime);
        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }
}

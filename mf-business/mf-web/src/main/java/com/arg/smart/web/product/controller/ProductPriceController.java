package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.vo.ProductPrice;
import com.arg.smart.web.product.entity.report.PriceData;
import com.arg.smart.web.product.entity.vo.ProductSupply;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author cgli
 * @description: 产品价格表
 * @date: 2023-07-01
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.vo.ProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;


/**
 * @description: 产品价格表
 * @author HanYuehong
 * @date: 2023-07-12
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品价格表")
@RestController
@RequestMapping("/productPrice")
public class ProductPriceController {
    @Resource
    private ProductPriceService productPriceService;


    /*@ApiOperation(value = "产品价格表-价格趋势查询", notes = "产品价格表-价格趋势查询")
    @GetMapping("/selectAverageByDate/{flag}/{startTime}/{endTime}")
    public Result<List<com.arg.smart.web.product.entity.ProductPrice>> selectAverageByDate(@ApiParam(name = "flag", value = "产品类别") @PathVariable Integer flag, @ApiParam(name = "startTime", value = "开始日期(可为空)") @RequestParam(required = false) @PathVariable LocalDate startTime, @ApiParam(name = "endTime", value = "结束时间(可为空)") @RequestParam(required = false) @PathVariable LocalDate endTime) {
        if (startTime == null || endTime == null) {
            endTime = LocalDate.now();
            startTime = endTime.minusDays(15);
        }
        List<com.arg.smart.web.product.entity.ProductPrice> list = productPriceService.selectPriceByDate(flag, startTime, endTime);
        return Result.ok(list, "查询成功");
    }*/
        /**
         * PC端-分页列表查询
         *
         * @param reqProductPrice 产品价格表请求参数
         * @return 返回产品价格表-分页列表
         */
        /*@ApiOperation(value = "产品价格表-分页列表查询", notes = "产品价格表-分页列表查询")
        @GetMapping("/public")
        public Result<PageResult<ProductPrice>> queryPageList (ReqProductPrice reqProductPrice, ReqPage reqPage){
            PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
            return Result.ok(new PageResult<>(productPriceService.queryList(reqProductPrice)), "产品价格表-查询成功!");
        }*/

        /**
         * 大屏-列表查询
         *
         * @param reqProductPrice 产品价格表请求参数
         * @return 返回产品价格表-大屏列表
         */
        /*@ApiOperation(value = "产品价格表-大屏列表查询", notes = "产品价格表-大屏列表查询")
        @GetMapping("/public/list")
        public Result<List<ProductPrice>> queryList (ReqProductPrice reqProductPrice){
            return Result.ok(productPriceService.queryList(reqProductPrice), "产品价格表-查询成功!");
        }*/


        /**
         * PC端-获取产品价格报表数据(趋势图数据)
         *
         * @param reqProductPrice 产品
         * @return 返回产品报表数据
         */
       /* @ApiOperation(value = "PC端-获取产品价格报表数据", notes = "PC端-获取产品价格报表数据")
        @GetMapping("/public/trend")
        public Result<List<PriceData>> getPriceReportData (ReqProductPrice reqProductPrice){
            return Result.ok(productPriceService.getPriceReportData(reqProductPrice), "PC端-获取产品价格报表数据-查询成功!");
        }*/
       @ApiModelProperty("提供产品供需求量")
       @GetMapping("public/GetProductSupply/{flag}")
        public Result<List<ProductSupply>> getProductSupply(@ApiParam(name = "flag", value = "产品类别")@PathVariable Integer flag){
           return Result.ok(productPriceService.selectSupplyByFlag(flag));
        }
    }


package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.data.entity.CompanyProduct;
import com.arg.smart.web.data.entity.CompanyRevenue;
import com.arg.smart.web.data.entity.ProductMarketSize;
import com.arg.smart.web.data.entity.vo.AvgProductValue;
import com.arg.smart.web.data.service.CompanyProductService;
import com.arg.smart.web.data.service.CompanyRevenueService;
import com.arg.smart.web.data.service.ProductMarketSizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Api(tags = "预制菜菜品")
@RestController
@RequestMapping("/CompanyProduct")
public class CompanyProductController {
    @Resource
    private CompanyProductService companyProductService;
    @Resource
    private ProductMarketSizeService productMarketSizeService;
    @Resource
    private CompanyRevenueService companyRevenueService;

    @GetMapping("/list")
    public Result<List<CompanyProduct>> queryCompanyProductList(){
        return Result.ok(companyProductService.list(),"预制菜菜品查询成功！");
    }

    @GetMapping("/selectAvgValue/{productName}")
    public Result<List<AvgProductValue>> queryAvgProductValue(@ApiParam(name = "productName", value = "产品名称") @PathVariable String productName){
        return Result.ok(companyProductService.companyProductValue(productName),"预制菜菜品查询成功！");
    }

    @GetMapping("/ProductMarketSize")
    public Result<List<ProductMarketSize>> queryProductMarketSize(){
        return Result.ok(productMarketSizeService.list(),"预制菜市场规模查询成功");
    }

    @GetMapping("/CompanyRevenue/{year}")
    public Result<List<CompanyRevenue>> queryCompanyRevenue(@ApiParam(name = "year", value = "年") @PathVariable Integer year){

        return Result.ok(companyRevenueService.selectCompanyRevenue(year),"查询公司年营收成功！");
    }
}

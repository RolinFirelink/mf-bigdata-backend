package com.arg.smart.web.company.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.entity.vo.CompanyExcel;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.req.ReqProductInfo;
import com.arg.smart.web.company.service.CompanyService;
import com.arg.smart.web.company.service.ProductInfoService;
import com.arg.smart.web.company.uitls.CompanyDataListener;
import com.arg.smart.web.company.vo.BaseVO;
import com.arg.smart.web.company.vo.ProductVo;
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

/**
 * @description: 企业信息
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品企业信息")
@RestController
@RequestMapping("/productinfo")
public class ProductInfoController {
    @Resource
    private ProductInfoService productInfoService;
    /**
     * PC端-产品企业和经纬度信息-查询产品企业和经纬度信息

     * @return
     */
    @ApiOperation(value = "产品企业和经纬度信息-查询产品企业信息", notes = "产品企业和经纬度信息-查询产品企业信息")
    @GetMapping("/public/count-by-city-and-flag/{flag}/{city}")
    public List<ProductVo>  getProductInfoarea(@PathVariable("flag") Integer flag,@PathVariable("city") String city) {
        return  productInfoService.getProductInfoarea(flag,city);
    }
}

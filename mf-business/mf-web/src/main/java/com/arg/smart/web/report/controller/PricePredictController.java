package com.arg.smart.web.report.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.report.service.PricePredictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author GlowingTree
 * @Description 农产品价格预测报告
 * @Date 09/01/2023 3:27 PM
 * @Version 1.0
 */
@Slf4j
@Api(tags = "农产品价格预测报告")
@RestController
@RequestMapping("/priceReport")
public class PricePredictController {

    @Resource
    private PricePredictService pricePredictService;

    @Log(title = "农产品价格预测报告-获取当天价格预测报告", operateType = OperateType.INSERT)
    @ApiOperation("农产品价格预测报告-获取当天价格预测报告")
    @GetMapping
    public String getPricePredict(@RequestParam("flag") Integer flag) {
        return pricePredictService.getPriceDescription(flag);
    }

}

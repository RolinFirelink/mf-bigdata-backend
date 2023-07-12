package com.arg.smart.web.product.controller;

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


    @ApiOperation(value = "产品价格表-价格趋势查询", notes = "产品价格表-价格趋势查询")
    @GetMapping("/selectAverageByDate/{flag}/{startTime}/{endTime}")
    public Result<List<ProductPrice>> selectAverageByDate (@ApiParam(name = "flag",value = "产品类别")@PathVariable Integer flag, @ApiParam(name = "startTime",value = "开始日期(可为空)") @RequestParam(required = false) @PathVariable LocalDate startTime,@ApiParam(name = "endTime",value = "结束时间(可为空)") @RequestParam(required = false) @PathVariable LocalDate endTime){
        if(startTime==null || endTime==null){
            endTime=LocalDate.now();
            startTime=endTime.minusDays(15);
        }
        List<ProductPrice> list = productPriceService.selectPriceByDate(flag,startTime,endTime);
        return  Result.ok(list,"查询成功");
    }
}

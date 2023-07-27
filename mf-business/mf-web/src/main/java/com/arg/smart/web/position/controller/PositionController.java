package com.arg.smart.web.position.controller;


import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.position.entity.PositionData;
import com.arg.smart.web.position.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Api(tags = "产销地分布")
@RestController
@RequestMapping("/position")
public class PositionController {
    @Resource
    private PositionService positionService;

    @ApiOperation("统计各品种的产地分布")
    @GetMapping("/public/positionDistribution")
    public Result<List<PositionData>> positionDistribution(@ApiParam(name = "flag", value = "产品区分字段") Integer flag) {
        List<PositionData> list = positionService.statisticalDistribution(flag);
        list.addAll(positionService.statisticalMarketDistribution(flag));
        list.addAll(positionService.statisticalSalePlace(flag));
        return Result.ok(list, "统计各品种的产销地分布-查询成功！");
    }
}

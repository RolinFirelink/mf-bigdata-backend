package com.arg.smart.web.customer.controller;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.customer.entity.HotWord;
import com.arg.smart.web.customer.req.ReqHotWord;
import com.arg.smart.web.customer.service.HotWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 热词表
 * @author zsj
 * @date: 2023-06-29
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "热词表")
@RestController
@RequestMapping("/hotWord")
public class HotWordController {

    @Resource
    private HotWordService hotWordService;

    /**
     * 热词列表查询
     * @param type 0表示查全部 1表示只查询负面
     */
    @ApiOperation(value = "热词列表查询",notes = "热词列表查询")
    @GetMapping("/public/list/{type}")
    public Result<List<HotWord>> publicList(@PathVariable("type") Integer type, ReqHotWord reqHotWord){
        return Result.ok(hotWordService.publicList(type,reqHotWord),"热词列表查询");
    }
}

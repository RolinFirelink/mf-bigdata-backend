package com.arg.smart.web.docking.controller;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.docking.req.ReqPresentations;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 线上推介会
 * @author zsj
 * @date: 2023-09-19
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "线上推介会")
@RestController
@RequestMapping("/presentations")
public class PresentationsController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/public")
    public Result<Object> getPresentations(ReqPresentations reqPresentations){
        Integer page = reqPresentations.getPage();
        if(page == null){
            page = 1;
        }
        String apiUrl = "https://show.12221.com.cn/addons/rwexhibition/api.Index/exIndex?page="+page;
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(apiUrl, JSONObject.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject body = response.getBody();
            Map data = (Map) body.get("data");
            return Result.ok(data,"获取线上推介会数据成功");
        } else {
            throw new RuntimeException("获取线上推介会数据失败");
        }
    }
}

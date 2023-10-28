package com.arg.smart.web.miniProgram.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.miniProgram.entity.RotationChart;
import com.arg.smart.web.miniProgram.req.ReqRotationChart;
import com.arg.smart.web.miniProgram.service.RotationChartService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author cgli
 * @description: 轮播图信息
 * @date: 2023-06-02
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "轮播图信息")
@RestController
@RequestMapping("/rotationChart")
public class RotationChartController {
    @Resource
    private RotationChartService rotationChartService;

    /**
     * 分页列表查询
     *
     * @param reqRotationChart 轮播图信息请求参数
     * @return 返回轮播图信息-分页列表
     */
    @ApiOperation(value = "轮播图信息-分页列表查询", notes = "轮播图信息-分页列表查询")
    @GetMapping
    public Result<PageResult<RotationChart>> queryPageList(ReqRotationChart reqRotationChart, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(rotationChartService.list(reqRotationChart)), "轮播图信息-查询成功!");
    }

    /**
     * 添加
     *
     * @param rotationChart 轮播图信息对象
     * @return 返回轮播图信息-添加结果
     */
    @Log(title = "轮播图信息-添加", operateType = OperateType.INSERT)
    @ApiOperation("轮播图信息-添加")
    @PostMapping
    public Result<RotationChart> add(@RequestBody RotationChart rotationChart) {
        if (rotationChartService.save(rotationChart)) {
            return Result.ok(rotationChart, "轮播图信息-添加成功!");
        }
        return Result.fail(rotationChart, "错误:轮播图信息-添加失败!");
    }

    /**
     * 编辑
     *
     * @param rotationChart 轮播图信息对象
     * @return 返回轮播图信息-编辑结果
     */
    @Log(title = "轮播图信息-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("轮播图信息-编辑")
    @PutMapping
    public Result<RotationChart> edit(@RequestBody RotationChart rotationChart) {
        if (rotationChartService.updateById(rotationChart)) {
            return Result.ok(rotationChart, "轮播图信息-编辑成功!");
        }
        return Result.fail(rotationChart, "错误:轮播图信息-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回轮播图信息-删除结果
     */
    @Log(title = "轮播图信息-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("轮播图信息-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (rotationChartService.removeById(id)) {
            return Result.ok(true, "轮播图信息-删除成功!");
        }
        return Result.fail(false, "错误:轮播图信息-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回轮播图信息-删除结果
     */
    @Log(title = "轮播图信息-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("轮播图信息-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.rotationChartService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "轮播图信息-批量删除成功!");
        }
        return Result.fail(false, "错误:轮播图信息-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回轮播图信息对象
     */
    @ApiOperation("轮播图信息-通过id查询")
    @GetMapping("/{id}")
    public Result<RotationChart> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        RotationChart rotationChart = rotationChartService.getById(id);
        return Result.ok(rotationChart, "轮播图信息-查询成功!");
    }

    @ApiOperation("轮播图信息-通过sort查询")
    @GetMapping("/public/getSwiperList")
    public Result<List<RotationChart>> getSwiperList(ReqRotationChart reqRotationChart){
        List<RotationChart> rotationChart = rotationChartService.getSwiperList(reqRotationChart);
        return Result.ok(rotationChart);
    }

}

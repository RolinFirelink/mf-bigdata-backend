package com.arg.smart.web.report.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.report.entity.GoodAnalyse;
import com.arg.smart.web.report.req.ReqGoodAnalyse;
import com.arg.smart.web.report.service.GoodAnalyseService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.URLEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author cgli
 * @description: 农产品竞品分析报告表
 * @date: 2023-08-27
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "农产品竞品分析报告表")
@RestController
@RequestMapping("/goodAnalyse")
public class GoodAnalyseController {

    @Resource
    private GoodAnalyseService goodAnalyseService;

    /**
     * 分页列表查询
     *
     * @param reqGoodAnalyse 农产品竞品分析报告请求参数
     * @return 返回农产品竞品分析报告-分页列表
     */
    @ApiOperation(value = "农产品竞品分析报告-分页列表查询", notes = "农产品竞品分析报告-分页列表查询")
    @GetMapping
    public Result<PageResult<GoodAnalyse>> queryPageList(ReqGoodAnalyse reqGoodAnalyse, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(goodAnalyseService.list(reqGoodAnalyse), "农产品竞品分析报告-查询成功!");
    }

    /**
     * 添加
     *
     * @param goodAnalyse 农产品农产品竞品分析报告对象
     * @return 返回农产品竞品分析报告-添加结果
     */
    @Log(title = "农产品竞品分析报告-添加", operateType = OperateType.INSERT)
    @ApiOperation("农产品竞品分析报告-添加")
    @PostMapping
    public Result<GoodAnalyse> add(@RequestBody GoodAnalyse goodAnalyse) {
        if (goodAnalyseService.save(goodAnalyse)) {
            return Result.ok(goodAnalyse, "农产品竞品分析报告-添加成功!");
        }
        return Result.fail(goodAnalyse, "错误:农产品竞品分析报告-添加失败!");
    }

    /**
     * 按照指定年月添加并获取
     *
     * @param reqGoodAnalyse 农产品竞品分析报告条件查询对象
     * @return 返回农产品竞品分析报告-添加结果
     */
    @Log(title = "农产品竞品分析报告-按照指定年月保存并获取", operateType = OperateType.INSERT)
    @ApiOperation("农产品竞品分析报告-按照指定年月保存并获取")
    @PostMapping("/addReport")
    public Result<GoodAnalyse> addByMonth(@RequestBody ReqGoodAnalyse reqGoodAnalyse) {
        if (StringUtils.isNotEmpty(reqGoodAnalyse.getFirstGood()) &&
                StringUtils.isNotEmpty(reqGoodAnalyse.getSecondGood()) &&
                reqGoodAnalyse.getYear() != null &&
                reqGoodAnalyse.getMonth() != null &&
                !goodAnalyseService.list(reqGoodAnalyse).getList().isEmpty()) {
            return Result.fail(null, String.format("错误:产品%s与产品%s在%d年%d月的农产品竞品分析报告已存在, 不可重复添加",
                    reqGoodAnalyse.getFirstGood(), reqGoodAnalyse.getSecondGood(),
                    reqGoodAnalyse.getYear(), reqGoodAnalyse.getMonth()));
        }

        Map<String, Object> map = goodAnalyseService.getGoodAnalyseReport(reqGoodAnalyse.getFirstGood(), reqGoodAnalyse.getSecondGood(), reqGoodAnalyse.getYear(), reqGoodAnalyse.getMonth());
        String reportContent = (String) map.get("report");
        int flag = (int) map.get("flag");
        log.info("Original Report: {}", reportContent);

        URLEncoder encoder = new URLEncoder();
        reportContent = encoder.encode(reportContent, StandardCharsets.UTF_8);

        if (StringUtils.isNotEmpty(reportContent)) {
            GoodAnalyse report = GoodAnalyse.builder()
                    .flag(flag)
                    .firstGood(reqGoodAnalyse.getFirstGood())
                    .secondGood(reqGoodAnalyse.getSecondGood())
                    .year(reqGoodAnalyse.getYear())
                    .month(reqGoodAnalyse.getMonth())
                    .content(reportContent)
                    .reportTime(new Date())
                    .build();
            if (goodAnalyseService.save(report)) {
                return Result.ok(report, "农产品竞品分析报告-添加成功!");
            }
        }
        return Result.fail(null, "错误:农产品竞品分析报告-添加失败!");
    }

    /**
     * 编辑
     *
     * @param goodAnalyse 农产品竞品分析报告对象
     * @return 返回农产品竞品分析报告-编辑结果
     */
    @Log(title = "农产品竞品分析报告-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("农产品竞品分析报告-编辑")
    @PutMapping
    public Result<GoodAnalyse> edit(@RequestBody GoodAnalyse goodAnalyse) {
        if (goodAnalyseService.updateById(goodAnalyse)) {
            return Result.ok(goodAnalyse, "农产品竞品分析报告-编辑成功!");
        }
        return Result.fail(goodAnalyse, "错误:农产品竞品分析报告-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回农产品产销形势分析报告-删除结果
     */
    @Log(title = "农产品竞品分析报告-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("农产品竞品分析报告-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (goodAnalyseService.removeById(id)) {
            return Result.ok(true, "农产品竞品分析报告-删除成功!");
        }
        return Result.fail(false, "错误:农产品竞品分析报告-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回农产品产销形势分析报告-删除结果
     */
    @Log(title = "农产品竞品分析报告-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("农产品竞品分析报告-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.goodAnalyseService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "农产品竞品分析报告-批量删除成功!");
        }
        return Result.fail(false, "错误:农产品竞品分析报告-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回农产品产销形势分析报告对象
     */
    @Log(title = "农产品竞品分析报告-通过id查询", operateType = OperateType.QUERY)
    @ApiOperation("农产品竞品分析报告-通过id查询")
    @GetMapping("/{id}")
    public Result<GoodAnalyse> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        GoodAnalyse goodAnalyse = goodAnalyseService.getById(id);
        return Result.ok(goodAnalyse, "农产品竞品分析报告-查询成功!");
    }

}

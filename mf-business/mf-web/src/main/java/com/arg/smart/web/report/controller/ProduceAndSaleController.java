package com.arg.smart.web.report.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.report.entity.ProduceAndSale;
import com.arg.smart.web.report.req.ReqProduceAndSale;
import com.arg.smart.web.report.service.ProduceAndSaleService;
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

/**
 * @author cgli
 * @description: 农产品产销形势分析报告表
 * @date: 2023-08-27
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "农产品产销形势分析报告表")
@RestController
@RequestMapping("/produceAndSale")
public class ProduceAndSaleController {

    @Resource
    private ProduceAndSaleService produceAndSaleService;

    /**
     * 分页列表查询
     *
     * @param reqProduceAndSale 农产品产销形势分析报告请求参数
     * @return 返回农产品产销形势分析报告-分页列表
     */
    @ApiOperation(value = "农产品产销形势分析报告-分页列表查询", notes = "农产品产销形势分析报告-分页列表查询")
    @GetMapping
    public Result<PageResult<ProduceAndSale>> queryPageList(ReqProduceAndSale reqProduceAndSale, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(produceAndSaleService.list(reqProduceAndSale), "农产品产销形势分析报告-查询成功!");
    }

    /**
     * 添加
     *
     * @param produceAndSale 农产品产销形势分析报告对象
     * @return 返回农产品产销形势分析报告-添加结果
     */
    @Log(title = "农产品产销形势分析报告-添加", operateType = OperateType.INSERT)
    @ApiOperation("农产品产销形势分析报告-添加")
    @PostMapping
    public Result<ProduceAndSale> add(@RequestBody ProduceAndSale produceAndSale) {
        if (produceAndSaleService.save(produceAndSale)) {
            return Result.ok(produceAndSale, "农产品产销形势分析报告-添加成功!");
        }
        return Result.fail(produceAndSale, "错误:农产品产销形势分析报告-添加失败!");
    }

    /**
     * 按照指定年月添加并获取
     *
     * @param reqProduceAndSale 农产品产销形势分析报告条件查询对象
     * @return 返回农产品产销形势分析报告-添加结果
     */
    @Log(title = "农产品产销形势分析报告-按照指定年月保存并获取", operateType = OperateType.INSERT)
    @ApiOperation("农产品产销形势分析报告-按照指定年月保存并获取")
    @PostMapping("/addReport")
    public Result<ProduceAndSale> addByMonth(@RequestBody ReqProduceAndSale reqProduceAndSale) {
        if (!produceAndSaleService.list(reqProduceAndSale).getList().isEmpty()) {
            return Result.fail(null, String.format("错误:%d年%d月的农产品产销形势分析报告已存在, 不可重复添加",
                    reqProduceAndSale.getYear(), reqProduceAndSale.getMonth()));
        }

        String reportContent = produceAndSaleService.getProductAndSaleReport(reqProduceAndSale.getFlag(), reqProduceAndSale.getYear(), reqProduceAndSale.getMonth());
        log.info("Original Report: {}", reportContent);

        URLEncoder encoder = new URLEncoder();
        reportContent = encoder.encode(reportContent, StandardCharsets.UTF_8);

        if (StringUtils.isNotEmpty(reportContent)) {
            ProduceAndSale report = ProduceAndSale.builder()
                    .flag(reqProduceAndSale.getFlag())
                    .year(reqProduceAndSale.getYear())
                    .month(reqProduceAndSale.getMonth())
                    .content(reportContent)
                    .reportTime(new Date())
                    .build();
            if (produceAndSaleService.save(report)) {
                return Result.ok(report, "农产品产销形势分析报告-添加成功!");
            }
        }
        return Result.fail(null, "错误:农产品产销形势分析报告-添加失败!");
    }

    /**
     * 编辑
     *
     * @param produceAndSale 农产品产销形势分析报告对象
     * @return 返回农产品产销形势分析报告-编辑结果
     */
    @Log(title = "农产品产销形势分析报告-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("农产品产销形势分析报告-编辑")
    @PutMapping
    public Result<ProduceAndSale> edit(@RequestBody ProduceAndSale produceAndSale) {
        if (produceAndSaleService.updateById(produceAndSale)) {
            return Result.ok(produceAndSale, "农产品产销形势分析报告-编辑成功!");
        }
        return Result.fail(produceAndSale, "错误:农产品产销形势分析报告-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回农产品产销形势分析报告-删除结果
     */
    @Log(title = "农产品产销形势分析报告-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("农产品产销形势分析报告-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (produceAndSaleService.removeById(id)) {
            return Result.ok(true, "农产品产销形势分析报告-删除成功!");
        }
        return Result.fail(false, "错误:农产品产销形势分析报告-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回农产品产销形势分析报告-删除结果
     */
    @Log(title = "农产品产销形势分析报告-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("农产品产销形势分析报告-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.produceAndSaleService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "农产品产销形势分析报告-批量删除成功!");
        }
        return Result.fail(false, "错误:农产品产销形势分析报告-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回农产品产销形势分析报告对象
     */
    @Log(title = "农产品产销形势分析报告-通过id查询", operateType = OperateType.QUERY)
    @ApiOperation("农产品产销形势分析报告-通过id查询")
    @GetMapping("/{id}")
    public Result<ProduceAndSale> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        ProduceAndSale produceAndSale = produceAndSaleService.getById(id);
        return Result.ok(produceAndSale, "农产品产销形势分析报告-查询成功!");
    }

}

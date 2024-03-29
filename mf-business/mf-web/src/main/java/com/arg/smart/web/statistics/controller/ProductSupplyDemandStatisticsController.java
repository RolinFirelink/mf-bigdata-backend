package com.arg.smart.web.statistics.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.statistics.entity.ProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.entity.vo.ProductSupplyDemandStatisticsExcel;
import com.arg.smart.web.statistics.req.ReqProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.service.ProductSupplyDemandStatisticsService;
import com.arg.smart.web.statistics.utils.ProductSupplyDemandStatisticsDataListener;
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
 * @author cgli
 * @description: 产品供需统计表
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品供需统计表")
@RestController
@RequestMapping("/productSupplyDemandStatistics")
public class ProductSupplyDemandStatisticsController {
    @Resource
    private ProductSupplyDemandStatisticsService productSupplyDemandStatisticsService;

    /**
     * 大屏——获取产品供需列表
     *
     * @param reqProductSupplyDemandStatistics 产品供需统计表请求参数
     * @return 返回产品供需统计表-分页列表
     */
    @ApiOperation(value = "产品供需统计表-分页列表查询", notes = "产品供需统计表-分页列表查询")
    @GetMapping("/public")
    public Result<List<ProductSupplyDemandStatistics>> publicList(ReqProductSupplyDemandStatistics reqProductSupplyDemandStatistics) {
        return Result.ok(productSupplyDemandStatisticsService.list(reqProductSupplyDemandStatistics), "产品供需统计表-查询成功!");
    }

    /**
     * 大屏——获取产品供需趋势
     */
    @ApiOperation(value = "产品供需趋势-分页列表查询", notes = "产品供需趋势-分页列表查询")
    @GetMapping("/public/trend")
    public Result<List<ProductSupplyDemandStatistics>> publicTrend(ReqProductSupplyDemandStatistics reqProductSupplyDemandStatistics) {
        return Result.ok(productSupplyDemandStatisticsService.trend(reqProductSupplyDemandStatistics), "产品供需趋势-查询成功!");
    }

    /**
     * 分页列表查询
     *
     * @param reqProductSupplyDemandStatistics 产品供需统计表请求参数
     * @return 返回产品供需统计表-分页列表
     */
    @ApiOperation(value = "产品供需统计表-分页列表查询", notes = "产品供需统计表-分页列表查询")
    @GetMapping
    public Result<PageResult<ProductSupplyDemandStatistics>> queryPageList(ReqProductSupplyDemandStatistics reqProductSupplyDemandStatistics, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(productSupplyDemandStatisticsService.pageList(reqProductSupplyDemandStatistics)), "产品供需统计表-查询成功!");
    }

    /**
     * 添加
     *
     * @param productSupplyDemandStatistics 产品供需统计表对象
     * @return 返回产品供需统计表-添加结果
     */
    @Log(title = "产品供需统计表-添加", operateType = OperateType.INSERT)
    @ApiOperation("产品供需统计表-添加")
    @PostMapping
    public Result<ProductSupplyDemandStatistics> add(@RequestBody ProductSupplyDemandStatistics productSupplyDemandStatistics) {
        if (productSupplyDemandStatisticsService.save(productSupplyDemandStatistics)) {
            return Result.ok(productSupplyDemandStatistics, "产品供需统计表-添加成功!");
        }
        return Result.fail(productSupplyDemandStatistics, "错误:产品供需统计表-添加失败!");
    }

    /**
     * 编辑
     *
     * @param productSupplyDemandStatistics 产品供需统计表对象
     * @return 返回产品供需统计表-编辑结果
     */
    @Log(title = "产品供需统计表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("产品供需统计表-编辑")
    @PutMapping
    public Result<ProductSupplyDemandStatistics> edit(@RequestBody ProductSupplyDemandStatistics productSupplyDemandStatistics) {
        if (productSupplyDemandStatisticsService.updateById(productSupplyDemandStatistics)) {
            return Result.ok(productSupplyDemandStatistics, "产品供需统计表-编辑成功!");
        }
        return Result.fail(productSupplyDemandStatistics, "错误:产品供需统计表-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回产品供需统计表-删除结果
     */
    @Log(title = "产品供需统计表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("产品供需统计表-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (productSupplyDemandStatisticsService.removeById(id)) {
            return Result.ok(true, "产品供需统计表-删除成功!");
        }
        return Result.fail(false, "错误:产品供需统计表-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回产品供需统计表-删除结果
     */
    @Log(title = "产品供需统计表-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("产品供需统计表-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.productSupplyDemandStatisticsService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "产品供需统计表-批量删除成功!");
        }
        return Result.fail(false, "错误:产品供需统计表-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回产品供需统计表对象
     */
    @ApiOperation("产品供需统计表-通过id查询")
    @GetMapping("/{id}")
    public Result<ProductSupplyDemandStatistics> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        ProductSupplyDemandStatistics productSupplyDemandStatistics = productSupplyDemandStatisticsService.getById(id);
        return Result.ok(productSupplyDemandStatistics, "产品供需统计表-查询成功!");
    }

    /**
     * 产品供需统计表-Excel导入
     *
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "产品供需统计表-Excel导入", notes = "产品供需统计表-Excel导入")
    @PostMapping("/excelUpload")
    public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ProductSupplyDemandStatisticsExcel.class, new ProductSupplyDemandStatisticsDataListener(productSupplyDemandStatisticsService)).sheet().doRead();
        return Result.ok(true, "上传数据成功");
    }
}

package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.RougePrice;
import com.arg.smart.web.product.req.ReqRougePrice;
import com.arg.smart.web.product.service.RougePriceService;
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
 * @description: 肉鸽价格表
 * @date: 2023-07-31
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "肉鸽价格表")
@RestController
@RequestMapping("/rougePrice")
public class RougePriceController {
    @Resource
    private RougePriceService rougePriceService;

    /**
     * 大屏价格列表查询
     *
     * @param reqRougePrice
     * @return RougePrice对象
     */
    @ApiOperation(value = "肉鸽价格表-大屏价格列表查询", notes = "肉鸽价格表-大屏价格列表查询")
    @GetMapping("/public/priceList")
    public Result<List<RougePrice>> queryPriceList(ReqRougePrice reqRougePrice) {
        return Result.ok(rougePriceService.queryList(reqRougePrice), "鸽子价格表-查询成功!");
    }

    /**
     * 大屏价格趋势查询
     *
     * @param reqRougePrice
     * @return RougePrice对象集合
     */
    @ApiOperation(value = "肉鸽价格表-大屏价格趋势查询", notes = "肉鸽价格表-大屏价格趋势查询")
    @GetMapping("/public/priceTrend")
    public Result<List<RougePrice>> queryPriceTrend(ReqRougePrice reqRougePrice) {
        return Result.ok(rougePriceService.getTrend(reqRougePrice), "鸽子价格表-查询成功!");
    }

    /**
     * 分页列表查询
     *
     * @param reqRougePrice 肉鸽价格表请求参数
     * @return 返回肉鸽价格表-分页列表
     */
    @ApiOperation(value = "肉鸽价格表-分页列表查询", notes = "肉鸽价格表-分页列表查询")
    @GetMapping
    public Result<PageResult<RougePrice>> queryPageList(ReqRougePrice reqRougePrice, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(rougePriceService.list()), "肉鸽价格表-查询成功!");
    }

    /**
     * 添加
     *
     * @param rougePrice 肉鸽价格表对象
     * @return 返回肉鸽价格表-添加结果
     */
    @Log(title = "肉鸽价格表-添加", operateType = OperateType.INSERT)
    @ApiOperation("肉鸽价格表-添加")
    @PostMapping
    public Result<RougePrice> add(@RequestBody RougePrice rougePrice) {
        if (rougePriceService.save(rougePrice)) {
            return Result.ok(rougePrice, "肉鸽价格表-添加成功!");
        }
        return Result.fail(rougePrice, "错误:肉鸽价格表-添加失败!");
    }

    /**
     * 编辑
     *
     * @param rougePrice 肉鸽价格表对象
     * @return 返回肉鸽价格表-编辑结果
     */
    @Log(title = "肉鸽价格表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("肉鸽价格表-编辑")
    @PutMapping
    public Result<RougePrice> edit(@RequestBody RougePrice rougePrice) {
        if (rougePriceService.updateById(rougePrice)) {
            return Result.ok(rougePrice, "肉鸽价格表-编辑成功!");
        }
        return Result.fail(rougePrice, "错误:肉鸽价格表-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回肉鸽价格表-删除结果
     */
    @Log(title = "肉鸽价格表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("肉鸽价格表-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (rougePriceService.removeById(id)) {
            return Result.ok(true, "肉鸽价格表-删除成功!");
        }
        return Result.fail(false, "错误:肉鸽价格表-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回肉鸽价格表-删除结果
     */
    @Log(title = "肉鸽价格表-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("肉鸽价格表-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.rougePriceService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "肉鸽价格表-批量删除成功!");
        }
        return Result.fail(false, "错误:肉鸽价格表-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回肉鸽价格表对象
     */
    @ApiOperation("肉鸽价格表-通过id查询")
    @GetMapping("/{id}")
    public Result<RougePrice> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        RougePrice rougePrice = rougePriceService.getById(id);
        return Result.ok(rougePrice, "肉鸽价格表-查询成功!");
    }
}

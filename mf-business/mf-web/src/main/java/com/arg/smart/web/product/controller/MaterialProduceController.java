package com.arg.smart.web.product.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.*;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.product.entity.MaterialAttribute;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.vo.BaseProduceInfoVO;
import com.arg.smart.web.product.req.ReqMaterialProduce;
import com.arg.smart.web.product.service.MaterialProduceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.*;

/**
 * @author cgli
 * @description: 产品生产表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品生产表")
@RestController
@RequestMapping("/materialProduce")
public class MaterialProduceController {
    @Resource
    private MaterialProduceService materialProduceService;

    /**
     * 获取总种植规模及产量年变化
     *
     * @param flag 产品类别
     */
    @ApiOperation(value = "获取总种植规模及产量年变化", notes = "获取总种植规模及产量年变化")
    @GetMapping("/public/getMaterialProductWithYears/{flag}")
    public Result<List<MaterialProduceWithYear>> getMaterialProductWithYears(@PathVariable("flag") Integer flag) {
        return Result.ok(materialProduceService.getMaterialProductWithYears(flag));
    }

    /**
     * 获取各生产基地种植规模和产量
     *
     * @param flag 产品类别
     */
    @ApiOperation(value = "获取总种植规模及产量年变化", notes = "获取总种植规模及产量年变化")
    @GetMapping("/public/getMaterialProduceWithProduceBase/{flag}")
    public Result<List<MaterialProduceWithProduceBase>> getMaterialProduceWithProduceBase(@PathVariable("flag") Integer flag) {
        return Result.ok(materialProduceService.getMaterialProduceWithProduceBase(flag));
    }

    /**
     * 分页列表查询
     *
     * @param reqMaterialProduce 产品生产表请求参数
     * @return 返回产品生产表-分页列表
     */
    @ApiOperation(value = "产品生产表-分页列表查询", notes = "产品生产表-分页列表查询")
    @GetMapping
    public Result<PageResult<MaterialProduce>> queryPageList(ReqMaterialProduce reqMaterialProduce, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(materialProduceService.list(reqMaterialProduce), "产品生产表-查询成功!");
    }

    /**
     * 添加
     *
     * @param materialProduce 产品生产表对象
     * @return 返回产品生产表-添加结果
     */
    @Log(title = "产品生产表-添加", operateType = OperateType.INSERT)
    @ApiOperation("产品生产表-添加")
    @PostMapping
    public Result<MaterialProduce> add(@RequestBody MaterialProduce materialProduce) {
        if (materialProduceService.save(materialProduce)) {
            return Result.ok(materialProduce, "产品生产表-添加成功!");
        }
        return Result.fail(materialProduce, "错误:产品生产表-添加失败!");
    }

    /**
     * 编辑
     *
     * @param materialProduce 产品生产表对象
     * @return 返回产品生产表-编辑结果
     */
    @Log(title = "产品生产表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("产品生产表-编辑")
    @PutMapping
    public Result<MaterialProduce> edit(@RequestBody MaterialProduce materialProduce) {
        if (materialProduceService.updateById(materialProduce)) {
            return Result.ok(materialProduce, "产品生产表-编辑成功!");
        }
        return Result.fail(materialProduce, "错误:产品生产表-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回产品生产表-删除结果
     */
    @Log(title = "产品生产表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("产品生产表-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (materialProduceService.removeById(id)) {
            return Result.ok(true, "产品生产表-删除成功!");
        }
        return Result.fail(false, "错误:产品生产表-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回产品生产表-删除结果
     */
    @Log(title = "产品生产表-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("产品生产表-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.materialProduceService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "产品生产表-批量删除成功!");
        }
        return Result.fail(false, "错误:产品生产表-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回产品生产表对象
     */
    @ApiOperation("产品生产表-通过id查询")
    @GetMapping("/{id}")
    public Result<MaterialProduce> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        MaterialProduce materialProduce = materialProduceService.getById(id);
        return Result.ok(materialProduce, "产品生产表-查询成功!");
    }

    /**
     * 获取各生产基地种植规模和产量
     *
     * @param flag 产品区分字段
     * @return
     */
    @ApiOperation("产品生产表-通过flag查询基地的产量和种植面积")
    @GetMapping("/public/getMaterialProduceProduceBase/{flag}")
    public Result<List<MaterialProduceWithProduceBase>> queryByProduceBaseId(@PathVariable("flag") Integer flag) {
        List<MaterialProduceWithProduceBase> list = materialProduceService.getByProduceBaseIdAndFlag(flag);
        return Result.ok(list, "产品生产表-查询成功!");
    }

    /**
     * 获取广东省城市的种植(养殖)规模
     *
     * @param flag
     * @return
     */
    @ApiOperation("产品生产表-通过flag查询某个省各个城市的种植面积")
    @GetMapping("/public/getMaterialProduceCity/{flag}")
    public Result<MaterialProduceWithCity> queryCityProduce(@PathVariable("flag") Integer flag) {
        MaterialProduceWithCity produceWithCity = materialProduceService.queryByCity(flag);
        return Result.ok(produceWithCity, "产品生产表-查询成功!");
    }

    /**
     * 获取产品的不同品种上市时间和上市产量
     *
     * @param flag      区分字段
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 预计对象实体类
     */
    @ApiOperation("产品生产表-通过flag查询产品的预计上市时间和上市产量")
    @GetMapping("/public/getEstimateTimeAndMarket/{flag}")
    public Result<List<EstimateTimeAndMarket>> queryEstimateTimeAndMarket(@PathVariable Integer flag,
                                                                          @DateTimeFormat(pattern = "yyyy-MM") Date startTime,
                                                                          @DateTimeFormat(pattern = "yyyy-MM") Date endTime) {
        return Result.ok(materialProduceService.queryByEstimateTime(flag, startTime, endTime), "产品生产表-查询成功!");
    }

    /**
     * 查询产品各品种产量
     *
     * @param flag 区分字段
     * @return
     */
    @ApiOperation("产品生产表-通过flag查询产品的各品种数量")
    @GetMapping("/public/getProduceQuantity/{flag}")
    public Result<List<ProduceNameAndQuantity>> queryProduceQuantity(@PathVariable Integer flag) {
        return Result.ok(materialProduceService.getProduceQuantity(flag), "产品生产表-查询成功!");
    }

    /**
     * 获取产品不同品种预计单位产量
     *
     * @param flag 区分字段
     * @return
     */
    @ApiOperation("产品生产表-通过flag查询产品的各品种单位产量")
    @GetMapping("/public/getUnitQuantity/{flag}")
    public Result<List<EstimateTimeAndMarket>> queryUnitQuantity(@PathVariable Integer flag) {
        return Result.ok(materialProduceService.getUnitQuantity(flag), "产品生产表-查询成功!");
    }

    //根据flag字段和基地名称统计基地种植信息
    @GetMapping("/public/fetch-produce-info/{flag}")
    @ResponseBody
    public Result<List<BaseProduceInfoVO>> fetchProduceInfo(@PathVariable Integer flag) {
        return materialProduceService.fetchProduceInfo(flag);
    }


    /**
     * 某个产品每月种植规模及产量近12个月变化
     *
     * @param flag 区分字段
     * @return
     */
    @GetMapping("/public/produce-scale-info/{flag}")
    @ResponseBody
    public Result<List<MaterialProduceWithYear>> ProduceScaleInfo(@PathVariable Integer flag) {
        return Result.ok(materialProduceService.ProduceScaleInfo(flag), "产品生产表-查询成功!");
    }
}

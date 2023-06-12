package com.arg.smart.web.order.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.entity.vo.OrderDetailExcel;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.uitls.OrderDetailDataListener;
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
 * @description: 订单数据明细表
 * @date: 2023-05-22
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "订单数据明细表")
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Resource
    private OrderDetailService orderDetailService;

    /**
     * 订单详情表-Excel导入
     *
     * @param file 订单主表Excel数据
     */
    @ApiOperation(value = "订单详情表-Excel导入", notes = "订单详情表f-Excel导入")
    @PostMapping("/excelUpload")
    public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), OrderDetailExcel.class, new OrderDetailDataListener(orderDetailService)).sheet().doRead();
        return Result.ok(true, "上传数据成功");
    }

    /**
     * 根据订单获取产品列表
     *
     * @param orderId 订单Id
     * @return 返回产品列表数据
     */
    @ApiOperation(value = "订单数据明细表-分页列表查询", notes = "订单数据明细表-分页列表查询")
    @GetMapping("/listByOrderId/{orderId}")
    public Result<List<OrderDetail>> list(@PathVariable("orderId") Long orderId) {
        return Result.ok(orderDetailService.list(orderId), "订单数据明细表-查询成功!");
    }

    /**
     * 添加
     *
     * @param orderDetail 订单数据明细表对象
     * @return 返回订单数据明细表-添加结果
     */
    @Log(title = "订单数据明细表-添加", operateType = OperateType.INSERT)
    @ApiOperation("订单数据明细表-添加")
    @PostMapping
    public Result<OrderDetail> add(@RequestBody OrderDetail orderDetail) {
        if (orderDetailService.save(orderDetail)) {
            return Result.ok(orderDetail, "订单数据明细表-添加成功!");
        }
        return Result.fail(orderDetail, "错误:订单数据明细表-添加失败!");
    }

    /**
     * 编辑
     *
     * @param orderDetail 订单数据明细表对象
     * @return 返回订单数据明细表-编辑结果
     */
    @Log(title = "订单数据明细表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("订单数据明细表-编辑")
    @PutMapping
    public Result<OrderDetail> edit(@RequestBody OrderDetail orderDetail) {
        if (orderDetailService.updateById(orderDetail)) {
            return Result.ok(orderDetail, "订单数据明细表-编辑成功!");
        }
        return Result.fail(orderDetail, "错误:订单数据明细表-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回订单数据明细表-删除结果
     */
    @Log(title = "订单数据明细表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("订单数据明细表-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (orderDetailService.removeById(id)) {
            return Result.ok(true, "订单数据明细表-删除成功!");
        }
        return Result.fail(false, "错误:订单数据明细表-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回订单数据明细表-删除结果
     */
    @Log(title = "订单数据明细表-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("订单数据明细表-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.orderDetailService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "订单数据明细表-批量删除成功!");
        }
        return Result.fail(false, "错误:订单数据明细表-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回订单数据明细表对象
     */
    @ApiOperation("订单数据明细表-通过id查询")
    @GetMapping("/{id}")
    public Result<OrderDetail> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        OrderDetail orderDetail = orderDetailService.getById(id);
        return Result.ok(orderDetail, "订单数据明细表-查询成功!");
    }

    /**
     * 查询每个产品的销售量
     *
     * @param time     时间差
     * @param timeFlag 年(0)、月(1)、周(2)、日(3)
     * @return 返回每个产品的订单销售量
     */
    @ApiOperation("订单数据明细表-通过时间差查询产品销售量")
    @GetMapping("/time/sales")
    public Result<List<String>> queryAverageSales(@ApiParam(name = "time", value = "时间（年、月、周）") Integer time,
                                                  @ApiParam(name = "timeFlag", value = "年(0)、月(1)、周(2)、日(3)") Integer timeFlag) {
        List<String> list = orderDetailService.averageSales(time, timeFlag);
        return Result.ok(list, "订单数据明细表-查询成功!");
    }

    /**
     * 查询同一产品的不同种类的销售量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param flag      产品区分字段
     * @return 某个产品的不同种类销售量
     */
    @ApiOperation("订单数据明细表-通过时间段查询产品种类销售量")
    @GetMapping("/timePeriod/sales")
    public Result<List<String>> queryMaterial(@ApiParam(name = "startTime", value = "开始时间") String startTime,
                                              @ApiParam(name = "endTime", value = "结束时间") String endTime,
                                              @ApiParam(name = "flag", value = "产品区分字段") Integer flag) {
        List<String> list = orderDetailService.averageSales(startTime, endTime, flag);
        return Result.ok(list, "订单数据明细表-查询成功!");
    }
}


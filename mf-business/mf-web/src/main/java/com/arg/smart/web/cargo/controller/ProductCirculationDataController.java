package com.arg.smart.web.cargo.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.cargo.entity.vo.*;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.cargo.entity.vo.ProductCirculationDataExcel;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.cargo.uitls.ProductCirculationDataListener;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "货运表")
@RestController
@RequestMapping("/productCirculationData")
public class ProductCirculationDataController {
	@Resource
	private ProductCirculationDataService productCirculationDataService;
	/*@Resource
	private OrderInformationListService orderInformationListService;
	@Resource
	private CirculationTransportationFrequencyDataListService circulationTransportationFrequencyDataListService;
	@Resource
	private ShipmentOrderDataService shipmentOrderDataService;*/

	/**
	 * 货运数据表-Excel导入
	 *
	 * @param file 企业主表Excel数据
	 */
	@ApiOperation(value = "货运数据表-Excel导入",notes = "货运数据表-Excel导入")
	@PostMapping("/excelUpload")
	public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), ProductCirculationDataExcel.class, new ProductCirculationDataListener(productCirculationDataService)).sheet().doRead();
		return Result.ok(true,"上传数据成功");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProductCirculationData 货运表请求参数
	 * @return 返回货运表-分页列表
	 */
	@ApiOperation(value = "货运表-分页列表查询", notes = "货运表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductCirculationData>> queryPageList(ReqProductCirculationData reqProductCirculationData, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(productCirculationDataService.list(reqProductCirculationData), "货运表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param productCirculationData 货运表对象
	 * @return 返回货运表-添加结果
	 */
	@Log(title = "货运表-添加", operateType = OperateType.INSERT)
	@ApiOperation("货运表-添加")
	@PostMapping
	public Result<ProductCirculationData> add(@RequestBody ProductCirculationData productCirculationData) {
		if (productCirculationDataService.save(productCirculationData)) {
			return Result.ok(productCirculationData, "货运表-添加成功!");
		}
        return Result.fail(productCirculationData, "错误:货运表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param productCirculationData 货运表对象
	 * @return 返回货运表-编辑结果
	 */
	@Log(title = "货运表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("货运表-编辑")
	@PutMapping
	public Result<ProductCirculationData> edit(@RequestBody ProductCirculationData productCirculationData) {
		if (productCirculationDataService.updateById(productCirculationData)) {
		    return Result.ok(productCirculationData, "货运表-编辑成功!");
		}
		return Result.fail(productCirculationData, "错误:货运表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回货运表-删除结果
	 */
	@Log(title = "货运表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("货运表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (productCirculationDataService.removeById(id)) {
			return Result.ok(true, "货运表-删除成功!");
		}
		return Result.fail(false, "错误:货运表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回货运表-删除结果
	 */
	@Log(title = "货运表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("货运表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.productCirculationDataService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "货运表-批量删除成功!");
		}
		return Result.fail(false, "错误:货运表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回货运表对象
	 */
	@ApiOperation("货运表-通过id查询")
	@GetMapping("/{id}")
	public Result<ProductCirculationData> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		ProductCirculationData productCirculationData = productCirculationDataService.getById(id);
		return Result.ok(productCirculationData, "货运表-查询成功!");
	}

	/**
	 * 使用flag查询 订单信息
	 * @param flag
	 * @return
	 */
	@ApiOperation("大屏页面-订单信息列表")
	@GetMapping("/order/{flag}")
	public Result<List<ProductCirculationData>> queryOrderByflag(@ApiParam(name = "flag", value = "产品类别") @PathVariable Integer flag){
		List<ProductCirculationData> productCirculationDataList = productCirculationDataService.selectOfOrderInformationList(flag);
		if(productCirculationDataList==null)return Result.ok("查询失败");
		return Result.ok(productCirculationDataList,"查询成功");
	}

	/**
	 * 使用flag查询 发货订单
	 * @param flag
	 * @return
	 */
	@ApiOperation("发货订单列表信息")
	@GetMapping("/shipment/{flag}")
	public Result<List<ProductCirculationData>> queryShipmentByflag(@ApiParam(name = "flag", value = "产品类别") @PathVariable Integer flag){
		List<ProductCirculationData> productCirculationDataList = productCirculationDataService.selectOfShipmentOrderData(flag);
		if(productCirculationDataList==null)return Result.ok("查询失败");
		return Result.ok(productCirculationDataList,"查询成功");
	}

	/**
	 *通过flag查询 得到产品流通订单
	 *
	 * @param flag
	 * @return
	 */
	@ApiOperation(value = "产品流通运输订单",notes = "是用产品类别和订单编号来查询出对应的运输流通订单")
	@GetMapping("/Transportation/{flag}")
	public Result<List<CirculationTransportationFrequencyDataList>> queryByFlagTransportationTimes(@ApiParam(name = "flag", value = "产品类别") @PathVariable Integer flag) {
		List<CirculationTransportationFrequencyDataList> circulationTransportationFrequencyData = productCirculationDataService.creatCirculationTransportationFrequencyDataList(flag);
		return Result.ok(circulationTransportationFrequencyData);
	}
	 /* 计算不同订单到各个销售渠道的占比
	 * @return
	 */
	@ApiOperation(value = "销售渠道占比",notes = "获得不同订单到各个销售渠道的占比")
	@GetMapping("/channelProportion/{flag}")
	public Result<Map<String, Double>> channelProportion(@ApiParam(name = "flag", value = "大数据类型") @PathVariable Integer flag){
		Map<String, Double> map = productCirculationDataService.selectChannelByFlag(flag);
		if(map==null)return Result.ok("查询失败，可能是没有这个产品~");
		return Result.ok(map,"销售渠道的占比查询成功！");
	}

	/**
	 *
	 * @return
	 */
	@ApiOperation(value = "收货发货的相关信息",notes = "根据发货地址，返回收货发货的相关信息")
	@GetMapping(value = "/orderInformation/{flag}/{shippingLocation}",produces = "application/json;charset=UTF-8")
	public Result<List<ProductCirculationData>> orderInformationByShippingLocation(@ApiParam(name = "flag", value = "大数据类型") @PathVariable Integer flag,@ApiParam(name = "shippingLocation", value = "发货地址") @PathVariable String shippingLocation){
		List<ProductCirculationData> productCirculationDataList = productCirculationDataService.findOrderInformationList(flag,shippingLocation);
		return  Result.ok(productCirculationDataList,"按照发货地址查看发货收货信息成功！");
	}

	/**
	 * 通过flag查询
	 *
	 * @param flag 模块
	 * @return 返回Map<品类,占比>
	 */
	@ApiOperation("货运表-通过flag查询不同运输方式的占比")
	@GetMapping("/selectPercentage/{flag}")
	public List<TransportationProportion> selectPercentageByFlag(@ApiParam(name = "flag",value = "产品类别") @PathVariable Integer flag){
		List<TransportationProportion> list = productCirculationDataService.selectPercentageByFlag(flag);
		return list;
	}
	@ApiOperation("货运表-通过flag查询运输均价")
	@GetMapping("/selectShippingPrice/{flag}")
	public BigDecimal selectAverageShippingPriceByFlag(@ApiParam(name = "flag",value = "产品类别") @PathVariable Integer flag){
		BigDecimal bigDecimal = productCirculationDataService.selectAverageShippingPriceByFlag(flag);
		return bigDecimal;
	}
	@ApiOperation("货运表-通过flag查询各渠道占比")
	@GetMapping("/selectCompanyQuantity/{flag}")
	public Result<Map<String, Integer>> selectCompanyQuantity(@ApiParam(name = "flag",value = "产品类别")@PathVariable Integer flag){
		Map<String,Integer> map = productCirculationDataService.selectCompanyQuantity(flag);
		return Result.ok(map,"hhh.");
	}
	@ApiOperation("得到销售的起始点和销售点")
	@GetMapping("LocationLatLon/{flag}")
	public Result<List<LocationLatLon>> selectLocationLatLons(@ApiParam(name = "flag", value = "产品类别")@PathVariable Integer flag){

		return Result.ok(productCirculationDataService.selectLocationLatLon(flag));
	}


}

package com.arg.smart.web.product.controller;

import com.alibaba.excel.EasyExcel;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.ProductPriceTrendData;
import com.arg.smart.web.product.entity.vo.*;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceMonthService;
import com.arg.smart.web.product.service.ProductPriceService;
import com.arg.smart.web.product.units.ProductPriceDataListener;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "产品价格表")
@RestController
@RequestMapping("/productPrice")
public class ProductPriceController {

    @Resource
    private ProductPriceService productPriceService;

	@Resource
	private ProductPriceMonthService productPriceMonthService;

    /**
     * PC端——行业分析地区价格走势预测
     * @param reqProductPrice 产品价格表请求参数
     * @return 返回产品价格表-分页列表
     */
    @ApiOperation(value = "PC端——行业分析地区价格走势", notes = "PC端——行业分析地区价格走势")
    @GetMapping("/public/getMarketTrendForecast")
    public Result<List<ProductPriceTrendData>> getMarketTrendForecast(ReqProductPrice reqProductPrice) {
        return Result.ok(productPriceService.getProductPriceTrendDataForecast(reqProductPrice), "产品价格预测-查询成功!");
    }

    /**
     * PC端——行业分析地区价格走势
     * @param reqProductPrice 产品价格表请求参数
     * @return 返回产品价格表-分页列表
     */
    @ApiOperation(value = "PC端——行业分析地区价格走势", notes = "PC端——行业分析地区价格走势")
    @GetMapping("/public/getMarketTrend")
    public Result<List<ProductPriceTrendData>> getMarketTrend(ReqProductPrice reqProductPrice) {
        return Result.ok(productPriceService.getProductPriceTrendData(reqProductPrice), "产品价格表-查询成功!");
    }

	/**
	 * 爬虫添加
	 *
	 * @return 返回爬虫添加结果
	 */
	@Log(title = "惠农网信息爬虫添加", operateType = OperateType.INSERT)
	@ApiOperation("惠农网信息爬虫添加")
	@PostMapping("/public/cnhnbAdd")
	public Result<String> cnhnbAdd() {
		// TODO 暂时使用该接口要求在本地有D:\pachong\new\chromedriver.exe文件且版本必须适配
		if (productPriceService.cnhnbSave()) {
			return Result.ok("爬虫添加成功");
		}
		return Result.fail("爬虫添加失败");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqProductPrice 产品价格表请求参数
	 * @return 返回产品价格表-分页列表
	 */
	@ApiOperation(value = "产品价格表-分页列表查询", notes = "产品价格表-分页列表查询")
	@GetMapping
	public Result<PageResult<ProductPrice>> queryPage(ReqProductPrice reqProductPrice, ReqPage reqPage) {
		PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
		return Result.ok(new PageResult<>(productPriceService.queryPage(reqProductPrice)), "产品价格表-查询成功!");
	}

    /**
     * 大屏获取价格趋势
     */
    @ApiOperation(value = "大屏获取价格趋势",notes="大屏获取价格趋势")
    @GetMapping("/public/trend")
    public Result<List<ProductPriceVO>> publicTrend(ReqProductPrice reqProductPrice){
        return Result.ok(productPriceService.publicTrend(reqProductPrice),"查询大屏价格趋势成功");
    }

    /**
     * PC端——今天价格指数查询
     */
    @ApiOperation(value = "PC端——今天价格指数查询", notes = "PC端今日价格指数查询")
    @GetMapping("/public/temp")
    public Result<List<PriceTemp>> getPriceTemp(ReqProductPrice reqProductPrice) {
        return Result.ok(productPriceService.getPriceTemp(reqProductPrice));
    }

    /**
     * 大屏-列表查询
     *
     * @param reqProductPrice 产品价格表请求参数
     * @return 返回产品价格表-大屏列表
     */
    @ApiOperation(value = "产品价格表-大屏列表查询", notes = "产品价格表-大屏列表查询")
    @GetMapping("/public/list")
    public Result<List<ProductPrice>> queryList(ReqProductPrice reqProductPrice) {
        return Result.ok(productPriceService.queryList(reqProductPrice), "产品价格表-查询成功!");
    }

    @ApiOperation(value = "PC端产品价格列表查询",notes="PC端产品价格列表查询")
    @GetMapping("/public")
    public Result<PageResult<ProductPrice>> publicQueryPage(ReqProductPrice reqProductPrice,ReqPage reqPage){
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(productPriceService.queryPage(reqProductPrice)), "产品价格表-查询成功!");
    }

    @ApiOperation(value = "产品价格表-地区行情走势按月", notes = "产品价格表-地区行情走势按月")
    @GetMapping("/public/selectAvgPriceOfDate")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfDate(
            ReqProductPrice reqProductPrice
    ) {
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String region = reqProductPrice.getRegion();
        String product = reqProductPrice.getProduct();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusDays(30);
        }
        if (region == null) {
            region = "";
        }
        if(product == null){
            product = "";
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfDate(startTime, endTime, region,product);
        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }

    @ApiOperation(value = "产品价格表-地区行情走势按季", notes = "产品价格表-地区行情走势按季")
    @GetMapping("/public/selectAvgPriceOfMonth")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfMonth(
            ReqProductPrice reqProductPrice
    ) {
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String region = reqProductPrice.getRegion();
        String product = reqProductPrice.getProduct();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusMonths(12);//近12个月跨度
        }
        if(region == null){
            region = "";
        }
        if(product == null){
            product = "";
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfMonth(startTime, endTime,region,product);
        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }

    @ApiOperation(value = "产品价格表-地区行情走势按月", notes = "产品价格表-地区行情走势按季")
    @GetMapping("/public/selectAvgPriceOfHalfYear")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfHalfYear(

            ReqProductPrice reqProductPrice
    ) {
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String region = reqProductPrice.getRegion();
        String product = reqProductPrice.getProduct();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusMonths(6);//近12个月跨度
        }
        if(region == null){
            region = "";
        }
        if(product == null){
            product = "";
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfHalfYear(startTime, endTime,region,product);

        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }

    @ApiOperation(value = "产品价格表-地区行情走势按月", notes = "产品价格表-地区行情走势按季")
    @GetMapping("/public/selectAvgPriceOfYear")
    public Result<Map<Integer, Map<String, BigDecimal>>> selectAvgPriceOfYear(
            ReqProductPrice reqProductPrice
    ) {
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String region = reqProductPrice.getRegion();
        String product = reqProductPrice.getProduct();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusMonths(6);//近12个月跨度
        }
        if(region == null){
            region = "";
        }
        if(product == null){
            product = "";
        }
        Map<Integer, Map<String, BigDecimal>> res = new HashMap<>();
        List<AvgPriceVO> prices = productPriceMonthService.selectAvgPriceOfYear(startTime, endTime,region,product);
        for (AvgPriceVO p : prices) {
            Integer flag = p.getFlag();
            String time = p.getTime();
            BigDecimal price = p.getPrice();
            Map<String, BigDecimal> priceMap;
            if (res.containsKey(flag)) {
                priceMap = res.get(flag);
            } else {
                priceMap = new HashMap<>();
                res.put(flag, priceMap);
            }
            priceMap.put(time, price);
        }
        return Result.ok(res);
    }


    /**
     * 添加
     *
     * @param productPrice 产品价格表对象
     * @return 返回产品价格表-添加结果
     */
    @Log(title = "产品价格表-添加", operateType = OperateType.INSERT)
    @ApiOperation("产品价格表-添加")
    @PostMapping
    public Result<ProductPrice> add(@RequestBody ProductPrice productPrice) {
        if (productPriceService.save(productPrice)) {
            return Result.ok(productPrice, "产品价格表-添加成功!");
        }
        return Result.fail(productPrice, "错误:产品价格表-添加失败!");
    }

    /**
     * 编辑
     *
     * @param productPrice 产品价格表对象
     * @return 返回产品价格表-编辑结果
     */
    @Log(title = "产品价格表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("产品价格表-编辑")
    @PutMapping
    public Result<ProductPrice> edit(@RequestBody ProductPrice productPrice) {
        if (productPriceService.updateById(productPrice)) {
            return Result.ok(productPrice, "产品价格表-编辑成功!");
        }
        return Result.fail(productPrice, "错误:产品价格表-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回产品价格表-删除结果
     */
    @Log(title = "产品价格表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("产品价格表-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable Long id) {
        if (productPriceService.removeById(id)) {
            return Result.ok(true, "产品价格表-删除成功!");
        }
        return Result.fail(false, "错误:产品价格表-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回产品价格表-删除结果
     */
    @Log(title = "产品价格表-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("产品价格表-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.productPriceService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "产品价格表-批量删除成功!");
        }
        return Result.fail(false, "错误:产品价格表-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回产品价格表对象
     */
    @ApiOperation("产品价格表-通过id查询")
    @GetMapping("/{id}")
    public Result<ProductPrice> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable Long id) {
        ProductPrice productPrice = productPriceService.getById(id);
        return Result.ok(productPrice, "产品价格表-查询成功!");
    }

    /**
     * 获取产品价格相关地区列表
     */
    @ApiOperation("产品价格地区列表-获取")
    @GetMapping("/public/regionList")
    public Result<List<String>> regionList() {
        return Result.ok(productPriceService.regionList(), "产品价格地区列表-获取成功");
    }

    @ApiOperation(value = "产品价格表-地区平均价格", notes = "返回数据最多前5条")
    @GetMapping("/public/selectAvgPriceOfArea")
    public Result<List<AreaAvgPriceAndSales>> selectAvgPriceAndSales(
            @RequestParam(required = false)
            Integer flag,
            @RequestParam(required = false)
            String product
    ) {
    List<AreaAvgPriceAndSales> res = productPriceService.selectAvgPriceAndSales(flag, product);
        return Result.ok(res);
    }

    /**
     * 产品价格表Excel
     *
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "产品价格表-Excel导入",notes = "产品价格表-Excel导入")
    @PostMapping("/excelUpload")
    public Result<Boolean> excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ProductPriceExcel.class, new ProductPriceDataListener(productPriceService)).sheet().doRead();
        return Result.ok(true,"上传数据成功");
    }

    /**
     * 获取产品平均价格（时间、平均价格、产品）
     */
    @ApiOperation(value="产品平均价格",notes = "产品平均价格")
    @GetMapping("/public/avgPrice")
    public Result<List<ProductPrice>> avgPrice(ReqProductPrice reqProductPrice){
        return Result.ok(productPriceService.avgPrice(reqProductPrice),"产品平均价格查询成功");
    }
}

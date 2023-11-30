package com.arg.smart.web.data.controller;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.data.entity.CompanyProduct;
import com.arg.smart.web.data.entity.vo.AvgProductValue;
import com.arg.smart.web.data.entity.vo.CompanyProductVO;
import com.arg.smart.web.data.req.ReqCompanyProduct;
import com.arg.smart.web.data.service.CompanyProductService;
import com.arg.smart.web.data.service.MarketSizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.log.annotation.Log;
import com.github.pagehelper.PageHelper;
import java.util.Arrays;

@Slf4j
@Api(tags = "预制菜菜品")
@RestController
@RequestMapping("/companyProduct")
public class CompanyProductController {
    @Resource
    private CompanyProductService companyProductService;

    @Resource
    private MarketSizeService productMarketSizeService;

    /**
     * 大屏——公司产品列表查询！
     *
     * @param reqCompanyProduct 公司产品请求参数
     * @return 返回大屏——公司产品列
     */
    @ApiModelProperty("大屏——公司产品列表查询")
    @GetMapping("/public/list")
    public Result<List<CompanyProductVO>> queryCompanyProductList(ReqCompanyProduct reqCompanyProduct){
        return Result.ok(companyProductService.publicList(reqCompanyProduct),"大屏——公司产品列表查询！");
    }

    @ApiModelProperty("根据产品获取公司名称及其价格")
    @GetMapping("/public/selectAvgValue/{productName}")
    public Result<List<AvgProductValue>> queryAvgProductValue(@ApiParam(name = "productName", value = "产品名称") @PathVariable String productName){
        return Result.ok(companyProductService.companyProductValue(productName),"预制菜菜品查询成功！");
    }

    /**
     * 分页列表查询
     *
     * @param reqCompanyProduct 公司产品请求参数
     * @return 返回公司产品-分页列表
     */
    @ApiOperation(value = "公司产品-分页列表查询", notes = "公司产品-分页列表查询")
    @GetMapping
    public Result<PageResult<CompanyProduct>> queryPageList(ReqCompanyProduct reqCompanyProduct, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(companyProductService.list(reqCompanyProduct)), "公司产品-查询成功!");
    }

    /**
     * 添加
     *
     * @param companyProduct 公司产品对象
     * @return 返回公司产品-添加结果
     */
    @Log(title = "公司产品-添加", operateType = OperateType.INSERT)
    @ApiOperation("公司产品-添加")
    @PostMapping
    public Result<CompanyProduct> add(@RequestBody CompanyProduct companyProduct) {
        if (companyProductService.save(companyProduct)) {
            return Result.ok(companyProduct, "公司产品-添加成功!");
        }
        return Result.fail(companyProduct, "错误:公司产品-添加失败!");
    }

    /**
     * 编辑
     *
     * @param companyProduct 公司产品对象
     * @return 返回公司产品-编辑结果
     */
    @Log(title = "公司产品-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("公司产品-编辑")
    @PutMapping
    public Result<CompanyProduct> edit(@RequestBody CompanyProduct companyProduct) {
        if (companyProductService.updateById(companyProduct)) {
            return Result.ok(companyProduct, "公司产品-编辑成功!");
        }
        return Result.fail(companyProduct, "错误:公司产品-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回公司产品-删除结果
     */
    @Log(title = "公司产品-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("公司产品-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (companyProductService.removeById(id)) {
            return Result.ok(true, "公司产品-删除成功!");
        }
        return Result.fail(false, "错误:公司产品-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回公司产品-删除结果
     */
    @Log(title = "公司产品-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("公司产品-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.companyProductService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "公司产品-批量删除成功!");
        }
        return Result.fail(false, "错误:公司产品-批量删除失败!");
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回公司产品对象
     */
    @ApiOperation("公司产品-通过id查询")
    @GetMapping("/{id}")
    public Result<CompanyProduct> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        CompanyProduct companyProduct = companyProductService.getById(id);
        return Result.ok(companyProduct, "公司产品-查询成功!");
    }
}


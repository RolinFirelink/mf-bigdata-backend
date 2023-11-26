package com.arg.smart.web.average.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.average.entity.Keyword;
import com.arg.smart.web.average.req.ReqKeyword;
import com.arg.smart.web.average.service.KeywordService;
import com.arg.smart.web.company.entity.OriginPrice;
import com.arg.smart.web.customer.entity.CustomerBehavior;
import com.arg.smart.web.customer.req.ReqCustomerBehavior;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Api(tags = "关键词表")
@RestController
@RequestMapping("/Keyword")
public class KeywordController {
    @Resource
    private KeywordService keywordService;

    /**
     * 分页列表查询
     *
     * @param reqKeyword 关键词表请求参数
     * @return 返回关键词表-分页列表
     */
    @ApiOperation(value = "关键词表-分页列表查询", notes = "关键词表-分页列表查询")
    @GetMapping
    public Result<PageResult<Keyword>> queryPageList(ReqKeyword reqKeyword, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(keywordService.list(reqKeyword)), "关键词表-查询成功!");
    }

    /**
     * 添加
     *
     * @param Keyword 关键词表对象
     * @return 返回关键词表-添加结果
     */
    @Log(title = "关键词表-添加", operateType = OperateType.INSERT)
    @ApiOperation("关键词表-添加")
    @PostMapping
    public Result<Keyword> add(@RequestBody Keyword keyword) {
        if (keywordService.save(keyword)) {
            return Result.ok(keyword, "关键词表-添加成功!");
        }
        return Result.fail(keyword, "错误:关键词表-添加失败!");
    }

    /**
     * 编辑
     *
     * @param Keyword 关键词表对象
     * @return 返回关键词表-编辑结果
     */
    @Log(title = "关键词表-编辑", operateType = OperateType.UPDATE)
    @ApiOperation("关键词表-编辑")
    @PutMapping
    public Result<Keyword> edit(@RequestBody Keyword keyword) {
        if (keywordService.updateById(keyword)) {
            return Result.ok(keyword, "关键词表-编辑成功!");
        }
        return Result.fail(keyword, "错误:关键词表-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回客户消费行为表-删除结果
     */
    @Log(title = "关键词表-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation("关键词表-通过id删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        if (keywordService.removeById(id)) {
            return Result.ok(true, "关键词表-删除成功!");
        }
        return Result.fail(false, "错误:关键词表-删除失败!");
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回客户消费行为表-删除结果
     */
    @Log(title = "关键词表-批量删除", operateType = OperateType.DELETE)
    @ApiOperation("关键词表-批量删除")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        if (this.keywordService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok(true, "关键词表-批量删除成功!");
        }
        return Result.fail(false, "错误:关键词表-批量删除失败!");
    }
    @ApiOperation("关键词-通过id查询")
    @GetMapping("/{id}")
    public Result<Keyword> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        Keyword keyword = keywordService.getById(id);
        return Result.ok(keyword, "关键词-查询成功!");
    }


    @ApiOperation("查询对应的flag的关键字")
    @GetMapping("/public/Keyword/{flag}/{count}")
    public Result<List<Keyword>> selectKeyword(@ApiParam(name = "flag", value = "区分字段") @PathVariable Integer flag,@ApiParam(name = "count", value = "要查询的数量") @PathVariable Integer count){
        List<Keyword> keywords = keywordService.selectKeywordList(flag,count);
        if(keywords==null)return Result.fail(keywords,"查询失败");
        return Result.ok(keywords,"查询成功");
    }

}

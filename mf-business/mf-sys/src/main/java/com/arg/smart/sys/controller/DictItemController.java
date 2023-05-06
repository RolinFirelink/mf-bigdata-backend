package com.arg.smart.sys.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.sys.cache.DictCache;
import com.arg.smart.sys.entity.Dict;
import com.arg.smart.sys.entity.DictItem;
import com.arg.smart.sys.mapper.DictMapper;
import com.arg.smart.sys.req.ReqDictItem;
import com.arg.smart.sys.service.DictItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 字典项
 * @author cgli
 * @date: 2023-01-03
 * @Version: V1.0.0
 */
@Slf4j
@Api(tags = "字典项")
@RestController
@RequestMapping("/dictItem")
public class DictItemController {
    @Resource
    private DictItemService dictItemService;
    @Resource
    private DictMapper dictMapper;
    @Resource
    private DictCache dictCache;

    /**
     * 分页列表查询
     *
     * @param reqDictItem
     * @return
     */
    @ApiOperation(value = "字典项-分页列表查询", notes = "字典项-分页列表查询")
    @GetMapping
    @RequiresPermissions("sys:dict:query")
    public Result<PageResult<DictItem>> queryPageList(ReqDictItem reqDictItem, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        return Result.ok(new PageResult<>(dictItemService.getDictItems(reqDictItem)), "字典项-查询成功!");
    }

    @ApiOperation("根据字典编码获取字典项(值根据类型设置进行转换)")
    @GetMapping("/{dictCode}")
    public Result<List<DictItem>> queryList(@ApiParam(name = "dictCode", value = "字典编码") @PathVariable String dictCode) {
        List<DictItem> list = dictCache.getFromCacheAndDB(dictCode);
        if (list == null || list.isEmpty()) {
            return Result.fail("错误:未获取到字典信息");
        }
        return Result.ok(list, "字典项-查询成功!");
    }


    /**
     * 添加
     *
     * @param dictItem
     * @return
     */
    @Log(title = "字典项-添加", operateType = OperateType.INSERT)
    @ApiOperation(value = "字典项-添加", notes = "字典项-添加")
    @PostMapping
    @RequiresPermissions("sys:dict:insert")
    public Result<DictItem> add(@RequestBody DictItem dictItem) {
        Result result = verifyDict(dictItem);
        if (!result.isSuccess()) {
            return result;
        }
        Dict dict = dictMapper.selectOne(new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, dictItem.getDictCode()));
        if (dict != null) {
            dictItem.setDictId(dict.getId());
            if (dictItemService.save(dictItem)) {
                dictCache.removeOneCache(dictItem.getDictCode());
                return Result.ok(dictItem, "字典项-添加成功!");
            }
        }
        return Result.fail(dictItem, "错误:字典项-添加失败!");
    }

    /**
     * 字典入参校验
     *
     * @param dictItem
     * @return
     */
    private Result<DictItem> verifyDict(DictItem dictItem) {
        if (StringUtils.isEmpty(dictItem.getDictCode())) {
            return Result.fail("错误:字典编码不允许为空!");
        }
        if (StringUtils.isEmpty(dictItem.getDictLabel())) {
            return Result.fail("错误:字典标签不允许为空!");
        }
        if (dictItem.getDictValue() == null || StringUtils.isEmpty(dictItem.getDictValue().toString())) {
            return Result.fail("错误:字典键值不允许为空!");
        }
        return Result.ok(dictItem, "校验成功");
    }

    /**
     * 编辑
     *
     * @param dictItem
     * @return
     */
    @Log(title = "字典项-编辑", operateType = OperateType.UPDATE)
    @ApiOperation(value = "字典项-编辑", notes = "字典项-编辑")
    @PutMapping
    @RequiresPermissions("sys:dict:update")
    public Result<DictItem> edit(@RequestBody DictItem dictItem) {
        Result result = verifyDict(dictItem);
        if (!result.isSuccess()) {
            return result;
        }
        if (dictItemService.updateById(dictItem)) {
            dictCache.removeOneCache(dictItem.getDictCode());
            return Result.ok(dictItem, "字典项-编辑成功!");
        }
        return Result.fail(dictItem, "错误:字典项-编辑失败!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @Log(title = "字典项-通过id删除", operateType = OperateType.DELETE)
    @ApiOperation(value = "字典项-通过id删除", notes = "字典项-通过id删除")
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:dict:delete")
    public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
        DictItem dictItem = dictItemService.getById(id);
        if (dictItem != null && dictItemService.removeById(id)) {
            dictCache.removeOneCache(dictItem.getDictCode());
            return Result.ok(true, "字典项-删除成功!");
        }
        return Result.fail(false, "错误:字典项-删除失败!");
    }
}

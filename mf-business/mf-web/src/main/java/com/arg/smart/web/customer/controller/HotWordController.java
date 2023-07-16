package com.arg.smart.web.customer.controller;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.log.annotation.Log;
import com.arg.smart.web.customer.entity.HotWord;
import com.arg.smart.web.customer.req.ReqHotWord;
import com.arg.smart.web.customer.service.HotWordService;
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
 * @description: 热词表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "热词表")
@RestController
@RequestMapping("/hotWord")
public class HotWordController {
	@Resource
	private HotWordService hotWordService;

	/**
	 * 热词列表查询
	 * @param type 0表示查全部 1表示只查询负面
	 */
	@ApiOperation(value = "热词列表查询",notes = "热词列表查询")
	@GetMapping("/public/list/{type}")
	public Result<List<HotWord>> publicList(@PathVariable("type") Integer type, ReqHotWord reqHotWord){
		return Result.ok(hotWordService.publicList(type,reqHotWord),"热词列表查询");
	}

	/**
	 * 分页列表查询
	 *
	 * @param reqHotWord 热词表请求参数
	 * @return 返回热词表-分页列表
	 */
	@ApiOperation(value = "热词表-分页列表查询", notes = "热词表-分页列表查询")
	@GetMapping
	public Result<PageResult<HotWord>> queryPageList(ReqHotWord reqHotWord, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	    return Result.ok(hotWordService.list(reqHotWord), "热词表-查询成功!");
	}

	/**
	 * 添加
	 *
	 * @param hotWord 热词表对象
	 * @return 返回热词表-添加结果
	 */
	@Log(title = "热词表-添加", operateType = OperateType.INSERT)
	@ApiOperation("热词表-添加")
	@PostMapping
	public Result<HotWord> add(@RequestBody HotWord hotWord) {
		if (hotWordService.save(hotWord)) {
			return Result.ok(hotWord, "热词表-添加成功!");
		}
        return Result.fail(hotWord, "错误:热词表-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param hotWord 热词表对象
	 * @return 返回热词表-编辑结果
	 */
	@Log(title = "热词表-编辑", operateType = OperateType.UPDATE)
	@ApiOperation("热词表-编辑")
	@PutMapping
	public Result<HotWord> edit(@RequestBody HotWord hotWord) {
		if (hotWordService.updateById(hotWord)) {
		    return Result.ok(hotWord, "热词表-编辑成功!");
		}
		return Result.fail(hotWord, "错误:热词表-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回热词表-删除结果
	 */
	@Log(title = "热词表-通过id删除", operateType = OperateType.DELETE)
	@ApiOperation("热词表-通过id删除")
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		if (hotWordService.removeById(id)) {
			return Result.ok(true, "热词表-删除成功!");
		}
		return Result.fail(false, "错误:热词表-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回热词表-删除结果
	 */
	@Log(title = "热词表-批量删除", operateType = OperateType.DELETE)
	@ApiOperation("热词表-批量删除")
	@DeleteMapping("/batch")
	public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
		if (this.hotWordService.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "热词表-批量删除成功!");
		}
		return Result.fail(false, "错误:热词表-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回热词表对象
	 */
	@ApiOperation("热词表-通过id查询")
	@GetMapping("/{id}")
	public Result<HotWord> queryById(@ApiParam(name = "id", value = "唯一性ID") @PathVariable String id) {
		HotWord hotWord = hotWordService.getById(id);
		return Result.ok(hotWord, "热词表-查询成功!");
	}
}

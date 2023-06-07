package com.arg.smart.report.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.arg.smart.report.common.base.BaseController;
import com.arg.smart.report.common.config.V2Config;
import com.arg.smart.report.common.domain.AjaxResult;
import com.arg.smart.report.common.domain.ResultTable;
import com.arg.smart.report.common.domain.Tablepar;
import com.arg.smart.report.entity.GoviewProject;
import com.arg.smart.report.entity.GoviewProjectData;
import com.arg.smart.report.entity.SysFile;
import com.arg.smart.report.entity.vo.GoviewProjectVo;
import com.arg.smart.report.entity.vo.SysFileVo;
import com.arg.smart.report.service.IGoviewProjectDataService;
import com.arg.smart.report.service.IGoviewProjectService;
import com.arg.smart.report.service.ISysFileService;
import com.arg.smart.report.utils.ConvertUtil;
import com.arg.smart.report.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/project")
public class GoviewProjectController extends BaseController {
	@Autowired
	private ISysFileService iSysFileService;
	@Autowired
	private V2Config v2Config;
	@Autowired
	private IGoviewProjectService iGoviewProjectService;
	@Autowired
	private IGoviewProjectDataService iGoviewProjectDataService;
	
	
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar){
		Page<GoviewProject> page= new Page<GoviewProject>(tablepar.getPage(), tablepar.getLimit());
		IPage<GoviewProject> iPages=iGoviewProjectService.page(page, new LambdaQueryWrapper<GoviewProject>());
		ResultTable resultTable=new ResultTable();
		resultTable.setData(iPages.getRecords());
		resultTable.setCode(200);
		resultTable.setCount(iPages.getTotal());
		resultTable.setMsg("获取成功");
		return resultTable;
	}
	
	
	/**
     * 新增保存
     * @param 
     * @return
     */
	//@Log(title = "项目表新增", action = "111")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/create")
	@ResponseBody
	public AjaxResult add(@RequestBody GoviewProject goviewProject){
		goviewProject.setCreateTime(DateUtil.now());
		goviewProject.setState(-1);
		boolean b=iGoviewProjectService.save(goviewProject);
		if(b){
			return successData(200, goviewProject).put("msg", "创建成功");
		}else{
			return error();
		}
	}
	
	
	/**
	 * 项目表删除
	 * @param ids
	 * @return
	 */
	//@Log(title = "项目表删除", action = "111")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/delete")
	@ResponseBody
	public AjaxResult remove(String ids){
		List<String> lista= ConvertUtil.toListStrArray(ids);
		Boolean b=iGoviewProjectService.removeByIds(lista);
		if(b){
			return success();
		}else{
			return error();
		}
	}
	
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody GoviewProject goviewProject)
    {
		Boolean b= iGoviewProjectService.updateById(goviewProject);
        if(b){
        	return success();
        }
        return error();
    }
	
	
	@ApiOperation(value = "项目重命名", notes = "项目重命名")
    @PostMapping("/rename")
    @ResponseBody
    public AjaxResult rename(@RequestBody GoviewProject goviewProject)
    {
		
		LambdaUpdateWrapper<GoviewProject> updateWrapper=new LambdaUpdateWrapper<GoviewProject>();
		updateWrapper.eq(GoviewProject::getId, goviewProject.getId());
		updateWrapper.set(GoviewProject::getProjectName, goviewProject.getProjectName());
		Boolean b=iGoviewProjectService.update(updateWrapper);
		if(b){
        	return success();
        }
		return error();
    }
	
	
	//发布/取消项目状态
    @PutMapping("/publish")
	@ResponseBody
    public AjaxResult updateVisible(@RequestBody GoviewProject goviewProject){
    	if(goviewProject.getState()==-1||goviewProject.getState()==1) {
    	
    		LambdaUpdateWrapper<GoviewProject> updateWrapper=new LambdaUpdateWrapper<GoviewProject>();
    		updateWrapper.eq(GoviewProject::getId, goviewProject.getId());
    		updateWrapper.set(GoviewProject::getState, goviewProject.getState());
    		Boolean b=iGoviewProjectService.update(updateWrapper);
    		if(b){
            	return success();
            }
    		return error();
    	}
    	return error("警告非法字段");
	}
	
    
    @ApiOperation(value = "获取项目存储数据", notes = "获取项目存储数据")
	@GetMapping("/getData")
	@ResponseBody
    public AjaxResult getData(String projectId, ModelMap map)
    {
		GoviewProject goviewProject= iGoviewProjectService.getById(projectId);
		
		GoviewProjectData blogText=iGoviewProjectDataService.getProjectid(projectId);
		if(blogText!=null) {
			GoviewProjectVo goviewProjectVo=new GoviewProjectVo();
			BeanUtils.copyProperties(goviewProject,goviewProjectVo);
			goviewProjectVo.setContent(blogText.getContent());
			return AjaxResult.successData(200,goviewProjectVo).put("msg","获取成功");
		}
		return AjaxResult.successData(200, null).put("msg","无数据");
        
    }
	
	
	
	@ApiOperation(value = "保存项目数据", notes = "保存项目数据")
	@PostMapping("/save/data")
	@ResponseBody
	public AjaxResult saveData(GoviewProjectData data) {
		
		GoviewProject goviewProject= iGoviewProjectService.getById(data.getProjectId());
		if(goviewProject==null) {
			return error("没有该项目ID");
		}
		GoviewProjectData goviewProjectData= iGoviewProjectDataService.getOne(new LambdaQueryWrapper<GoviewProjectData>().eq(GoviewProjectData::getProjectId, goviewProject.getId()));
		if(goviewProjectData!=null) {
			 data.setId(goviewProjectData.getId());
			 iGoviewProjectDataService.updateById(data);
			 return success("数据保存成功");
		}else {
			iGoviewProjectDataService.save(data);
			return success("数据保存成功");
		}
	}
	
	/**
	 * 上传文件
	 * @param object 文件流对象
	 * @param bucketName 桶名
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/upload")
	public AjaxResult upload(@RequestBody MultipartFile object) throws IOException{
		String fileName = object.getOriginalFilename();
		//默认文件格式
		String suffixName=v2Config.getDefaultFormat();
		String mediaKey="";
		Long filesize= object.getSize();
		//文件名字
		String fileSuffixName="";
		if(fileName.lastIndexOf(".")!=-1) {//有后缀
			 suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			 //mediaKey=MD5.create().digestHex(fileName);
			 mediaKey=SnowflakeIdWorker.getUUID();
			 fileSuffixName=mediaKey+suffixName;
		}else {//无后缀
			//取得唯一id
			 //mediaKey = MD5.create().digestHex(fileName+suffixName);
			mediaKey= SnowflakeIdWorker.getUUID();
			//fileSuffixName=mediaKey+suffixName;
		}
		String virtualKey=FileController.getFirstNotNull(v2Config.getXnljmap());
		String absolutePath=v2Config.getXnljmap().get(FileController.getFirstNotNull(v2Config.getXnljmap()));
		SysFile sysFile=new SysFile();
		sysFile.setId(SnowflakeIdWorker.getUUID());
		sysFile.setFileName(fileSuffixName);
		sysFile.setFileSize(Integer.parseInt(filesize+""));
		sysFile.setFileSuffix(suffixName);
		sysFile.setCreateTime(DateUtil.formatLocalDateTime(LocalDateTime.now()));
		String filepath=DateUtil.formatDate(new Date());
		sysFile.setRelativePath(filepath);
		sysFile.setVirtualKey(virtualKey);
		sysFile.setAbsolutePath(absolutePath.replace("file:",""));
		iSysFileService.saveOrUpdate(sysFile);
		File desc = FileController.getAbsoluteFile(v2Config.getFileurl()+File.separator+filepath,fileSuffixName);
		object.transferTo(desc);
		SysFileVo sysFileVo=BeanUtil.copyProperties(sysFile, SysFileVo.class);
		sysFileVo.setFileurl(v2Config.getHttpurl()+sysFile.getVirtualKey()+"/"+sysFile.getRelativePath()+"/"+sysFile.getFileName());
		return successData(200, sysFileVo);
	}
	

}

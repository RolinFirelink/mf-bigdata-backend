package com.arg.smart.report.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import com.arg.smart.report.common.base.BaseController;
import com.arg.smart.report.common.config.V2Config;
import com.arg.smart.report.common.domain.AjaxResult;
import com.arg.smart.report.entity.SysFile;
import com.arg.smart.report.entity.vo.SysFileVo;
import com.arg.smart.report.service.ISysFileService;
import com.arg.smart.report.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 文件上传controller
 * @author fuce 
 * @date: 2018年9月16日 下午4:23:50
 */
@Api(value = "文件上传")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController extends BaseController {


	@Autowired
	private V2Config v2Config;
	@Autowired
	private ISysFileService iSysFileService;

	/**
	 * 删除文件
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	public AjaxResult remove(String ids){
		Boolean b=iSysFileService.removeByIds(StrUtil.split(ids, ',',-1));
		if(b){
			return success();
		}else{
			return error();
		}
	}
	
	
	@ApiOperation(value = "修改", notes = "修改")
	@PutMapping("/update")
	public AjaxResult update(String id,@RequestBody MultipartFile object) throws IllegalStateException, IOException{
		SysFile sysFile=iSysFileService.getById(id);
		if(sysFile!=null){
			String fileurl=sysFile.getAbsolutePath()+sysFile.getRelativePath()+File.separator+sysFile.getFileName();
			object.transferTo(new File(fileurl));
			return success("修改成功");
		}else{
			return error();
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
			mediaKey=SnowflakeIdWorker.getUUID();
			//fileSuffixName=mediaKey+suffixName;
		}
		String virtualKey=getFirstNotNull(v2Config.getXnljmap());
		String absolutePath=v2Config.getXnljmap().get(getFirstNotNull(v2Config.getXnljmap()));
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
		File desc = getAbsoluteFile(v2Config.getFileurl()+File.separator+filepath,fileSuffixName);
		object.transferTo(desc);
		SysFileVo sysFileVo=BeanUtil.copyProperties(sysFile, SysFileVo.class);
		sysFileVo.setFileurl(v2Config.getHttpurl()+sysFile.getVirtualKey()+"/"+sysFile.getRelativePath()+"/"+sysFile.getFileName());
		return AjaxResult.successData(200, sysFileVo);
	}
	
	
	/**
	 * Base64字符串转成图片
	 * @param str
	 * @throws IOException 
	 */
	@PostMapping("/uploadbase64")
	public synchronized AjaxResult uploadbase64(String base64str) throws IOException{
		if(StrUtil.isNotBlank(base64str)){
			String suffixName=v2Config.getDefaultFormat();
			String mediaKey=SnowflakeIdWorker.getUUID();
			String fileSuffixName=mediaKey+suffixName;
			String virtualKey=getFirstNotNull(v2Config.getXnljmap());
			String absolutePath=v2Config.getXnljmap().get(getFirstNotNull(v2Config.getXnljmap()));
			SysFile sysFile=new SysFile();
			sysFile.setId(SnowflakeIdWorker.getUUID());
			sysFile.setFileName(fileSuffixName);
			sysFile.setFileSuffix(suffixName);
			sysFile.setCreateTime(DateUtil.formatLocalDateTime(LocalDateTime.now()));
			String filepath=DateUtil.formatDate(new Date());
			sysFile.setRelativePath(filepath);
			sysFile.setVirtualKey(virtualKey);
			sysFile.setAbsolutePath(absolutePath.replace("file:",""));
			File desc = getAbsoluteFile(v2Config.getFileurl()+File.separator+filepath,fileSuffixName);
			File file=null;
			try {
				 file=Base64.decodeToFile(base64str, desc);
			} catch (Exception e) {
				System.out.println("错误base64："+base64str);
				e.printStackTrace();
			}
			sysFile.setFileSize(Integer.parseInt(file.length()+""));
			iSysFileService.saveOrUpdate(sysFile);
			SysFileVo sysFileVo=BeanUtil.copyProperties(sysFile, SysFileVo.class);
			sysFileVo.setFileurl(v2Config.getHttpurl()+sysFile.getVirtualKey()+"/"+sysFile.getRelativePath()+"/"+sysFile.getFileName());
			return AjaxResult.successData(200, sysFileVo);
		}
		return AjaxResult.error();
		
	}
	
	
	/**
	 * 定制方法
	 * 根据关键字与相对路径获取文件内容 
	 * @param key 访问关键字
	 * @param rpf 相对路径+文件名字
	 * @return
	 */
	@PostMapping("/getFileText")
	public AjaxResult getFileText(String key,String relativePath){
		String absolutePath= v2Config.getXnljmap().get(key).replace("file:", "");
		String fileurl=absolutePath+relativePath;
		try {
			String text=FileUtil.readUtf8String(fileurl);
			return AjaxResult.successData(200, text);
		}catch (IORuntimeException e) {
			return AjaxResult.error("没有该文件");
		}
		catch (Exception e) {
			return AjaxResult.error("报错:"+e.getMessage());
		}
	}
	
	
	/**
	 * 定制方法
	 * 根据关键字与相对路径获取文件内容 
	 * @param key 访问关键字
	 * @param rpf 相对路径+文件名字
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/getFileText302")
	public void getFileText302(String key,String relativePath,HttpServletResponse response) throws IOException{
		String str=v2Config.getHttpurl()+key+"/"+relativePath;
		response.sendRedirect(str);
		
	}
	
	
	
	
	/**
	 * 覆盖上传文件 key与指定路径
	 * @param object 文件流对象
	 * @param bucketName 桶名
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/coverupload")
	public AjaxResult coverupload(@RequestBody MultipartFile object,String key,String relativePath) throws IOException{
		
		String fileName = object.getOriginalFilename();
		String suffixName=v2Config.getDefaultFormat();
		Long filesize= object.getSize();
		//文件名字
		String fileSuffixName="";
		if(fileName.lastIndexOf(".")!=-1) {//有后缀
			 suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			 //mediaKey=MD5.create().digestHex(fileName);
			 //mediaKey=SnowflakeIdWorker.getUUID();
			 fileSuffixName=relativePath.substring(relativePath.lastIndexOf("/")+1,relativePath.length());
		}else {//无后缀
			//取得唯一id
			 //mediaKey = MD5.create().digestHex(fileName+suffixName);
			//mediaKey=SnowflakeIdWorker.getUUID();
			//fileSuffixName=mediaKey+suffixName;
		}
		String virtualKey=key;
		String absolutePath=v2Config.getXnljmap().get(key).replace("file:", "");
		SysFile sysFile=new SysFile();
		sysFile.setId(SnowflakeIdWorker.getUUID());
		sysFile.setFileName(fileSuffixName);
		sysFile.setFileSize(Integer.parseInt(filesize+""));
		sysFile.setFileSuffix(suffixName);
		sysFile.setCreateTime(DateUtil.formatLocalDateTime(LocalDateTime.now()));
		String filepath=relativePath.substring(0,relativePath.lastIndexOf("/"));
		sysFile.setRelativePath(filepath);
		sysFile.setVirtualKey(virtualKey);
		sysFile.setAbsolutePath(absolutePath);
		iSysFileService.saveOrUpdate(sysFile);
		File desc = getAbsoluteFile(absolutePath+filepath,fileSuffixName);
		object.transferTo(desc);
		SysFileVo sysFileVo=BeanUtil.copyProperties(sysFile, SysFileVo.class);
		sysFileVo.setFileurl(v2Config.getHttpurl()+sysFile.getVirtualKey()+"/"+sysFile.getRelativePath()+"/"+sysFile.getFileName());
		return AjaxResult.successData(200, sysFileVo);
	}
	
	
	

	
	/**
	 * 根据文件id查询文件信息json
	 * @param id
	 * @return
	 */
	@GetMapping("/getFileid/{id}")
	public AjaxResult getFileid(@PathVariable("id") String id){
		SysFile sysFile=iSysFileService.getById(id);
		if(sysFile!=null){
			SysFileVo sysFileVo=BeanUtil.copyProperties(sysFile, SysFileVo.class);
			sysFileVo.setFileurl(v2Config.getHttpurl()+sysFile.getVirtualKey()+"/"+sysFile.getRelativePath()+"/"+sysFile.getFileName());
			return AjaxResult.successData(200, sysFileVo);
		}
		return AjaxResult.error("没有该文件");
		
	}
	
	/**
	 * 根据文件id 302跳转到绝对地址
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/getFileid/302/{id}")
	public void getFileid302(@PathVariable("id") String id,HttpServletResponse response) throws IOException{
		SysFile sysFile=iSysFileService.getById(id);
		if(sysFile!=null){
			String str=v2Config.getHttpurl()+sysFile.getVirtualKey()+"/"+sysFile.getRelativePath()+"/"+sysFile.getFileName();
			response.sendRedirect(str);
		}
	}
	
	
	
	
	
	
	/**
	 * 分页查询
	 * @param current
	 * @param size
	 * @return
	 */
	@GetMapping("/list")
	public Object list(long current, long size){
		Page<SysFile> page= new Page<SysFile>(current, size);
		IPage<SysFile> sysFile=iSysFileService.page(page, new LambdaQueryWrapper<SysFile>());
		return sysFile;
	}
	
	
	
	

    /**
     * 获取map中第一个非空数据key
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> K getFirstNotNull(Map<K, V> map) {
        K obj = null;
        for (Entry<K, V> entry : map.entrySet()) {
            obj =  entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }
	
	
    public  final static File getAbsoluteFile(String uploadDir, String filename) throws IOException
    {
        File desc = new File(uploadDir+File.separator + filename);

        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }
	
	
	/**
	 * 获取上传文件的md5
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String getMd5(MultipartFile file) {
	    try {
	        //获取文件的byte信息
	        byte[] uploadBytes = file.getBytes();
	        // 拿到一个MD5转换器
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        byte[] digest = md5.digest(uploadBytes);
	        //转换为16进制
	        return new BigInteger(1, digest).toString(16);
	    } catch (Exception e) {
	        log.error(e.getMessage());
	    }
	    return null;
	}


    
    
}

package com.arg.smart.report.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 读取项目相关配置
 * 
 * @author fuce
 */
@Component
@ConfigurationProperties(prefix = "v2")
public class V2Config {

	/**
	 * 存储路径
	 */
	private String fileurl;
	/**
	 * 请求url
	 */
	private String httpurl;
	/**
	 * 虚拟路径map
	 */
	private Map<String, String> xnljmap;
	
	/**
	 * 默认文件格式
	 */
	private String defaultFormat;
	

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	

	

	public String getHttpurl() {
		return httpurl;
	}

	public void setHttpurl(String httpurl) {
		this.httpurl = httpurl;
	}

	public Map<String, String> getXnljmap() {
		return xnljmap;
	}

	public void setXnljmap(Map<String, String> xnljmap) {
		this.xnljmap = xnljmap;
	}

	public String getDefaultFormat() {
		return defaultFormat;
	}

	public void setDefaultFormat(String defaultFormat) {
		this.defaultFormat = defaultFormat;
	}
	
	
}

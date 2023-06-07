package com.arg.smart.report.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

public class GoviewProjectVo implements Serializable {
    private static final long serialVersionUID = 1L;

	
	@ApiModelProperty(value = "主键")
	private String id;
	
	@ApiModelProperty(value = "项目名称")
	private String projectName;
	
	@ApiModelProperty(value = "项目状态[-1未发布,1发布]")
	private Integer state;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	
	@ApiModelProperty(value = "创建人id")
	private String createUserId;
	
	@ApiModelProperty(value = "删除状态[1删除,-1未删除]")
	private Integer isDelete;
	
	@ApiModelProperty(value = "首页图片")
	private String indexImage;
	
	@ApiModelProperty(value = "项目介绍")
	private String remarks;
	
	private String content;
	
	
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id =  id;
	}
	@JsonProperty("projectName")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName =  projectName;
	}
	@JsonProperty("state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state =  state;
	}
	@JsonProperty("createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime =  createTime;
	}
	@JsonProperty("createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId =  createUserId;
	}
	@JsonProperty("isDelete")
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete =  isDelete;
	}
	@JsonProperty("indexImage")
	public String getIndexImage() {
		return indexImage;
	}

	public void setIndexImage(String indexImage) {
		this.indexImage =  indexImage;
	}
	@JsonProperty("remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks =  remarks;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String dateToStringConvert(Date date) {
		if(date!=null) {
//			return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
			return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
}
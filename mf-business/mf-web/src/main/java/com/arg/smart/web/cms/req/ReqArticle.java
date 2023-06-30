package com.arg.smart.web.cms.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 文章内容
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("文章内容请求参数")
public class ReqArticle {

    @ApiModelProperty(value = "文章分类Id")
    Long categoryId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布开始期")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布结束期")
    private Date endTime;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "作者")
    private String author;
    @ApiModelProperty(value = "来源")
    private String source;
    @ApiModelProperty(value = "需要返回的数量,为0代表全部返回")
    private Integer number;

}

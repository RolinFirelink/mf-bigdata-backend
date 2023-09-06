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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "最早发布时间")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "最晚发布时间")
    private Date endTime;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "作者")
    private String author;
    @ApiModelProperty(value = "来源")
    private String source;
    @ApiModelProperty(value = "用于Es查询的字符串")
    private String stringToEs;
    @ApiModelProperty(value = "需要返回的数量,为0代表全部返回")
    private Integer number;
    @ApiModelProperty(value = "倾向性")
    private Integer inclined;
    @ApiModelProperty(value = "类型（周报日报等）")
    private Integer type;

    @ApiModelProperty(value = "地域")
    private String place;

    @ApiModelProperty(value = "区分字段")
    private Integer flag;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "关键词")
    private String key;

    @ApiModelProperty(value = "来源列表")
    private String sources;


}

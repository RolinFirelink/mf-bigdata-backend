package com.arg.smart.web.cms.entity.info;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "sh_article对象", description = "文章内容")
@Document(indexName = "article")
public class ArticleInfo{
    @ApiModelProperty(value = "唯一ID")
    @Id
    private Long id;
    @ApiModelProperty(value = "标题")
    @Field(name = "title",type = FieldType.Text,analyzer = "ik_max_word")
    private String title;
    @ApiModelProperty(value = "摘要")
    @Field(name = "summary",type = FieldType.Text,analyzer = "ik_max_word")
    private String summary;
    @ApiModelProperty(value = "作者")
    @Field(name = "author",type = FieldType.Text,analyzer = "ik_max_word")
    private String author;
    @ApiModelProperty(value = "来源")
    private String source;
    @ApiModelProperty(value = "分类id")
    private Long categoryId;
    @ApiModelProperty(value = "封面图片地址")
    private String coverImg;
    @ApiModelProperty(value = "状态,0->草稿箱,1->发布")
    private Integer status;
    @ApiModelProperty(value = "是否置顶,0->不置顶,1->置顶")
    private Integer isTop;
    @ApiModelProperty(value = "内容模式,0->标准模式,1->定制模式")
    private Integer contentModel;
    @ApiModelProperty(value = "点击数")
    private Long clickNum;
    @ApiModelProperty(value = "收藏量")
    private Integer collectNum;
    @ApiModelProperty(value = "评论数")
    private Integer commentNum;
    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;
    @ApiModelProperty(value = "下载量")
    private Integer downloadNum;
    @ApiModelProperty(value = "分享数")
    private Integer shareNum;
    @ApiModelProperty(value = "是否允许评论")
    private Integer allowComment;
    @ApiModelProperty(value = "是否允许订阅")
    private Integer allowSubscribe;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "发布开始期")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "发布结束期")
    private Date endTime;
    @ApiModelProperty(value = "信息类型,分为,article:文本;picture:图片类;vidio:视频类")
    private String mediaType;
    @ApiModelProperty(value = "扩展属性 JSON结构")
    private String extendAttribute;
    @ApiModelProperty(value = "多租户组织ID")
    private Long orgId;
    @TableField(exist = false)
    @ApiModelProperty(value = "分类名称")
    private String categoryName;
    @ApiModelProperty(value = "内容")
    @Field(name = "content",type = FieldType.Text,analyzer = "ik_smart")
    private String content;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleteFlag;

    @ApiModelProperty(value = "文章类型（周报、日报等）")
    private Long type;

    @ApiModelProperty(value = "文章发布地域")
    private String place;

    @ApiModelProperty(value = "倾向")
    private Integer inclined;

    @ApiModelProperty(value = "区分字段")
    private Integer flag;
}

package com.arg.smart.web.company.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description: 产品基地
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_base")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_base对象", description = "产品基地")
public class ProductBase extends BaseEntity<Long> {

    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "基地名称")
	private String baseName;
    @ApiModelProperty(value = "基地编码")
	private String baseNo;
    @ApiModelProperty(value = "外键，关联企业表，一个养殖基地只能对应一个企业")
    private Long companyId;
    @ApiModelProperty(value = "联系人")
	private String contacts;
    @ApiModelProperty(value = "联系电话")
	private String contactPhone;
    @ApiModelProperty(value = "电子邮箱")
	private String email;
    @ApiModelProperty(value = "行政区域编码")
	private String areaCode;
    @ApiModelProperty(value = "基地详细地址")
	private String address;
    @ApiModelProperty(value = "1=已删除、0=未删除")
	private Integer deleteFlag;
    @ApiModelProperty(value = "自定义拓展字段JSON 结构")
	private String extendField;
    @ApiModelProperty(value = "备注")
	private String remark;
    @ApiModelProperty(value ="基地面积")
    private Integer area;
    @ApiModelProperty(value = "主要产品")
    private String mainProduct;
    @ApiModelProperty(value = "年产量")
    private BigDecimal annualOutput;
    @ApiModelProperty(value="认证情况（1：绿色，2：无公害，3：地理标志，4：其他）")
    private String attestation;
    @ApiModelProperty(value = "封面图片")
    private String img;
    @ApiModelProperty(value = "公司网址")
    private String websiteAddress;
    @ApiModelProperty(value = "产品分类")
    private Integer flag;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "经度")
    private BigDecimal lat;
    @ApiModelProperty(value = "维度")
    private BigDecimal lng;
    @ApiModelProperty(value = "产量单位")
    private String outputUnit;
    @ApiModelProperty(value = "所在的区")
    private String region;
    @TableField(exist = false)
    @ApiModelProperty(value = "企业")
    private String companyName;

    @ApiModelProperty(value = "交易主体")
    private Integer transactionSubject;
}


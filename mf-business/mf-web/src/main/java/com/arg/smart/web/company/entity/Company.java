package com.arg.smart.web.company.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 企业、供货商、销售商和承运商
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_company")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_company对象", description = "企业、供货商、销售商和承运商")
public class Company extends BaseEntity<Long> {

    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "公司名称")
	private String companyName;
    @ApiModelProperty(value = "公司编码")
	private String companyNo;
    @ApiModelProperty(value = "法人")
	private String juridicalPerson;
    @ApiModelProperty(value = "法人电话")
	private String juridicalPhone;
    @ApiModelProperty(value = "联系人")
	private String contacts;
    @ApiModelProperty(value = "联系电话")
	private String contactPhone;
    @ApiModelProperty(value = "电子邮箱")
	private String email;
    @ApiModelProperty(value = "行政区域名称")
	private String areaName;
    @ApiModelProperty(value = "行政区域编码")
	private String areaCode;
    @ApiModelProperty(value = "公司地址")
	private String address;
    @ApiModelProperty(value = "公司类型(1=供货商、2=销售商、3=承运商）")
	private Integer companyType;
    @ApiModelProperty(value = "公司产品类型(1=肉鸡、2=柑橘、3=兰花、4=对虾、5=菜心、6=预制菜)")
	private Integer productType;
    @ApiModelProperty(value = "1=已启用、0=未启用")
	private Integer enabled;
    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
	private Integer deleteFlag;
    @ApiModelProperty(value = "自定义拓展字段JSON 结构(如果公司类型为承运商，要在该字段加上承运商商业性质标识（1=物流公司、2=运输公司、3=快递公司）)")
	private String extendField;
    @ApiModelProperty(value = "备注")
	private String remark;
}

package com.arg.smart.web.statistics.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @description: 省份供应表Excel
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@ToString
public class ProvinceSupplyExcel {
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "省份",index = 1)
	private String province;
    @ExcelProperty(value = "供应量单位",index = 2)
	private String upplyUnit;
    @ExcelProperty(value = "供应量类型",index = 3)
	private Integer flag;
    @ExcelProperty(value = "产量",index = 4)
	private String upply;
    @ExcelProperty(value = "产品",index = 5)
	private String product;
}

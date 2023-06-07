package com.arg.smart.web.customer.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author zsj
 * @description: 客户消费行为excel实体
 * @date: 2023-06-08
 * @version: V1.0.0
 */
@Data
@ToString
public class CustomerBehaviorExcel {

    @ExcelProperty(value = "唯一ID", index = 0)
    private Long id;
    @ExcelProperty(value = "客户编号", index = 1)
    private Long customerId;
    @ExcelProperty(value = "行为类型", index = 2)
    private Integer type;
    @ExcelProperty(value = "扩展信息", index = 3)
    private String extendInfo;
    @ExcelProperty(value = "备注", index = 4)
    private String remark;
    @ExcelProperty(value = "产品分类", index = 5)
    private Integer flag;
}

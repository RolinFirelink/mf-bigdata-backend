package com.arg.smart.web.report.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author GlowingTree
 * @Description 消费者产品评价值对象
 * @Date 27/08/2023 20:12
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerEvaluationVO {
    @ApiModelProperty("主键 ID")
    private Long id;
    @ApiModelProperty("区分字段")
    private Integer flag;
    @ApiModelProperty("消费者 ID")
    private Long customerId;
    @ApiModelProperty("消费者名称")
    private String customerName;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("评价等级 0: 差评, 1: 中评, 2: 好评")
    private Integer evaluate;
    @ApiModelProperty("评价时间")
    private Date evaluateTime;
}

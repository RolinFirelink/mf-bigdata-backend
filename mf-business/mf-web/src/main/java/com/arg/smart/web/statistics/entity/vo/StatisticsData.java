package com.arg.smart.web.statistics.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 统计数据
 * @author zsj
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "统计数据", description = "统计数据")
public class StatisticsData {

    private String title;

    private Integer count;


}

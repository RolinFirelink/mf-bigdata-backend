package com.arg.smart.web.docking.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("惠农网对接参数")
public class ReqDocking {

    private  String cateName;
    private String startDate;
    private String endDate;
    private Integer pageNum;
    private Integer pageSize;
    private  String startYear;
    private String endYear;
    private String dateMonth;
    private String yearDate;

    private String startMonth;
    private String endMonth;

    private String collectDate;

    private Integer type;
}

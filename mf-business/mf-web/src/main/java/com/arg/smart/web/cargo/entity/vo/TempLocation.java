package com.arg.smart.web.cargo.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class TempLocation {
    @ApiModelProperty(value = "公司id")
    private String companyId;
    @ApiModelProperty(value = "城市编号")
    private String cityCode;

}

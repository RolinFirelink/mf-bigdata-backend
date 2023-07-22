package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.bouncycastle.cms.PasswordRecipientId;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
@Accessors(chain = true)
public class ProductionScaleResponseData {
    @ApiModelProperty(value = "产地名称")
    private String baseName;

    @ApiModelProperty("规模单位")
    private String unit;

    @ApiModelProperty(value = "产品生产规模")
    private List<ProductionScale> productProductionScale;

    @ApiModelProperty(value = "所属城市")
    private String city;

    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;

    @ApiModelProperty(value = "所属区")
    private String region;
}

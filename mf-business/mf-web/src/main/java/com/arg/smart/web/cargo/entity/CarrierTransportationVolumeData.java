package com.arg.smart.web.cargo.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Data
@ToString
@TableName("sh_carrier_data")
@Accessors(chain = true)
public class CarrierTransportationVolumeData {
    @ApiModelProperty(value = "产品类别")
    private Integer flag;
    @ApiModelProperty(value = "承运商ID")
    private Long companyId;
    @ApiModelProperty(value = "承运商")
    private String companyName;
    @ApiModelProperty(value = "产品运输总量")
    private BigDecimal transportationQuantityTall;
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateMeasureTatol;
}


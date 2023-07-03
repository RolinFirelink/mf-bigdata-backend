package com.arg.smart.web.cargo.entity.vo;

import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ToString
@Accessors(chain = true)
public class CarrierTransportationVolumeDataList {
    @ApiModelProperty(value = "质量单位")
    String massUnit;
    @ApiModelProperty(value = "产品运输质量集合")
    List<CarrierTransportationVolumeData> carrierTransportationVolumeDataList;


}


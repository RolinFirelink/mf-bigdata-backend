package com.arg.smart.web.company.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 企业、供货商、销售商和承运商
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("企业、供货商、销售商和承运商请求参数")
public class ReqCompany {

    private Integer companyType;

    private Integer productType;

}

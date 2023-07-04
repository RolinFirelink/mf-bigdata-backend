package com.arg.smart.web.cargo.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.entity.vo.CarrierInformation;
import com.arg.smart.web.cargo.entity.vo.TransportInformation;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.req.ReqCompany;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
public interface ProductCirculationDataService extends IService<ProductCirculationData> {

    PageResult<ProductCirculationData> selectListByCondition(ReqProductCirculationData reqProductCirculationData);

    Result<Map<String, TransportInformation>> getTransportInformation(Integer flag);

    Result<List<CarrierInformation>> getCarrierInformation(Integer flag);
}

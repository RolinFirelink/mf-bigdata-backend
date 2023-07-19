package com.arg.smart.web.cargo.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.cargo.entity.vo.*;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
public interface ProductCirculationDataService extends IService<ProductCirculationData> {



    List<ProductCirculationData> selectListByCondition(ReqProductCirculationData reqProductCirculationData);

   // Map<String, Double> selectPercentageByFlag(Integer flag);

    List<TransportationProportion> selectPercentageByFlag (Integer flag);
    BigDecimal selectAverageShippingPriceByFlag(Integer flag);

    Map<String, Double> selectChannelByFlag(int flag);

    Map<String, Integer> selectCompanyQuantity(Integer flag);

    List<CirculationTransportationFrequencyData> selectOneOfCirculationData(Date receivingDate);

    List<CirculationTransportationFrequencyDataList> creatCirculationTransportationFrequencyDataList(Integer flag);


    List<ProductCirculationData> selectOfOrderInformationList(Integer flag);

    /*
     * 发货相关的运输订单信息
     * */
    List<ProductCirculationData> findOrderInformationList(Integer flag, String shippinglocation);

    List<ProductCirculationData> selectOfShipmentOrderData(Integer flag);


    List<LocationLatLon> selectLocationLatLon(Integer flag);

    PageResult<ProductCirculationData> list(ReqProductCirculationData reqProductCirculationData);
    List<SalesFlowLatLng> selectSalesFlowByFlag(Integer flag);
}

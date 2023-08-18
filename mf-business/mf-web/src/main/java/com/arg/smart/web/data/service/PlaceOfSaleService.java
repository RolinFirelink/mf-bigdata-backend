package com.arg.smart.web.data.service;

import com.arg.smart.web.data.entity.PlaceOfSale;
import com.arg.smart.web.data.req.ReqPlaceOfSale;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 销售地表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
public interface PlaceOfSaleService extends IService<PlaceOfSale> {

    List<PlaceOfSale> pagelist(ReqPlaceOfSale reqPage);

    boolean savePlaceOfSale(PlaceOfSale placeOfSale);

    boolean updatePlaceOfSaleById(PlaceOfSale placeOfSale);
}

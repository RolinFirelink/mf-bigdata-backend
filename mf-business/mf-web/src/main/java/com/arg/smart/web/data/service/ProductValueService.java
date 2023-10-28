package com.arg.smart.web.data.service;

import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.ProductValue;
import com.arg.smart.web.data.entity.vo.ProductValueTotal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProductValueService extends IService<ProductValue> {
    List<ProductValueTotal> selectProductValueTotal(Integer flag,Integer date);
    List<ProductValue> selectProductValue(Integer flag,String city);
}

package com.arg.smart.web.company.service;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.vo.BaseVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 产品基地
 * @author lwy
 * @date: 2023-05-18
 * @version: V1.0.0
 */
public interface ProductBaseService extends IService<ProductBase> {
    List<ProductBase> getOptions();

    List<BaseVO> selectListByCondition(ReqProductBase reqProductBase);

    List<ProductBase> list(ReqProductBase reqProductBase);

    Map<String, Long> queryyield();
}

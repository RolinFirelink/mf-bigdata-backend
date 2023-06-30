package com.arg.smart.web.cargo.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.req.ReqCompany;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
public interface ProductCirculationDataService extends IService<ProductCirculationData> {

    PageResult<ProductCirculationData> selectListByCondition(ReqProductCirculationData reqProductCirculationData);
}

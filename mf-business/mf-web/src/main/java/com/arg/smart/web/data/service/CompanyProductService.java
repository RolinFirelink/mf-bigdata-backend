package com.arg.smart.web.data.service;

import com.arg.smart.web.data.entity.CompanyProduct;
import com.arg.smart.web.data.entity.vo.AvgProductValue;
import com.arg.smart.web.data.req.ReqCompanyProduct;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

public interface CompanyProductService extends IService<CompanyProduct> {
    List<CompanyProduct> selectCompanyProductList(Integer flag);
    List<AvgProductValue> companyProductValue(String productName);
    List<CompanyProduct> list(ReqCompanyProduct reqCompanyProduct);
}

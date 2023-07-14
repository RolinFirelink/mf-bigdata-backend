package com.arg.smart.web.company.service;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.entity.ProductInfo;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.req.ReqProductInfo;
import com.arg.smart.web.company.vo.BaseVO;
import com.arg.smart.web.company.vo.ProductVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProductInfoService  extends IService<ProductInfo> {
    List<ProductVo> getProductInfoarea(Integer flag,String city);
}

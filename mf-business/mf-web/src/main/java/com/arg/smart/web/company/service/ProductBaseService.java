package com.arg.smart.web.company.service;

import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.req.ReqProductBase;
import com.baomidou.mybatisplus.extension.service.IService;
<<<<<<< HEAD
import java.util.List;

=======


import java.util.List;


>>>>>>> 67b29d8a2b48656ad20c4945eb1a8732f8df0432
/**
 * @description: 产品基地
 * @author lwy
 * @date: 2023-05-18
 * @version: V1.0.0
 */
public interface ProductBaseService extends IService<ProductBase> {

<<<<<<< HEAD
    List<ProductBase> list(ReqProductBase reqProductBase);
=======
>>>>>>> 67b29d8a2b48656ad20c4945eb1a8732f8df0432

    List<ProductBase> getOptions();

    List<ProductBase>  SelectListByCondition(ReqProductBase reqProductBase);

}

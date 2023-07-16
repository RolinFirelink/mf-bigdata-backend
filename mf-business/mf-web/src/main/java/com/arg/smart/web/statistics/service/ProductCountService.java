package com.arg.smart.web.statistics.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.ProductCount;
import com.arg.smart.web.statistics.req.ReqProductCount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.javafx.collections.MappingChange;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: 城市产品生产统计表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
public interface ProductCountService extends IService<ProductCount> {

    List<ProductCount> list(ReqProductCount reqProductCount);

    PageResult<ProductCount> listPage(ReqProductCount reqProductCount);
}

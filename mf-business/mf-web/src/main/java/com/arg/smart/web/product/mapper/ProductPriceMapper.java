package com.arg.smart.web.product.mapper;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netscape.javascript.JSObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
public interface ProductPriceMapper extends BaseMapper<ProductPrice> {

    @Select("select flag,max(time) as time,avg(price) as price,unit ,avg(lifting) as lifting from sh_product_price group by flag,unit")
    List<ProductPrice> getMaxTimePrice();
}

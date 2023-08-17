package com.arg.smart.web.product.entity;

import com.arg.smart.web.product.entity.vo.ProductPriceTrend;
import lombok.Data;

import java.util.List;

@Data
public class ProductPriceTrendData {

    private String product;

    private List<ProductPriceTrend> productPriceTrends;
}


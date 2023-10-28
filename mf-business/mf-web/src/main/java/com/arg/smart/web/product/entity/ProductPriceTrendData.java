package com.arg.smart.web.product.entity;

import com.arg.smart.web.product.entity.vo.ProductPriceTrend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceTrendData {

    private String product;

    private List<ProductPriceTrend> productPriceTrends;


}


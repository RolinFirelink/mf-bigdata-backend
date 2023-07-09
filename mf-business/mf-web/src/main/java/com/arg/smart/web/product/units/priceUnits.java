package com.arg.smart.web.product.units;

import java.math.BigDecimal;

public class priceUnits {
    public static BigDecimal transform(String price){
        String trim = price.trim();
        int mid = trim.indexOf("/");
        double v = Double.parseDouble(trim.substring(0,mid-1));
        return BigDecimal.valueOf(v);
    }
}

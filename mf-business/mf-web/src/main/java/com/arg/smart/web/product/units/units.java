package com.arg.smart.web.product.units;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class units {
    public static BigDecimal stringToBdm(String price){
        String trim = price.trim();
        int mid = trim.indexOf("/");
        double v = Double.parseDouble(trim.substring(0,mid-1));
        return BigDecimal.valueOf(v);
    }

    public static String stringToUnit(String price){
        String trim = price.trim();
        int mid = trim.indexOf("/");
        return trim.substring(mid+1);
    }

    public static Date stringToDate(String date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.arg.smart.web.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date stringToDateTime(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
           e.printStackTrace();
        }
        return null;
    }
}

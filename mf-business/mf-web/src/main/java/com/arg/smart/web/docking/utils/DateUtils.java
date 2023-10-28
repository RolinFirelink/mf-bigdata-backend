package com.arg.smart.web.docking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    // 将日期字符串转换为Date对象
    public static Date parseStringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    // 将Date对象转换为指定格式的日期字符串
    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

}

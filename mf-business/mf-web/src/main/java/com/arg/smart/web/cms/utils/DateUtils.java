package com.arg.smart.web.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    //将yyyy-MM-dd转为yyyy-MM-dd 00:00:00
    public static Date setStartTime(Date startTime) {
        if (startTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    //将yyyy-MM-dd转为yyyy-MM-dd 23:59:59
    public static Date setEndTime(Date endTime) {
        if (endTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }
        return null;
    }
}

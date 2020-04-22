package com.hrtp.system.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TimeUtils
 * description 针对昨日商户数据时间工具抽取
 * create by lxj 2018/8/23
 **/
public class TimeUtils {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    public static String[] getYdyAndFday(){
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        String yesterday = format.format(c.getTime());
        String firstDay = "";
        if (format.format(currentDate).substring(4, 8).equals("0101")) {
            c.setTime(currentDate);
            c.set(Calendar.YEAR, -1);
            firstDay = format.format(c.getTime());
        } else {
            c.clear();
            c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            firstDay = format.format(c.getTime());
        }
        return new String[]{yesterday,firstDay};
    }

}

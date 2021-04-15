package com.green.todearmail.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    private DateUtils() {
    }

    /**
     * @param @param  sdate
     * @param @param  bdate
     * @param @return 设定文件
     * @throws
     * @Description: TODO(计算两个日期 【 日期类型 】 之间的时间距离)
     */
    public static Map<String, Long> timesBetween(Date sdate, Date bdate) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long day = 0;

        long hour = 0;

        long min = 0;

        long sec = 0;

        long diff = 0;

        try {

            sdate = df.parse(df.format(sdate));

            bdate = df.parse(df.format(bdate));
            long stime = sdate.getTime();
            long btime = bdate.getTime();
            if (stime > btime) {

                diff = stime - btime;

            } else {

                diff = btime - stime;

            }

            day = diff / (24 * 60 * 60 * 1000);

            hour = diff / (60 * 60 * 1000) - day * 24;

            min = diff / (60 * 1000) - day * 24 * 60 - hour * 60;

            sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;

        } catch (ParseException e) {

            e.printStackTrace();

        }

        Map<String, Long> timeMap = new HashMap<>();

        timeMap.put("Day", day);

        timeMap.put("Hour", hour);

        timeMap.put("Min", min);

        timeMap.put("Sec", sec);

        return timeMap;

    }
}

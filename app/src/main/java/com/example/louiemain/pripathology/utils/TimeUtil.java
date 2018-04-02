package com.example.louiemain.pripathology.utils;/**
 * @description
 * @author&date Created by louiemain on 2018/4/1 13:56
 */

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 时间相关
 * @Author: louiemain
 * @Created: 2018/4/1 13:56
 **/
public class TimeUtil {
    /**
     * TimeStamp2StringTimeStamp
     * @param time
     * @return
     */
    public String TimeStamp2String(Timestamp time) {
        return String.valueOf(time.getTime());
    }

    /**
     * 格式化时间格式"2018-04-02T06:47:47.000+0000"
     * @param date
     * @return
     * @throws ParseException
     */
    public long formatTime(String date) throws ParseException {
        Date parse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        // 转换到中国时间
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        return calendar.getTimeInMillis();
    }
}

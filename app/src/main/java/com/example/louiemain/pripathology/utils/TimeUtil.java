package com.example.louiemain.pripathology.utils;/**
 * @description
 * @author&date Created by louiemain on 2018/4/1 13:56
 */

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}

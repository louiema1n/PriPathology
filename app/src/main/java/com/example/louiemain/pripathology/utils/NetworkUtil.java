package com.example.louiemain.pripathology.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description: 网络操作相关工具类
 * @Author: louiemain
 * @Created: 2018-04-10 10:32
 **/
public class NetworkUtil {
    
    /** 
     * @Description: 是否为WiFi环境 
     * @Author: louiemain 
     * @Date: 2018-04-10 10:42
     * @param context 
     * @return: boolean 
     */ 
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}

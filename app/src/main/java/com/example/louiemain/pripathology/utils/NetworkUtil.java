package com.example.louiemain.pripathology.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

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
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /** 
     * @Description: 获取WiFi连接名称 
     * @Author: louiemain 
     * @Date: 2018-04-10 11:01 
     * @param context 
     * @return: java.lang.String 
     */ 
    public static String getWifiSSID(Context context) {
        if (isWifi(context)) {
            // 连接WiFi
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            return info != null ? info.getSSID() : null;
        }
        return null;
    }
}

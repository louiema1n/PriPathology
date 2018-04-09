package com.example.louiemain.pripathology.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description: APK操作相关操作工具类
 * @Author: louiemain
 * @Created: 2018-04-09 09:17
 **/
public class APKUtil {

    /**
     * @Description: 获取当前apk versionCode
     * @Author: louiemain
     * @Date: 2018-04-09 9:22
     * @param context
     * @return: java.lang.String
     */
//    public static String getVersionCode(Context context) {
//        int versionCode = 0;
//        try {
//            versionCode = context.getPackageManager().
//                    getPackageInfo(context.getPackageName(), 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return versionCode + "";
//    }

    /** 
     * @Description: 获取当前apk versionName 
     * @Author: louiemain 
     * @Date: 2018-04-09 9:25
     * @param context 
     * @return: java.lang.String 
     */ 
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}

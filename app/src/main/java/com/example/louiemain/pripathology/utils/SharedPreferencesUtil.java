package com.example.louiemain.pripathology.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description: 文件存储相关操作工具类
 * @Author: louiemain
 * @Created: 2018-03-30 17:21
 **/
public class SharedPreferencesUtil {
    private Context context;

    public SharedPreferencesUtil(Context context) {
        this.context = context;
    }

    public void writeDatabaseVer(Integer databaseVer) {
        // 0-默认模式（表示只有当前的应用程序才可以对当前这个SharedPreferences文件进行读写）
        SharedPreferences preferences = context.getSharedPreferences("sys_arg", 0);
        // 获取文件存储编辑对象
        SharedPreferences.Editor editor = preferences.edit();
        // 写入文件
        editor.putInt("databaseVer", databaseVer);
        // 提交
        editor.commit();
    }

    public Integer getDatabaseVer() {
        return context.getSharedPreferences("sys_arg", 0).getInt("databaseVer", 0);
    }
}

package com.example.louiemain.pripathology.base;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 21:11
 */

import android.content.Context;
import android.view.View;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 通用Pager类
 * @Author: louiemain
 * @Created: 2018/3/26 21:11
 **/
public abstract class BasePager {

    public final Context context;   // 上下文
    public final View rootView;     // 视图

    // 是否已经初始化数据，不是static 每个实例化单独继承一个
    public boolean isInitData;

    public BasePager(Context context) {
        this.context = context;
        this.rootView = initView();
    }

    /**
     * 初始化视图-由子类自行重写
     * @return
     */
    public abstract View initView();

    /**
     * 初始化数据-由子类自行决定是否重写
     */
    public void initData() {

    }
}

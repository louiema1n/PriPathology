package com.example.louiemain.pripathology.pager;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 21:15
 */

import android.content.Context;
import android.view.View;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.base.BasePager;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 练习页面
 * @Author: louiemain
 * @Created: 2018/3/26 21:15
 **/
public class ExamPager extends BasePager {

    public ExamPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.layout_exam, null);
        return view;
    }
}

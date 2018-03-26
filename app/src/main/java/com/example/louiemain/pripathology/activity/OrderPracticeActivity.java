package com.example.louiemain.pripathology.activity;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 22:27
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.base.BasePracticeActivity;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 顺序练习
 * @Author: louiemain
 * @Created: 2018/3/26 22:27
 **/
public class OrderPracticeActivity extends BasePracticeActivity {

    private int id = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.generatePractice(String.valueOf(id));
    }

    @Override
    protected void leftFlyingHandle() {
        nextTopic();
    }

    @Override
    protected void rightFlyingHandle() {
        upTopic();
    }

    /**
     * 下一个题目
     */
    public void nextTopic() {
        id++;
        // 刷新
        onDestroy();
        onCreate(null);
        // 生成随机id
        if (id >= 2140) {
            id = 2140;
            finish();
            Toast.makeText(this, "恭喜你！完成了所有的题目练习。", Toast.LENGTH_SHORT).show();
        } else {
            super.generatePractice(String.valueOf(id));
        }
    }

    /**
     * 上一个题目
     */
    public void upTopic() {
        id--;
        if (id < 1) {
            id = 1;
            Toast.makeText(this, "这是第一个题目，别翻了。", Toast.LENGTH_SHORT).show();
        } else {
            onDestroy();
            onCreate(null);
            super.generatePractice(String.valueOf(id));
        }
    }

    @Override
    public String handleName(Cursor cursorExam) {
        // 去掉前面的数字前面加上id
        String name = cursorExam.getString(cursorExam.getColumnIndex("name"));
        name = id + name.replaceFirst("\\d+", "");
        return name;
    }

    @Override
    protected String getTlTitle() {
        return getString(R.string.order) + getString(R.string.title_practice);
    }

    @Override
    public String getNumber() {
        return "当前题目: " + id + "/2140";
    }

}

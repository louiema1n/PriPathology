package com.example.louiemain.pripathology.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.base.BasePracticeActivity;

import java.util.Random;

public class RandomPracticeActivity extends BasePracticeActivity {

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = String.valueOf(new Random().nextInt(2140) + 1);
        super.onCreate(savedInstanceState);
        super.generatePractice(id);

    }

    @Override
    protected void leftFlyingHandle() {
        // 刷新
        onDestroy();
        onCreate(null);
        // 生成随机id
        id = String.valueOf(new Random().nextInt(2140) + 1);
        super.generatePractice(id);
    }

    @Override
    protected void rightFlyingHandle() {

    }

    @Override
    public String handleName(Cursor cursorExam) {
        // 去掉前面的数字
        String name = cursorExam.getString(cursorExam.getColumnIndex("name"));
        name = name.replaceFirst("\\d+.", "");
        return name;
    }

    @Override
    protected String getTlTitle() {
        return this.getString(R.string.random) + this.getString(R.string.title_practice);
    }

    @Override
    public String getNumber() {
        return "随机题目: " + id;
    }
}

package com.example.louiemain.pripathology.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.louiemain.pripathology.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 延迟时间执行（当前线程）
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 1000);
    }

    /**
     * @Description: 启动主Activity
     * @Author: louiemain
     * @Date: 2018-03-12 17:33
     * @param
     * @return: void
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // 关闭当前Activity
        finish();
    }
}

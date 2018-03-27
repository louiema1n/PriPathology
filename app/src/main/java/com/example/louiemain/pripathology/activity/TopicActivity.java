package com.example.louiemain.pripathology.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity;
import com.example.louiemain.pripathology.adapter.TopicBaseAdapter;

public class TopicActivity extends BaseAppCompatActivity {

    private ViewPager vp_topic_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_topic);
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic;
    }

    private void initView() {
        vp_topic_container = (ViewPager) findViewById(R.id.vp_topic_container);
        // 设置适配器
        vp_topic_container.setAdapter(new TopicBaseAdapter(this));
        // 设置预加载
        vp_topic_container.setOffscreenPageLimit(3);
    }
}

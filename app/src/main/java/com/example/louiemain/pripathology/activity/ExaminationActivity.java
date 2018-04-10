package com.example.louiemain.pripathology.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity;
import com.example.louiemain.pripathology.adapter.ExamTopicRVAdapter;
import com.example.louiemain.pripathology.dao.TopicDao;
import com.example.louiemain.pripathology.domain.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExaminationActivity extends BaseAppCompatActivity {

    private Toolbar tb_base_toolbar;
    private RecyclerView rv_exam_container;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_examination);
        getToolbarTitle().setText(this.getString(R.string.examination));

        initView();
        initData();
    }

    private void initData() {

        // 随机生成topic
        List<Topic> topics = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            topics.add(new TopicDao(this)
                    .generatePractice(String.valueOf(new Random().nextInt(2140) + 1)));
        }

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ExamTopicRVAdapter adapter = new ExamTopicRVAdapter(topics);
        // 设置布局管理器
        rv_exam_container.setLayoutManager(layoutManager);
        // 设置adapter
        rv_exam_container.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_examination;
    }

    private void initView() {
        tb_base_toolbar = (Toolbar) findViewById(R.id.tb_base_toolbar);
        rv_exam_container = (RecyclerView) findViewById(R.id.rv_exam_container);
    }
}

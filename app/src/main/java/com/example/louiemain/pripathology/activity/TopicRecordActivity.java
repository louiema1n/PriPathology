package com.example.louiemain.pripathology.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity;
import com.example.louiemain.pripathology.adapter.TopicRecordRVAdapter;
import com.example.louiemain.pripathology.domain.TopicRecord;

import java.util.ArrayList;
import java.util.List;

public class TopicRecordActivity extends BaseAppCompatActivity {

    private RecyclerView rv_container;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_topic_record);
        getToolbarTitle().setText(getString(R.string.record_topic));
        initView();
        initData();
    }

    private void initData() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        List<TopicRecord> topicRecords = initTopicRecords();
        adapter = new TopicRecordRVAdapter(topicRecords);

        // 设置布局管理器
        rv_container.setLayoutManager(layoutManager);
        // 设置adapter
        rv_container.setAdapter(adapter);
    }

    /**
     * 初始化需要展示的TopicRecord数据
     * @return
     */
    private List<TopicRecord> initTopicRecords() {
        List<TopicRecord> topicRecords = new ArrayList<>();
        TopicRecord topicRecord = null;
        for (int i = 1000; i < 1015; i++) {
            topicRecord = new TopicRecord();

            topicRecord.setName("我是题目" + i);
            topicRecord.setNumber("" + i);
            topicRecord.setRightAnswer("A" + i);
            topicRecord.setSelectAnswer("C" + i);
            topicRecord.setTime("2018-3-29 16:28:15" + i);

            topicRecords.add(topicRecord);
        }
        return topicRecords;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_record;
    }

    private void initView() {
        rv_container = (RecyclerView) findViewById(R.id.rv_container);

    }
}

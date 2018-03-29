package com.example.louiemain.pripathology.activity;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity;
import com.example.louiemain.pripathology.adapter.TopicRecordRVAdapter;
import com.example.louiemain.pripathology.domain.TopicRecord;
import com.example.louiemain.pripathology.utils.DataBaseHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TopicRecordActivity extends BaseAppCompatActivity {

    private RecyclerView rv_container;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private static final String TABLE_TOPIC_RECORD = "topic_record";
    private SQLiteDatabase database;
    private DataBaseHelper helper;

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_topic_record);
        tag = getIntent().getStringExtra("tag");
        // 设置title
        switch (tag) {
            case "order":
                getToolbarTitle().setText(getString(R.string.record_order));
                break;
            case "random":
                getToolbarTitle().setText(getString(R.string.record_random));
                break;
        }

        initView();
        initData();
    }

    private void initData() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        helper = new DataBaseHelper(this, "topic", null, 2);

        List<TopicRecord> topicRecords = initTopicRecords();
        adapter = new TopicRecordRVAdapter(topicRecords);

        // 设置布局管理器
        rv_container.setLayoutManager(layoutManager);
        // 设置adapter
        rv_container.setAdapter(adapter);

    }

    /**
     * 初始化需要展示的TopicRecord数据
     *
     * @return
     */
    private List<TopicRecord> initTopicRecords() {
        List<TopicRecord> topicRecords = new ArrayList<>();
        TopicRecord topicRecord;

        // 得到数据库操作对象-读取模式
        database = helper.getReadableDatabase();
        try {
            Cursor cursor = database.query(TABLE_TOPIC_RECORD,
                    new String[]{"id", "name", "number", "rightAnswer", "time", "selectAnswer", "target"},
                    "target = ?",
                    new String[] {String.valueOf(tag.equals("order") ? 0 : 1)},
                    null,
                    null,
                    null);

            // cursor置顶
            cursor.moveToFirst();
            if (cursor != null) {
                // cursor遍历
                while (!cursor.isAfterLast()) {
                    topicRecord = new TopicRecord();

                    topicRecord.setName(cursor.getString(cursor.getColumnIndex("name")));
                    topicRecord.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
                    topicRecord.setRightAnswer(cursor.getString(cursor.getColumnIndex("rightAnswer")));
                    topicRecord.setTime(new Timestamp(cursor.getLong(cursor.getColumnIndex("time"))));
                    topicRecord.setSelectAnswer(cursor.getString(cursor.getColumnIndex("selectAnswer")));
                    topicRecord.setTarget(cursor.getInt(cursor.getColumnIndex("target")));

                    topicRecords.add(topicRecord);
                    cursor.moveToNext();
                }
            } else {
                Log.i("msg", "未查询到数据Exam");
            }
            return topicRecords;
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(this, "未查询到数据，请先同步远程数据库。", Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }
        return null;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_record;
    }

    private void initView() {
        rv_container = (RecyclerView) findViewById(R.id.rv_container);

    }
}

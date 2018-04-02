package com.example.louiemain.pripathology.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.dao.TopicDao;
import com.example.louiemain.pripathology.domain.Topic;
import com.example.louiemain.pripathology.domain.TopicRecord;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class ShowTopicRecordActivity extends com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity {

    private TextView tv_name;
    private RadioButton rb_a;
    private RadioButton rb_b;
    private RadioButton rb_c;
    private RadioButton rb_d;
    private RadioButton rb_e;
    private RadioGroup rg_option;
    private TextView tv_commons;
    private TextView tv_anser;
    private TextView tv_analysis;
    private TextView tv_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_topic_record);
        initView();
        getToolbarTitle().setText(getString(R.string.view_topic_record));
        // 获取intent中的数据
        Bundle bundle = getIntent().getBundleExtra("topic_record_bundle");
        TopicRecord topicRecord = (TopicRecord) bundle.getSerializable("topic_record");
        initData(topicRecord);
    }

    private void initData(TopicRecord topicRecord) {

        // 根据id获取topic
        String id = String.valueOf(topicRecord.getNumber());
        Topic topic = new TopicDao(this).generatePractice(id);

        tv_name.setText(topicRecord.getName());
        rb_a.setText(topic.getA());
        rb_b.setText(topic.getB());
        rb_c.setText(topic.getC());
        rb_d.setText(topic.getD());
        rb_e.setText(topic.getE());
        tv_commons.setText(topic.getCommons());
        tv_anser.setText("正确答案：" + topic.getAnser());
        tv_analysis.setText(topic.getAnalysis());
        tv_number.setText(id);

        // 禁用所有选项
        for (int i = 0; i < rg_option.getChildCount(); i++) {
            rg_option.getChildAt(i).setEnabled(false);
        }
        // 显示正确答案
        RadioButton btnRight = (RadioButton) rg_option.getChildAt(getPosition(topic.getAnser()));
        // 不能使用button.setTextColor(R.color.colorAccent);
        btnRight.setTextColor(this.getResources().getColor(R.color.colorRbRight, null));

        // 显示错误答案
        RadioButton btnWrong = (RadioButton) rg_option.getChildAt(getPosition(topicRecord.getSelectAnswer()));
        // 不能使用button.setTextColor(R.color.colorAccent);
        btnWrong.setTextColor(this.getResources().getColor(R.color.colorAccent, null));
    }

    /**
     * 获取选项的index
     *
     * @param option
     * @return
     */
    private int getPosition(String option) {
        int position = 0;
        switch (option) {
            case "A":
                position = 0;
                break;
            case "B":
                position = 1;
                break;
            case "C":
                position = 2;
                break;
            case "D":
                position = 3;
                break;
            case "E":
                position = 4;
                break;
        }
        return position;
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        rb_a = (RadioButton) findViewById(R.id.rb_a);
        rb_b = (RadioButton) findViewById(R.id.rb_b);
        rb_c = (RadioButton) findViewById(R.id.rb_c);
        rb_d = (RadioButton) findViewById(R.id.rb_d);
        rb_e = (RadioButton) findViewById(R.id.rb_e);
        rg_option = (RadioGroup) findViewById(R.id.rg_option);
        tv_commons = (TextView) findViewById(R.id.tv_commons);
        tv_anser = (TextView) findViewById(R.id.tv_anser);
        tv_analysis = (TextView) findViewById(R.id.tv_analysis);
        tv_number = (TextView) findViewById(R.id.tv_number);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_topic_record;
    }
}

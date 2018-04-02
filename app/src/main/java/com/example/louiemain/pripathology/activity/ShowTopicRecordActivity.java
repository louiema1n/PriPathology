package com.example.louiemain.pripathology.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;

public class ShowTopicRecordActivity extends com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity {

    private TextView tv_toolbar_title;
    private Toolbar tb_base_toolbar;
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
    private LinearLayout ly_result_analysis;
    private TextView tv_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_topic_record);
        initView();
        getToolbarTitle().setText(getString(R.string.view_topic_record));
    }

    private void initView() {
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tb_base_toolbar = (Toolbar) findViewById(R.id.tb_base_toolbar);
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
        ly_result_analysis = (LinearLayout) findViewById(R.id.ly_result_analysis);
        tv_number = (TextView) findViewById(R.id.tv_number);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_topic_record;
    }
}

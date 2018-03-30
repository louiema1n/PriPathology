package com.example.louiemain.pripathology.view;/**
 * @description
 * @author&date Created by louiemain on 2018/3/28 16:52
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.dao.TopicRecordDao;
import com.example.louiemain.pripathology.domain.Topic;
import com.example.louiemain.pripathology.domain.TopicRecord;
import com.example.louiemain.pripathology.utils.DataBaseHelper;

import java.util.Date;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 题目公共fragment视图
 * @Author: louiemain
 * @Created: 2018/3/28 16:52
 **/
public class TopicFragmentView extends Fragment {

    private Bundle bundle;
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

    // 正确答案
    private String rightAnser;
    // 正确答案所属下标
    private int position;

    private Topic topic;


    private static Context context;
    private String tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_base_practice, null);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        topic = (Topic) bundle.getSerializable("topic");
        tv_name.setText(topic.getName());
        rb_a.setText(topic.getA());
        rb_b.setText(topic.getB());
        rb_c.setText(topic.getC());
        rb_d.setText(topic.getD());
        rb_e.setText(topic.getE());
        tv_commons.setText(topic.getCommons());
        tv_anser.setText("正确答案：" + topic.getAnser());
        tv_analysis.setText(topic.getAnalysis());
        tv_number.setText(topic.getNumber());

        rightAnser = topic.getAnser();

        tag = bundle.getString("tag");
    }

    private void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rb_a = (RadioButton) view.findViewById(R.id.rb_a);
        rb_b = (RadioButton) view.findViewById(R.id.rb_b);
        rb_c = (RadioButton) view.findViewById(R.id.rb_c);
        rb_d = (RadioButton) view.findViewById(R.id.rb_d);
        rb_e = (RadioButton) view.findViewById(R.id.rb_e);
        rg_option = (RadioGroup) view.findViewById(R.id.rg_option);
        tv_commons = (TextView) view.findViewById(R.id.tv_commons);
        tv_anser = (TextView) view.findViewById(R.id.tv_anser);
        tv_analysis = (TextView) view.findViewById(R.id.tv_analysis);
        ly_result_analysis = (LinearLayout) view.findViewById(R.id.ly_result_analysis);
        tv_number = (TextView) view.findViewById(R.id.tv_number);

        rb_a.setOnClickListener(new MyOnClickListener());
        rb_b.setOnClickListener(new MyOnClickListener());
        rb_c.setOnClickListener(new MyOnClickListener());
        rb_d.setOnClickListener(new MyOnClickListener());
        rb_e.setOnClickListener(new MyOnClickListener());

        ly_result_analysis.setVisibility(View.GONE);

    }

    /**
     * 实例化TopicFragmentView
     * @param bundle
     * @return
     */
    public static TopicFragmentView newInstance(Bundle bundle, Context context) {
        TopicFragmentView topicFragmentView = new TopicFragmentView();
        topicFragmentView.setArguments(bundle);
        TopicFragmentView.context = context;
        return topicFragmentView;
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rb_a:
                    handleOption("A");
                    break;
                case R.id.rb_b:
                    handleOption("B");
                    break;
                case R.id.rb_c:
                    handleOption("C");
                    break;
                case R.id.rb_d:
                    handleOption("D");
                    break;
                case R.id.rb_e:
                    handleOption("E");
                    break;
            }

        }
    }

    /**
     * 初始化正确答案
     */
    private void handleOption(String selectOption) {
        // 禁用所有选项
        for (int i = 0; i < rg_option.getChildCount(); i++) {
            rg_option.getChildAt(i).setEnabled(false);
        }
        // 显示正确答案
        switch (rightAnser) {
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
        RadioButton button = (RadioButton) rg_option.getChildAt(position);
        // 不能使用button.setTextColor(R.color.colorAccent);
        button.setTextColor(this.getResources().getColor(R.color.colorRbRight, null));

        // 显示答案及解析
        ly_result_analysis.setVisibility(View.VISIBLE);

        // 插入记录到数据库
        ContentValues values = new ContentValues();
        values.put("name", topic.getName());
        values.put("number", topic.getNumber());
        values.put("rightAnswer", (String) ((RadioButton) rg_option.getChildAt(position)).getText());
        values.put("time", (new Date()).getTime());
        values.put("selectAnswer", selectOption);
        values.put("target", tag.equals("order") ? 0 : 1);

        //执行插入操作
        new TopicRecordDao(context).saveTopicRecord(values);
    }
}

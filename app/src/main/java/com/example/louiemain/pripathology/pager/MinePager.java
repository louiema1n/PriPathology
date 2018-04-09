package com.example.louiemain.pripathology.pager;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 21:15
 */

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.TopicRecordActivity;
import com.example.louiemain.pripathology.base.BasePager;
import com.example.louiemain.pripathology.dao.TopicRecordDao;
import com.example.louiemain.pripathology.utils.APKUtil;
import com.example.louiemain.pripathology.utils.HttpUtil;
import com.example.louiemain.pripathology.utils.SharedPreferencesUtil;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 练习页面
 * @Author: louiemain
 * @Created: 2018/3/26 21:15
 **/
public class MinePager extends BasePager {

    private static final int COUNT_DOWN = 4;

    private CardView cv_order_record;
    private CardView cv_random_record;
    private TextView tv_count_down;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private CardView cv_download_topic;
    private CardView cv_upload_topic_record;
    private CardView cv_download_topic_record;
    private TextView tv_version_name;

    public MinePager(Context context) {
        super(context);
        new Thread() {
            @Override
            public void run() {
                super.run();
                handler.sendEmptyMessage(COUNT_DOWN);
            }
        }.start();
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.layout_mine, null);

        cv_order_record = (CardView) view.findViewById(R.id.cv_order_record);
        cv_random_record = (CardView) view.findViewById(R.id.cv_random_record);
        cv_download_topic = (CardView) view.findViewById(R.id.cv_download_topic);
        cv_upload_topic_record = (CardView) view.findViewById(R.id.cv_upload_topic_record);
        cv_download_topic_record = (CardView) view.findViewById(R.id.cv_download_topic_record);

        cv_upload_topic_record.setOnClickListener(new MyOnClickListener());
        cv_download_topic_record.setOnClickListener(new MyOnClickListener());
        cv_download_topic.setOnClickListener(new MyOnClickListener());
        cv_order_record.setOnClickListener(new MyOnClickListener());
        cv_random_record.setOnClickListener(new MyOnClickListener());

        tv_count_down = (TextView) view.findViewById(R.id.tv_count_down);
        tv_version_name = (TextView) view.findViewById(R.id.tv_version_name);

        // 初始化版本名称
        tv_version_name.setText("当前版本 " + APKUtil.getVersionName(context));

        return view;
    }

    /**
     * 计算倒计时
     */
    private String calcCountDown() {
        // 考试时间为5.26
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        // 当前时间戳
        long currentTime = calendar.getTimeInMillis();
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DATE, 26);
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // 考试时间戳
        long examTime = calendar.getTimeInMillis();
        // 倒计时
        long rang = examTime - currentTime;
        long nd = 1000 * 24 * 60 * 60;  // 一天
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        long day = rang / nd;
        long hour = rang % nd / nh;
        long min = rang % nd % nh / nm;
        return day + "天" + hour + "小时" + min + "分";
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TopicRecordActivity.class);
            switch (view.getId()) {
                case R.id.cv_download_topic:
                    if (sharedPreferencesUtil.getSyncDataState() == 0) {
                        // 上次失败
                        new HttpUtil(context).downloadTopic();
                    } else {
                        Toast.makeText(context, "已经成功同步过数据库，无需执行此操作。", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.cv_upload_topic_record:
                    new HttpUtil(context).uploadTopicRecord(new TopicRecordDao(context).getAllUploadedTopicRecord());
                    break;
                case R.id.cv_download_topic_record:
                    new HttpUtil(context).downloadTopicRecord();
                    break;
                case R.id.cv_order_record:
                    intent.putExtra("tag", "order");
                    context.startActivity(intent);
                    break;
                case R.id.cv_random_record:
                    intent.putExtra("tag", "random");
                    context.startActivity(intent);
                    break;
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COUNT_DOWN:
                    tv_count_down.setText("距离考试还有" + calcCountDown());
                    removeMessages(COUNT_DOWN);
                    sendEmptyMessageDelayed(COUNT_DOWN, 1000 * 60);
                    break;
            }
        }
    };
}

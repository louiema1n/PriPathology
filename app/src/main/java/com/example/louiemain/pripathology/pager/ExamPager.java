package com.example.louiemain.pripathology.pager;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 21:15
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.base.BasePager;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 考试页面
 * @Author: louiemain
 * @Created: 2018/3/26 21:15
 **/
public class ExamPager extends BasePager {

    private TextView tv_selected_topic_total;
    private CardView cv_topic_total;
    private TextView tv_selected_time_total;
    private CardView cv_time_total;
    private Button btn_start_exam;

    public ExamPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.layout_exam, null);

        tv_selected_topic_total = (TextView) view.findViewById(R.id.tv_selected_topic_total);
        cv_topic_total = (CardView) view.findViewById(R.id.cv_topic_total);
        tv_selected_time_total = (TextView) view.findViewById(R.id.tv_selected_time_total);
        cv_time_total = (CardView) view.findViewById(R.id.cv_time_total);
        btn_start_exam = (Button) view.findViewById(R.id.btn_start_exam);

        cv_topic_total.setOnClickListener(new MyOnclickListener());
        cv_time_total.setOnClickListener(new MyOnclickListener());
        btn_start_exam.setOnClickListener(new MyOnclickListener());

        return view;
    }

    class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cv_topic_total:
                    String[] topics = {"100", "150", "200"};
                    handleSelect(tv_selected_topic_total, topics);
                    break;
                case R.id.cv_time_total:
                    String[] times = {"120", "180", "240"};
                    handleSelect(tv_selected_time_total, times);
                    break;
                case R.id.btn_start_exam:
                    startExam();
                    break;
            }
        }
    }

    /**
     * 开始考试
     */
    private void startExam() {
        Toast.makeText(context, "你选择了" + tv_selected_topic_total.getText() + "个题目和" + tv_selected_time_total.getText() + "分钟时间", Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理点击选择题目数量
     */
    private void handleSelect(final TextView textView, final String[] items) {
        final TextView tv = textView;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.select));
        tv.setText(items[0]);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            // 点击item事件
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                textView.setText(items[i]);
            }
        });
        builder.create().show();
    }
}

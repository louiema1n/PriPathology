package com.example.louiemain.pripathology.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.domain.TopicRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description: 题目记录RecycleView adapter
 * @Author: louiemain
 * @Created: 2018-03-29 16:05
 **/
public class TopicRecordRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TopicRecord> topicRecords;
    private View view;

    // 错误答案position集合
    private Map<Integer, TRViewHolder> rightPositions;

    private OnRVItemClickListener onRVItemClickListener;

    public TopicRecordRVAdapter(List<TopicRecord> topicRecords) {
        this.topicRecords = topicRecords;
        rightPositions = new HashMap<>();
    }

    /**
     * 实例化需要展示的view
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_topic_record, parent, false);
        // 实例化viewHolder
        RecyclerView.ViewHolder viewHolder = new TRViewHolder(view);
        return viewHolder;
    }

    /**
     * 绑定数据到view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TRViewHolder trViewHolder = (TRViewHolder) holder;
        TopicRecord topicRecord = topicRecords.get(position);
        // 绑定数据
        trViewHolder.tv_record_number.setText(topicRecord.getNumber() + "");
        trViewHolder.tv_record_name.setText(topicRecord.getName());
        String rightAnswer = topicRecord.getRightAnswer();
        trViewHolder.tv_record_right_answer.setText(rightAnswer);
        trViewHolder.tv_record_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(topicRecord.getTime()));
        String selectAnswer = topicRecord.getSelectAnswer();
        trViewHolder.tv_record_select_answer.setText(selectAnswer);

        // 设置item点击监听
        trViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRVItemClickListener.onRVItemClick(topicRecords.get(position));
            }
        });

        // 错误答案
        rightAnswer = rightAnswer.substring(0, 1);
        if (!rightAnswer.equals(selectAnswer)) {
            trViewHolder.tv_record_select_answer.setTextColor(view.getResources().getColor(R.color.colorAccent, null));
            trViewHolder.tv_record_number.setEnabled(false);
        } else {
            // 正确答案
            rightPositions.put(position, trViewHolder);
            if (rightPositions.size() > 0) {
                for (Integer pos : rightPositions.keySet()) {
                    if (pos == position) {
                        rightPositions.get(pos).tv_record_select_answer.setTextColor(view.getResources().getColor(R.color.colorRbRight, null));
                        rightPositions.get(pos).tv_record_number.setEnabled(true);
                    }
                }
            }
        }
    }

    /**
     * 项目数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return topicRecords == null ? 0 : topicRecords.size();
    }

    /**
     * 自定义item点击事件接口
     */
    public interface OnRVItemClickListener {
        // 点击item方法
        void onRVItemClick(TopicRecord topicRecord);
    }

    /**
     * 初始化item事件点击对象-由子类来实现接口
     * @param onRVItemClickListener
     */
    public void setOnRVItemClickListener(OnRVItemClickListener onRVItemClickListener) {
        this.onRVItemClickListener = onRVItemClickListener;
    }

    /**
     * 自定义实现RecyclerView.ViewHolderr
     */
    public static class TRViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_record_number;
        public TextView tv_record_name;
        public TextView tv_record_right_answer;
        public TextView tv_record_time;
        public TextView tv_record_select_answer;
        public LinearLayout ll_item;
        public TRViewHolder(View itemView) {
            super(itemView);
            this.ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            this.tv_record_number = (TextView) itemView.findViewById(R.id.tv_record_number);
            this.tv_record_name = (TextView) itemView.findViewById(R.id.tv_record_name);
            this.tv_record_right_answer = (TextView) itemView.findViewById(R.id.tv_record_right_answer);
            this.tv_record_time = (TextView) itemView.findViewById(R.id.tv_record_time);
            this.tv_record_select_answer = (TextView) itemView.findViewById(R.id.tv_record_select_answer);
        }
    }

}

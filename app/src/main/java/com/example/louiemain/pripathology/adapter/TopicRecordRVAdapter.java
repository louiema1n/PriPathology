package com.example.louiemain.pripathology.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.domain.TopicRecord;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description: 题目记录RecycleView adapter
 * @Author: louiemain
 * @Created: 2018-03-29 16:05
 **/
public class TopicRecordRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TopicRecord> topicRecords;

    public TopicRecordRVAdapter(List<TopicRecord> topicRecords) {
        this.topicRecords = topicRecords;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_topic_record, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TRViewHolder trViewHolder = (TRViewHolder) holder;
        TopicRecord topicRecord = topicRecords.get(position);
        // 绑定数据
        trViewHolder.tv_record_number.setText(topicRecord.getNumber() + "");
        trViewHolder.tv_record_name.setText(topicRecord.getName());
        trViewHolder.tv_record_right_answer.setText(topicRecord.getRightAnswer());
        trViewHolder.tv_record_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(topicRecord.getTime()));
        trViewHolder.tv_record_select_answer.setText(topicRecord.getSelectAnswer());
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
     * 自定义实现RecyclerView.ViewHolder
     */
    public static class TRViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_record_number;
        public TextView tv_record_name;
        public TextView tv_record_right_answer;
        public TextView tv_record_time;
        public TextView tv_record_select_answer;
        public TRViewHolder(View itemView) {
            super(itemView);
            this.tv_record_number = (TextView) itemView.findViewById(R.id.tv_record_number);
            this.tv_record_name = (TextView) itemView.findViewById(R.id.tv_record_name);
            this.tv_record_right_answer = (TextView) itemView.findViewById(R.id.tv_record_right_answer);
            this.tv_record_time = (TextView) itemView.findViewById(R.id.tv_record_time);
            this.tv_record_select_answer = (TextView) itemView.findViewById(R.id.tv_record_select_answer);
        }
    }

}

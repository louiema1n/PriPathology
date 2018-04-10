package com.example.louiemain.pripathology.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.domain.Topic;
import com.example.louiemain.pripathology.domain.TopicRecord;

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
public class ExamTopicRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Topic> topics;
    private View view;

    public ExamTopicRVAdapter(List<Topic> topics) {
        this.topics = topics;
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exam_topic, parent, false);
        // 实例化viewHolder
        RecyclerView.ViewHolder viewHolder = new ExamTopicViewHolder(view);
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
        ExamTopicViewHolder ExamTopicViewHolder = (ExamTopicViewHolder) holder;
        Topic topic = topics.get(position);
        // 绑定数据
        ExamTopicViewHolder.ly_result_analysis.setVisibility(View.GONE);
        ExamTopicViewHolder.ll_topic_bottom.setVisibility(View.GONE);

        ExamTopicViewHolder.tv_name.setText((position + 1) + "." + topic.getName());
        ExamTopicViewHolder.rb_a.setText(topic.getA());
        ExamTopicViewHolder.rb_b.setText(topic.getB());
        ExamTopicViewHolder.rb_c.setText(topic.getC());
        ExamTopicViewHolder.rb_d.setText(topic.getD());
        ExamTopicViewHolder.rb_e.setText(topic.getE());
        ExamTopicViewHolder.tv_anser.setText(topic.getAnser());
        ExamTopicViewHolder.tv_analysis.setText(topic.getAnalysis());
        ExamTopicViewHolder.tv_commons.setText(topic.getCommons());
    }

    /**
     * 项目数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return topics == null ? 0 : topics.size();
    }

    /**
     * 自定义实现RecyclerView.ViewHolderr
     */
    public static class ExamTopicViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public RadioButton rb_a;
        public RadioButton rb_b;
        public RadioButton rb_c;
        public RadioButton rb_d;
        public RadioButton rb_e;
        public RadioGroup rg_option;
        public TextView tv_commons;
        public TextView tv_anser;
        public TextView tv_analysis;
        public LinearLayout ly_result_analysis;
        public TextView tv_number;
        public LinearLayout ll_topic_bottom;

        public ExamTopicViewHolder(View itemView) {
            super(itemView);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.rb_a = (RadioButton) itemView.findViewById(R.id.rb_a);
            this.rb_b = (RadioButton) itemView.findViewById(R.id.rb_b);
            this.rb_c = (RadioButton) itemView.findViewById(R.id.rb_c);
            this.rb_d = (RadioButton) itemView.findViewById(R.id.rb_d);
            this.rb_e = (RadioButton) itemView.findViewById(R.id.rb_e);
            this.rg_option = (RadioGroup) itemView.findViewById(R.id.rg_option);
            this.tv_commons = (TextView) itemView.findViewById(R.id.tv_commons);
            this.tv_anser = (TextView) itemView.findViewById(R.id.tv_anser);
            this.tv_analysis = (TextView) itemView.findViewById(R.id.tv_analysis);
            this.ly_result_analysis = (LinearLayout) itemView.findViewById(R.id.ly_result_analysis);
            this.tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            this.ll_topic_bottom = (LinearLayout) itemView.findViewById(R.id.ll_topic_bottom);
        }
    }

}

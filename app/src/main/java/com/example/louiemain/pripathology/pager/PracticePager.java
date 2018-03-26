package com.example.louiemain.pripathology.pager;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 21:15
 */

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.MainActivity;
import com.example.louiemain.pripathology.activity.OrderPracticeActivity;
import com.example.louiemain.pripathology.activity.RandomPracticeActivity;
import com.example.louiemain.pripathology.base.BasePager;
import com.example.louiemain.pripathology.utils.DrawableVerticalButton;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 练习页面
 * @Author: louiemain
 * @Created: 2018/3/26 21:15
 **/
public class PracticePager extends BasePager {

    private DrawableVerticalButton btn_order;
    private DrawableVerticalButton btn_random;

    public PracticePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.layout_practice, null);

        btn_order = (DrawableVerticalButton) view.findViewById(R.id.btn_order);
        btn_random = (DrawableVerticalButton) view.findViewById(R.id.btn_random);

        btn_order.setOnClickListener(new MyOnClickListener());
        btn_random.setOnClickListener(new MyOnClickListener());

        return view;
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.btn_order:
                    intent = new Intent(context, OrderPracticeActivity.class);
                    break;
                case R.id.btn_random:
                    intent = new Intent(context, RandomPracticeActivity.class);
                    break;
            }
            context.startActivity(intent);
        }
    }
}

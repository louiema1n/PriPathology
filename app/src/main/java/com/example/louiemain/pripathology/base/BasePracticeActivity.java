package com.example.louiemain.pripathology.base;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 22:38
 */

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.utils.DataBaseHelper;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 公共的练习题目处理类
 * @Author: louiemain
 * @Created: 2018/3/26 22:38
 **/
public abstract class BasePracticeActivity extends com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity {

    private static final String TABLE_EXAM = "exam";
    private static final String TABLE_RADIO = "radio";

    // 获取数据库操作对象
    private DataBaseHelper helper = new DataBaseHelper(this, "topic", null, 1);
    private SQLiteDatabase database;
    private TextView tv_name;
    private TextView tv_anser;
    private TextView tv_analysis;
    private RadioButton rb_a;
    private RadioButton rb_b;
    private RadioButton rb_c;
    private RadioButton rb_d;
    private RadioButton rb_e;
    private TextView tv_commons;
    private RadioGroup rg_option;

    // 正确答案
    private String anser;
    // 位置
    private int position;
    private LinearLayout ly_result_analysis;
    private TextView tv_number;

    // 定义gestureDetector手势识别器
    private GestureDetector gestureDetector;

    private boolean flag;
    private int FLING_MIN_DISTANCE;
    private int FLING_MIN_VELOCITY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniView();

        getToolbarTitle().setText(getTlTitle());

        // 隐藏答案及解析
        ly_result_analysis.setVisibility(View.GONE);

    }

    private void iniView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_anser = (TextView) findViewById(R.id.tv_anser);
        tv_analysis = (TextView) findViewById(R.id.tv_analysis);
        rb_a = (RadioButton) findViewById(R.id.rb_a);
        rb_a.setOnClickListener(new MyOnClickListener());
        rb_b = (RadioButton) findViewById(R.id.rb_b);
        rb_b.setOnClickListener(new MyOnClickListener());
        rb_c = (RadioButton) findViewById(R.id.rb_c);
        rb_c.setOnClickListener(new MyOnClickListener());
        rb_d = (RadioButton) findViewById(R.id.rb_d);
        rb_d.setOnClickListener(new MyOnClickListener());
        rb_e = (RadioButton) findViewById(R.id.rb_e);
        rb_e.setOnClickListener(new MyOnClickListener());

        tv_commons = (TextView) findViewById(R.id.tv_commons);
        rg_option = (RadioGroup) findViewById(R.id.rg_option);
        ly_result_analysis = (LinearLayout) findViewById(R.id.ly_result_analysis);
        tv_number = (TextView) findViewById(R.id.tv_number);

        gestureDetector = new GestureDetector(this, MyGestureDetector);

        // 设置当前题目数
        tv_number.setText(getNumber());

        // 初始化屏幕宽度
        if (!flag) {
            getWindowWidthAndHeight();
            flag = true;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_base_practice;
    }

    GestureDetector.SimpleOnGestureListener MyGestureDetector = new  GestureDetector.SimpleOnGestureListener() {
        // 滑动结束时触发
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float left = e1.getX() - e2.getX();     // e1.getX() > e2.getX()-左
            float right = e2.getX() - e1.getX();     // e1.getX() > e2.getX()-左
            // 向右滑
            if (right > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                rightFlyingHandle();
            } else if (left > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // 向左滑
                leftFlyingHandle();
            }
            // 自己消费-滑动时不允许触发点击事件
            return super.onFling(e1, e2, velocityX, velocityY);
        }

    };

    /**
     * 左滑处理
     */
    protected abstract void leftFlyingHandle();

    /**
     * 右滑处理
     */
    protected abstract void rightFlyingHandle();

    /**
     * 处理题目名称
     * @param cursorExam
     * @return
     */
    public abstract String handleName(Cursor cursorExam);

    /**
     * 返回子类需要设置的title
     */
    protected abstract String getTlTitle();

    public abstract String getNumber();

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            initRightResult();
        }
    }

    /**
     * 初始化正确答案
     */
    private void initRightResult() {
        // 禁用所有选项
        for (int i = 0; i < rg_option.getChildCount(); i++) {
            rg_option.getChildAt(i).setEnabled(false);
        }
        // 显示正确答案
        switch (anser) {
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
    }

    /**
     * 根据id生成题目
     *
     * @param id
     */
    public void generatePractice(String id) {
        // 得到数据库操作对象-读取模式
        database = helper.getReadableDatabase();
        try {
            Cursor cursorExam = database.query(TABLE_EXAM,
                    new String[]{"id", "name", "catalog", "type", "eid", "commons", "anser", "analysis", "rid"},
                    "id = ?",
                    new String[]{id},
                    null,
                    null,
                    null);

            // cursor置顶
            cursorExam.moveToFirst();
            if (cursorExam != null) {
                // 设置exam
                String name = handleName(cursorExam);

                // 去掉前面的数字
                String commons = cursorExam.getString(cursorExam.getColumnIndex("commons"));    // commons
                commons = commons.replaceFirst("\\d+.", "").replace("<br>", "\n");

                anser = cursorExam.getString(cursorExam.getColumnIndex("anser"));        // anser
                tv_name.setText(name);
                tv_anser.setText("答案 " + anser);
                tv_analysis.setText(cursorExam.getString(cursorExam.getColumnIndex("analysis")));
                tv_commons.setText(commons);
            } else {
                Log.i("msg", "未查询到数据Exam");
            }

            Cursor cursorRadio = database.query(TABLE_RADIO,
                    new String[]{"id", "a", "b", "c", "d", "e"},
                    "id = ?",
                    new String[]{id},
                    null,
                    null,
                    null);
            cursorRadio.moveToFirst();

            if (cursorRadio != null) {
                // 设置Radio
                rb_a.setText(cursorRadio.getString(cursorRadio.getColumnIndex("a")));
                rb_b.setText(cursorRadio.getString(cursorRadio.getColumnIndex("b")));
                rb_c.setText(cursorRadio.getString(cursorRadio.getColumnIndex("c")));
                rb_d.setText(cursorRadio.getString(cursorRadio.getColumnIndex("d")));
                rb_e.setText(cursorRadio.getString(cursorRadio.getColumnIndex("e")));
            } else {
                Log.i("msg", "未查询到数据Radio");
            }
            cursorExam.close();
            cursorRadio.close();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(this, "未查询到数据，请先同步远程数据库。", Toast.LENGTH_SHORT).show();
            // 结束当前视图
            finish();
        } finally {
            database.close();
        }
    }

    /**
     * 获取屏幕宽度
     */
    private void getWindowWidthAndHeight() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        this.FLING_MIN_DISTANCE = (int) (metrics.widthPixels * 0.15);
        this.FLING_MIN_VELOCITY = this.FLING_MIN_DISTANCE / 4;
//        this.heigth = metrics.heightPixels;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}

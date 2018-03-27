package com.example.louiemain.pripathology.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.base.BasePracticeActivity;
import com.example.louiemain.pripathology.utils.DataBaseHelper;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description: 题目viewpager数据源适配器
 * @Author: louiemain
 * @Created: 2018-03-27 11:41
 **/
public class TopicBaseAdapter extends PagerAdapter {

    private Context context;

    private static final String TABLE_EXAM = "exam";
    private static final String TABLE_RADIO = "radio";

    // 获取数据库操作对象
    private DataBaseHelper helper;
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

    public TopicBaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * 需要显示的条数
     * @return
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化显示的条目
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        View view = View.inflate(context, R.layout.layout_base_practice, null);

        initView(view);

        helper = new DataBaseHelper(context, "topic", null, 1);
        generatePractice(String.valueOf(position + 1));

        // 隐藏答案及解析
        ly_result_analysis.setVisibility(View.GONE);

        container.addView(view);
        return view;
    }

    private void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_anser = (TextView) view.findViewById(R.id.tv_anser);
        tv_analysis = (TextView) view.findViewById(R.id.tv_analysis);
        rb_a = (RadioButton) view.findViewById(R.id.rb_a);
        rb_a.setOnClickListener(new MyOnClickListener());
        rb_b = (RadioButton) view.findViewById(R.id.rb_b);
        rb_b.setOnClickListener(new MyOnClickListener());
        rb_c = (RadioButton) view.findViewById(R.id.rb_c);
        rb_c.setOnClickListener(new MyOnClickListener());
        rb_d = (RadioButton) view.findViewById(R.id.rb_d);
        rb_d.setOnClickListener(new MyOnClickListener());
        rb_e = (RadioButton) view.findViewById(R.id.rb_e);
        rb_e.setOnClickListener(new MyOnClickListener());

        tv_commons = (TextView) view.findViewById(R.id.tv_commons);
        rg_option = (RadioGroup) view.findViewById(R.id.rg_option);
        ly_result_analysis = (LinearLayout) view.findViewById(R.id.ly_result_analysis);
        tv_number = (TextView) view.findViewById(R.id.tv_number);

        // 设置当前题目数
        tv_number.setText(getNumber());

    }

    /**
     * 处理题目名称
     * @param cursorExam
     * @return
     */
    public String handleName(Cursor cursorExam){return null;}

    /**
     * 返回子类需要设置的title
     */
    protected String getTlTitle(){return null;}

    public String getNumber(){return null;}

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rb_a:
                    Toast.makeText(context, "你选择的是A", Toast.LENGTH_SHORT).show();
                    rb_a.setEnabled(false);
                    break;
            }
//            initRightResult();
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
        button.setTextColor(context.getResources().getColor(R.color.colorRbRight, null));

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
            Toast.makeText(context, "未查询到数据，请先同步远程数据库。", Toast.LENGTH_SHORT).show();
            // 结束当前视图
//            finish();
        } finally {
            database.close();
        }
    }


    /**
     * 销毁条目
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}

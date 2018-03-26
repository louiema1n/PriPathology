package com.example.louiemain.pripathology.activity.base;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 20:49
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.louiemain.pripathology.R;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 通用AppCompatActivity
 * @Author: louiemain
 * @Created: 2018/3/26 20:49
 **/
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    private Toolbar tb_base_toolbar;    // toolbar
    private TextView tv_toolbar_title;  // toolbar_title
    /**
     * 创建
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tb_base_toolbar = (Toolbar) findViewById(R.id.tb_base_toolbar);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);

        // 设置toolbar
        if (tb_base_toolbar != null) {
            // 设置到界面
            setSupportActionBar(tb_base_toolbar);
        }

        // 设置Title
        if (tv_toolbar_title != null) {
            // 默认为getTitle()-android:label的值
            tv_toolbar_title.setText(getTitle());
            // 不显示默认的Title
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 从子类获取布局id - 需子类重写
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 返回当前的tv_toolbar_title
     * @return
     */
    public TextView getToolbarTitle() {
        return this.tv_toolbar_title;
    }
    public Toolbar getToolbar() {
        return this.tb_base_toolbar;
    }

    /**
     * 启动
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (tb_base_toolbar != null && isShowBacking()) {
            // 显示back按钮
//            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            tb_base_toolbar.setNavigationIcon(R.drawable.ic_back_white_24dp);
        }
    }

    /**
     * toolbar事件监听
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 后退按钮
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 是否显示后退按钮- 可由子类复写
     * @return
     */
    public boolean isShowBacking() {
        return true;
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

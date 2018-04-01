package com.example.louiemain.pripathology.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.adapter.TopicFragmentStateAdapter;
import com.example.louiemain.pripathology.dao.TopicDao;
import com.example.louiemain.pripathology.dao.TopicRecordDao;
import com.example.louiemain.pripathology.view.TopicFragmentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TopicActivity extends com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity {

    private ViewPager vp_topic_container;
    private List<Fragment> fragments;
    private TopicFragmentStateAdapter adapter;

    private Bundle bundle;
    private TopicFragmentView tfv;
    private int id;

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_topic);
        initView();
        tag = getIntent().getStringExtra("tag");
        if (tag.equals("order")) {
            // 查询当前已记录的最大已做题目id
            id = new TopicRecordDao(this).getMaxSelectedId(0) + 1;
            if (id == 0) {
                id = 1;
            }
            getToolbarTitle().setText(getString(R.string.order) + getString(R.string.title_practice));
        } else if (tag.equals("random")){
            id = new Random().nextInt(2140) + 1;
            getToolbarTitle().setText(getString(R.string.random) + getString(R.string.title_practice));
        }
        initData(id);

        vp_topic_container.setAdapter(adapter);
        vp_topic_container.addOnPageChangeListener(new TopicOnPageChangeListener());
    }


    private void initData(int id) {
        fragments = new ArrayList<>();
        initFragments(String.valueOf(id));
        // 设置adapter
        adapter = new TopicFragmentStateAdapter(getSupportFragmentManager(), fragments);
    }

    private void initView() {
        vp_topic_container = (ViewPager) findViewById(R.id.vp_topic_container);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic;
    }

    /**
     * viewpager的页面改变监听
     */
    private class TopicOnPageChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // 开始按下state=1;中间state=2;结束state=0
            if (state == 1) {
                if (tag.equals("order")) {
                    id++;
                } else if (tag.equals("random")) {
                    id = new Random().nextInt(2140) + 1;
                }
                initFragments(String.valueOf(id));
                // 通知adapter更新数据
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 根据id初始化fragments
     * @param id
     */
    private void initFragments(String id) {
        bundle = new Bundle();
        bundle.putSerializable("topic", new TopicDao(TopicActivity.this).generatePractice(id));
        bundle.putString("tag", tag);
        tfv = TopicFragmentView.newInstance(bundle, this);
        fragments.add(tfv);
    }

    /**
     * 加载菜单资源
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 获取MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        // 加载menu
        menuInflater.inflate(R.menu.menu_topic_record, menu);
        // 自己处理
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
            case R.id.menu_topic_record:
                Intent intent = new Intent(this, TopicRecordActivity.class);
                intent.putExtra("tag", tag);
                this.startActivity(intent);
                return true;
        }
    }
}

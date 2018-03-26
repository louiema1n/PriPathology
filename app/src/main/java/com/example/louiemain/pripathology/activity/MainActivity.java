package com.example.louiemain.pripathology.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.base.BaseAppCompatActivity;
import com.example.louiemain.pripathology.base.BasePager;
import com.example.louiemain.pripathology.pager.ExamPager;
import com.example.louiemain.pripathology.pager.MinePager;
import com.example.louiemain.pripathology.pager.PracticePager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity {

    private BottomNavigationView nv_bottom;
    private ViewPager vp_container;
    private List<Fragment> basePagerFragments;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nv_title_practice:
                    vp_container.setCurrentItem(0, false);
                    return true;
                case R.id.nv_title_exam:
                    vp_container.setCurrentItem(1, false);
                    return true;
                case R.id.nv_title_mine:
                    vp_container.setCurrentItem(2, false);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
        iniData();
        iniView();
    }

    private void iniData() {
        basePagerFragments = new ArrayList<>();
        basePagerFragments.add(new ReplaceFragment(new PracticePager(this)));
        basePagerFragments.add(new ReplaceFragment(new ExamPager(this)));
        basePagerFragments.add(new ReplaceFragment(new MinePager(this)));
    }

    /**
     * 获取fragment
     */
    static class ReplaceFragment extends Fragment {
        private BasePager basePager;

        public ReplaceFragment(BasePager basePager) {
            this.basePager = basePager;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (basePager != null && !basePager.isInitData) {
                // 未初始化
                basePager.initData();
                basePager.isInitData = true;
                return basePager.rootView;
            }
            return null;
        }
    }

    private void iniView() {
        nv_bottom = (BottomNavigationView) findViewById(R.id.nv_bottom);
        vp_container = (ViewPager) findViewById(R.id.vp_container);

        // 设置adapter
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return basePagerFragments.get(position);
            }

            @Override
            public int getCount() {
                return basePagerFragments.size();
            }
        };

        vp_container.setAdapter(adapter);
        vp_container.setOffscreenPageLimit(2);  // 预加载2页

        // 设置BottomNavigationView监听
        nv_bottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // 设置viewpager的滑动监听
        vp_container.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 不显示back按钮
     * @return
     */
    @Override
    public boolean isShowBacking() {
        return false;
    }

    /**
     * 滑动pager监听
     */
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    nv_bottom.setSelectedItemId(R.id.nv_title_practice);
                    break;
                case 1:
                    nv_bottom.setSelectedItemId(R.id.nv_title_exam);
                    break;
                case 2:
                    nv_bottom.setSelectedItemId(R.id.nv_title_mine);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}

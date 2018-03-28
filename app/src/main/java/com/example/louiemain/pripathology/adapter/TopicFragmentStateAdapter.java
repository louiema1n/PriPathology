package com.example.louiemain.pripathology.adapter;/**
 * @description
 * @author&date Created by louiemain on 2018/3/28 17:06
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: TopicFragmentStateAdapter
 * @Author: louiemain
 * @Created: 2018/3/28 17:06
 **/
public class TopicFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public TopicFragmentStateAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

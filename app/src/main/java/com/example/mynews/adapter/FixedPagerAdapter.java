package com.example.mynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.mynews.entity.CategoriesBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */

public class FixedPagerAdapter extends FragmentStatePagerAdapter {

    private List<CategoriesBean> categoriesBeen;

    public void setCategoriesBeen(List<CategoriesBean> categoriesBeen) {
        this.categoriesBeen = categoriesBeen;
    }

    private List<Fragment> fragments;

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public FixedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //固定写法
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try{
            fragment = (Fragment) super.instantiateItem(container,position);
        }catch (Exception e){

        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoriesBeen.get(position%categoriesBeen.size()).getTitle();
    }
}

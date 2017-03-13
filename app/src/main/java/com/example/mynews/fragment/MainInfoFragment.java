package com.example.mynews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.R;
import com.example.mynews.adapter.FixedPagerAdapter;
import com.example.mynews.common.DefineView;
import com.example.mynews.entity.CategoriesBean;
import com.example.mynews.fragment.base.BaseFragment;
import com.example.mynews.ui.MainActivity;
import com.example.mynews.utils.CategoryDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class MainInfoFragment extends BaseFragment implements DefineView, ViewPager.OnPageChangeListener {
    private View mView;
    private TabLayout tab_layout;
    private ViewPager info_viewpager;
    private FixedPagerAdapter fixedPagerAdapter;
    private List<Fragment> fragments;
    private static List<CategoriesBean> categoriesBeans = CategoryDataUtils.getCategoryBeans();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.main_info_fragment_layout, container, false);
            initView();
            initValidata();
            initListener();
            bindData();
        }
        return mView;
    }

    @Override
    public void initView() {
        tab_layout = (TabLayout) mView.findViewById(R.id.tab_layout);
        info_viewpager = (ViewPager) mView.findViewById(R.id.info_viewpager);
    }

    @Override
    public void initValidata() {
        fixedPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());
        fixedPagerAdapter.setCategoriesBeen(categoriesBeans);
        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < categoriesBeans.size(); i++) {
            BaseFragment fragment = null;
            if (i == 0){
                fragment=HomeFragment.newInstance(categoriesBeans.get(i));
            }else{
                fragment = PageFragment.newInstance(categoriesBeans.get(i));
            }
            fragments.add(fragment);
        }
        fixedPagerAdapter.setFragments(fragments);

        info_viewpager.setAdapter(fixedPagerAdapter);
        tab_layout.setupWithViewPager(info_viewpager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void initListener() {
        info_viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void bindData() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            ((MainActivity)getActivity()).getDrag_layout().setIsDrag(true);
        }else {
            ((MainActivity)getActivity()).getDrag_layout().setIsDrag(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

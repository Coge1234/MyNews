package com.example.mynews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.entity.CategoriesBean;
import com.example.mynews.fragment.base.BaseFragment;
import com.example.mynews.common.DefineView;

/**
 * Created by Administrator on 2017/3/9.
 */

public class HomeFragment extends BaseFragment implements DefineView {
    private View mView;
    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;
    private TextView tv_page;

    public static PageFragment newInstance(CategoriesBean extra){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY,extra);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        if (bundle != null){
            categoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.home_fragment_layout,container,false);
            initView();
            initValidata();
            initListener();
        }
        return mView;
    }

    @Override
    public void initView() {
        tv_page = (TextView) mView.findViewById(R.id.tv_page);
        tv_page.setText(categoriesBean.getTitle());
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }
}

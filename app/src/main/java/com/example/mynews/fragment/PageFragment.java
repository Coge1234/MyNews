package com.example.mynews.fragment;


import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.common.DefineView;
import com.example.mynews.entity.CategoriesBean;
import com.example.mynews.fragment.base.BaseFragment;
import com.example.mynews.utils.OkhttpManager;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends BaseFragment implements DefineView{

    private View mView;
    private static final String KEY="EXTRA";
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
        Bundle bundle = getArguments();
        if (bundle != null){
            categoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == mView){
            mView = inflater.inflate(R.layout.page_fragment_layout, container, false);
            initView();
            initValidata();
            initListener();
            bindData();
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

package com.example.mynews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mynews.R;
import com.example.mynews.adapter.HomeRecyclerAdapter;
import com.example.mynews.application.NEWSApplication;
import com.example.mynews.common.DefineView;
import com.example.mynews.entity.CategoriesBean;
import com.example.mynews.entity.ITEM;
import com.example.mynews.fragment.base.BaseFragment;
import com.example.mynews.ui.DetailsActivity;
import com.example.mynews.utils.OkhttpManager;
import com.example.mynews.utils.Utility;
import com.example.mynews.widget.RecyclerViewItemDecoration;

import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends BaseFragment implements DefineView {

    private View mView;
    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;
    private RecyclerView home_recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private FrameLayout home_framelayout;
    private LinearLayout loading, empty, error;
    private List<ITEM> items;//新闻列表数据
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int lastItem;
    private boolean isMore=true; //解决上拉重复加载的bug

    private HomeRecyclerAdapter adapter;

    public static PageFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.page_fragment_layout, container, false);
            initView();
            initValidata();
            initListener();

        }
        return mView;
    }

    @Override
    public void initView() {
        home_framelayout = (FrameLayout) mView.findViewById(R.id.prompt_framelayout);
        home_recyclerview = (RecyclerView) mView.findViewById(R.id.home_recyclerview);
        empty = (LinearLayout) mView.findViewById(R.id.empty);
        error = (LinearLayout) mView.findViewById(R.id.error);
        loading = (LinearLayout) mView.findViewById(R.id.loading);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);

        //设置swipeRefreshLayout的进度条的背景颜色
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_white);
        //进度条的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        //设置进度条的偏移量
        mSwipeRefreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));

        home_recyclerview.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(NEWSApplication.getInstance(), OrientationHelper.VERTICAL, false);
        home_recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new HomeRecyclerAdapter();
        //设置分隔线
        home_recyclerview.addItemDecoration(new RecyclerViewItemDecoration(getContext()));
        //设置动画
    }

    @Override
    public void initValidata() {

        //数据获取
        OkhttpManager.getAsync(categoriesBean.getHref(), new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Toast.makeText(NEWSApplication.getInstance(), "获取新闻数据失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) {
                items = Utility.handleDATAResponse(result).items;
                if (items != null) {
                    bindData();
                } else {
                    Toast.makeText(NEWSApplication.getInstance(), "获取新闻数据失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        initValidata();
                        Toast.makeText(NEWSApplication.getInstance(),"下拉刷新成功",Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });

        home_recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //正在滚动时回调，回调2-3次，手指没抛则回调2次。newState = 2的这次不回调
                //回调顺序如下
                //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
                //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
                //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
                //当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；
                //由于用户的操作，屏幕产生惯性滑动时为2

                //当滚到最后一行且停止滚动时，执行加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem+1 == linearLayoutManager.getItemCount()){
                    if(isMore && items != null){
                        isMore = false;
                        //进行加载数据...
                        //构造请求地址
                        StringBuffer str = new StringBuffer(categoriesBean.getHref());
                        String strInsert = items.get(items.size()-1).id;
                        str.insert(str.lastIndexOf("&"),strInsert);
                        String loadMoreUrl = str.toString();
                        OkhttpManager.getAsync(loadMoreUrl, new OkhttpManager.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, Exception e) {
                                Toast.makeText(NEWSApplication.getInstance(),"加载数据失败",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void requestSuccess(String result) {
                                items.addAll(Utility.handleDATAResponse(result).items);
                                adapter.notifyDataSetChanged();
                                isMore=true;
                            }
                        });
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        //添加点击事件
        adapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ITEM item) {
                Intent mIntent = new Intent(getActivity(), DetailsActivity.class);
                mIntent.putExtra("news_item",item);
                getActivity().startActivity(mIntent);
            }
        });
    }

    @Override
    public void bindData() {
        if (items != null) {
            adapter.setHomeNewsBeans(items);
            home_recyclerview.setAdapter(adapter);
        }
    }
}

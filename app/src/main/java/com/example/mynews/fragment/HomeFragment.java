package com.example.mynews.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.helper.BaseAdapterHelper;
import com.example.adapter.helper.QuickAdapter;
import com.example.cwidgetutils.AutoGallery;
import com.example.cwidgetutils.FlowIndicator;
import com.example.cwidgetutils.PullToRefreshListView;
import com.example.mynews.R;
import com.example.mynews.application.NEWSApplication;
import com.example.mynews.common.AdvertisementURL;
import com.example.mynews.common.DefineView;
import com.example.mynews.entity.AdHeadBean;
import com.example.mynews.entity.CategoriesBean;
import com.example.mynews.entity.DATA;
import com.example.mynews.entity.ITEM;
import com.example.mynews.fragment.base.BaseFragment;
import com.example.mynews.ui.DetailsActivity;
import com.example.mynews.utils.OkhttpManager;
import com.example.mynews.utils.Utility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/3/9.
 */

public class HomeFragment extends BaseFragment implements DefineView {
    private View mView;
    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;
    private PullToRefreshListView home_listview;
    private DATA mData;
    private List<ITEM> adHeadBeans;
    private QuickAdapter<ITEM> mQuickAdapter;
    private FrameLayout home_framelayout;
    private View headView;
    private LayoutInflater mInflater;
    private AutoGallery headline_image_gallery;
    private FlowIndicator headline_circle_indicator;
    private LinearLayout loading, empty, error;
    private int gallerySelectedPosition = 0; //Gallery索引
    private int circleSelectedPosition = 0; //默认指示器的圆圈的位置为第一
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;

    public static HomeFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        HomeFragment fragment = new HomeFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.home_fragment_layout, container, false);
            mInflater = LayoutInflater.from(NEWSApplication.getInstance());
            headView = mInflater.inflate(R.layout.gallery_indicator_layout, null);
            initView();
            initValidata();
            initListener();
        }

        return mView;
    }

    @Override
    public void initView() {
        home_listview = (PullToRefreshListView) mView.findViewById(R.id.home_listview);
        home_listview.addHeaderView(headView);
        home_framelayout = (FrameLayout) mView.findViewById(R.id.home_framelayout);
        loading = (LinearLayout) mView.findViewById(R.id.loading);
        empty = (LinearLayout) mView.findViewById(R.id.empty);
        error = (LinearLayout) mView.findViewById(R.id.error);
        //获取AutoGallery和FlowIndicator控件
        headline_image_gallery = (AutoGallery) headView.findViewById(R.id.headline_image_gallery);
        headline_circle_indicator = (FlowIndicator) headView.findViewById(R.id.headline_circle_indicator);

        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.defaultbg)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        home_listview.setVisibility(View.GONE);
        home_framelayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

        adHeadBeans = new ArrayList<>();
    }

    @Override
    public void initValidata() {


        //获取首页广告
        OkhttpManager.getAsync(AdvertisementURL.AdUrl, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Toast.makeText(NEWSApplication.getInstance(), "获取广告数据失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) {
                DATA dataTemp = Utility.handleDATAResponse(result);
                if (dataTemp != null) {
                    for (int i = 0; i < 5; i++) {
                        adHeadBeans.add(dataTemp.items.get(i));
                    }
                } else {
                    Toast.makeText(NEWSApplication.getInstance(), "获取广告数据失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //获取首页新闻
        OkhttpManager.getAsync(categoriesBean.getHref(), new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Toast.makeText(NEWSApplication.getInstance(), "获取新闻数据失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) {
                mData = Utility.handleDATAResponse(result);
                if (mData != null) {
                    home_listview.setVisibility(View.VISIBLE);
                    home_framelayout.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    bindData();
                } else {
                    Toast.makeText(NEWSApplication.getInstance(), "获取新闻数据失败！", Toast.LENGTH_SHORT).show();
                    home_listview.setVisibility(View.GONE);
                    home_framelayout.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initListener() {
        home_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), DetailsActivity.class);
                mIntent.putExtra("news_item", mData.items.get((int) id));
                getActivity().startActivity(mIntent);
//                Toast.makeText(NEWSApplication.getInstance(), ""+position, Toast.LENGTH_SHORT).show();
            }
        });
        home_listview.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initValidata();
            }
        });
        headline_image_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), DetailsActivity.class);
                mIntent.putExtra("news_item", adHeadBeans.get(position%5));
                getActivity().startActivity(mIntent);
            }
        });
    }

    @Override
    public void bindData() {

        if (adHeadBeans != null) {
//            int topSize = adHeadBeans.size();
            int topSize = 5;
            //设置指示器
            headline_circle_indicator.setCount(topSize);
            headline_circle_indicator.setSeletion(circleSelectedPosition);
            //设置画廊Gallery
            headline_image_gallery.setLength(topSize);
            //乘以50为了让位置偏离初始位置，便于左右滑动
            gallerySelectedPosition = topSize * 50 + circleSelectedPosition;
            headline_image_gallery.setSelection(gallerySelectedPosition);
            headline_image_gallery.setDelayMillis(3000);
            headline_image_gallery.start();
            headline_image_gallery.setAdapter(new GalleryAdapter());
            headline_image_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    circleSelectedPosition = position;
                    gallerySelectedPosition = circleSelectedPosition % adHeadBeans.size();
                    headline_circle_indicator.setSeletion(gallerySelectedPosition);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        mQuickAdapter = new QuickAdapter<ITEM>(NEWSApplication.getInstance(), R.layout.item_home_news_layout, mData.items) {
            @Override
            protected void convert(BaseAdapterHelper helper, ITEM item) {
                String mask_color = item.column.bg_color;
                helper.setText(R.id.item_news_tv_name, item.user.name)
                        .setText(R.id.item_news_tv_time, item.updated_at)
                        .setText(R.id.item_news_tv_type, item.column.name)
                        .setText(R.id.item_news_tv_title, item.title);

                mImageLoader.displayImage(item.cover, (ImageView) helper.getView(R.id.item_news_tv_img), mOptions);
                mImageLoader.displayImage(item.user.avatar_url, (ImageView) helper.getView(R.id.item_news_img_icon), mOptions);
                TextView tv_type = helper.getView(R.id.item_news_tv_type);
                tv_type.setTextColor(Color.parseColor(mask_color));
                helper.getView(R.id.item_news_tv_arrow).setBackgroundColor(Color.parseColor(mask_color));
            }
        };
        home_listview.setAdapter(mQuickAdapter);
    }

    /**
     * AutoGallery的自定义Adapter
     */
    class GalleryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return adHeadBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = mInflater.inflate(R.layout.item_gallery_layout, null);
                holder.item_head_gallery_img = (ImageView) convertView.findViewById(R.id.item_head_gallery_img);
                holder.item_head_gallery_textview = (TextView) convertView.findViewById(R.id.item_head_gallery_textview);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            //显示数据
//            Picasso.with(NEWSApplication.getInstance()).load(adHeadBeans.get(position % adHeadBeans.size()).getImgurl()).into(holder.item_head_gallery_img);
            if (adHeadBeans != null && adHeadBeans.size()>0) {
                mImageLoader.displayImage(adHeadBeans.get(position % 5).cover, holder.item_head_gallery_img, mOptions);
                holder.item_head_gallery_textview.setText(adHeadBeans.get(position % 5).title);
            }
            return convertView;
        }
    }

    private static class Holder {
        ImageView item_head_gallery_img;
        TextView item_head_gallery_textview;
    }
}

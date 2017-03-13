package com.example.mynews.adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.application.NEWSApplication;
import com.example.mynews.entity.ITEM;
import com.example.mynews.widget.RoundAngleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOT = 1;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;
    private Resources mResources;
    //新闻列表的数据
    private List<ITEM> items;

    public void setHomeNewsBeans(List<ITEM> items) {
        this.items = items;
    }

    public HomeRecyclerAdapter() {
        this.mInflater = LayoutInflater.from(NEWSApplication.getInstance());
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.defaultbg_h)
                .build();
        mImageLoader = ImageLoader.getInstance();
        mResources = NEWSApplication.getInstance().getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = mInflater.inflate(R.layout.item_home_news_layout, parent, false);
            itemView.setOnClickListener(this);
            ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOT) {
            View footItemView = mInflater.inflate(R.layout.recycler_load_more_layout, parent, false);
            FootItemViewHolder footItemViewHolder = new FootItemViewHolder(footItemView);
            return footItemViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            holder.itemView.setTag(items.get(position));
            String mask_color = items.get(position).column.bg_color;
            mImageLoader.displayImage(items.get(position).cover, ((ItemViewHolder) holder).item_news_tv_img, mOptions);
            mImageLoader.displayImage(items.get(position).user.avatar_url, ((ItemViewHolder) holder).item_news_img_icon, mOptions);

            ((ItemViewHolder) holder).item_news_tv_name.setText(items.get(position).user.name);
            ((ItemViewHolder) holder).item_news_tv_time.setText(items.get(position).updated_at);
            ((ItemViewHolder) holder).item_news_tv_type.setText(items.get(position).column.name);
            ((ItemViewHolder) holder).item_news_tv_title.setText(items.get(position).title);
            ((ItemViewHolder) holder).item_news_tv_arrow.setBackgroundColor(Color.parseColor(mask_color));
            ((ItemViewHolder) holder).item_news_tv_type.setTextColor(Color.parseColor(mask_color));
        } else if (holder instanceof FootItemViewHolder) {
            //上拉加载更多布局信息...
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(v, (ITEM) v.getTag());
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private RoundAngleImageView item_news_img_icon;
        private TextView item_news_tv_name;
        private TextView item_news_tv_time;
        private TextView item_news_tv_arrow;
        private TextView item_news_tv_type;
        private TextView item_news_tv_title;
        private ImageView item_news_tv_img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_news_img_icon = (RoundAngleImageView) itemView.findViewById(R.id.item_news_img_icon);
            item_news_tv_name = (TextView) itemView.findViewById(R.id.item_news_tv_name);
            item_news_tv_time = (TextView) itemView.findViewById(R.id.item_news_tv_time);
            item_news_tv_arrow = (TextView) itemView.findViewById(R.id.item_news_tv_arrow);
            item_news_tv_type = (TextView) itemView.findViewById(R.id.item_news_tv_type);
            item_news_tv_title = (TextView) itemView.findViewById(R.id.item_news_tv_title);
            item_news_tv_img = (ImageView) itemView.findViewById(R.id.item_news_tv_img);
        }
    }

    /**
     * 上拉加载更多进度布局
     */
    static class FootItemViewHolder extends RecyclerView.ViewHolder {

        public FootItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    //添加ItemClickListener接口
    public interface OnItemClickListener {
        void onItemClick(View view, ITEM item);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}

package com.example.mynews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.application.NEWSApplication;
import com.example.mynews.entity.LeftItemMenu;
import com.example.mynews.utils.MenuDataUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class LeftItemAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    private List<LeftItemMenu> itemMenus;
    public LeftItemAdapter(){
        mInflater=LayoutInflater.from(NEWSApplication.getInstance());
        itemMenus= MenuDataUtils.getItemMenus();
    }
    @Override
    public int getCount() {
        return itemMenus!=null?itemMenus.size():0;
    }

    @Override
    public Object getItem(int position) {
        return itemMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null){
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_left_menu_layout,null);
            holder.item_left_view_img = (ImageView) convertView.findViewById(R.id.item_left_view_img);
            holder.item_left_view_title = (TextView) convertView.findViewById(R.id.item_left_view_title);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.item_left_view_img.setImageResource(itemMenus.get(position).getLeftIcon());
        holder.item_left_view_title.setText(itemMenus.get(position).getTitle());
        return convertView;
    }
    private static class Holder{
        ImageView item_left_view_img;
        TextView item_left_view_title;
    }
}

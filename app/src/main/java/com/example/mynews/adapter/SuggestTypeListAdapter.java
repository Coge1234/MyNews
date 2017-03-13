package com.example.mynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mynews.R;

/**
 * Created by Administrator on 2017/3/14.
 */

public class SuggestTypeListAdapter extends BaseAdapter {
    private String[] types;
    private LayoutInflater inflater;

    public SuggestTypeListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public SuggestTypeListAdapter(Context context, String[] types) {
        this(context);
        this.types = types;
    }

    @Override
    public int getCount() {
        return types == null ? 0 : types.length;
    }

    @Override
    public Object getItem(int position) {
        return types[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.suggest_type_list_item_layout,null);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_suggest_type_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_type.setText(types[position]);
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_type;
    }
}

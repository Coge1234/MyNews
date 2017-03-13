package com.example.mynews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by newuser on 2016/11/10.
 */

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration{

    public  static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private final Drawable mDrawable;

    public RecyclerViewItemDecoration(Context context){

        TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        doDrawVertical(c, parent);
        doHorizontal(c, parent);
    }

    private void doHorizontal(Canvas c, RecyclerView parent) {

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            //拿到里面的条目
            View child = parent.getChildAt(i);
            int top = child.getTop();
            int bottom = child.getBottom();
            int left = child.getRight();
            int right = left + mDrawable.getIntrinsicWidth();

            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }

    }

    private void doDrawVertical(Canvas c, RecyclerView parent) {

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            View child = parent.getChildAt(i);

            int left = child.getLeft();
            int right = child.getRight()+mDrawable.getIntrinsicWidth();
            int top = child.getBottom();
            int bottom = top+ mDrawable.getIntrinsicHeight();
            //設置參數並且畫出來
            mDrawable.setBounds(left,top,right,bottom);
            mDrawable.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,mDrawable.getIntrinsicWidth(),mDrawable.getIntrinsicHeight());
    }
}

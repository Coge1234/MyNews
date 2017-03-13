package com.example.mynews.ui.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.mynews.R;

import java.lang.reflect.Field;

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            TypedValue typedValue = new TypedValue();
//            Resources.Theme theme = getTheme();
//            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
//            int color = typedValue.data;
//
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_activity_welcome);
//            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, bm, color);
//
//            setTaskDescription(td);
//            bm.recycle();
//
//        }
    }

    protected void openActivity(Class<?> pClass) {
        Intent mIntent = new Intent(this, pClass);
        this.startActivity(mIntent);
    }


    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup linear_bar = (ViewGroup) findViewById(R.id.bar_layout);
            final int statusHeight = getStatusBarHeight();
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getHeight();
                    android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) linear_bar.getLayoutParams();
                    params.height = statusHeight + titleHeight;
                    linear_bar.setLayoutParams(params);
                }
            });
        }
    }
    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight(){
        try
        {
            Class<?> c=Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x=Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}

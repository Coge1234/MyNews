package com.example.mynews.application;

import android.app.Application;

/**
 * Created by Administrator on 2017/3/8.
 * 当前类注释:全局Application类,作为全局数据的配置以及相关参数数据初始化工作
 */

public class NEWSApplication extends Application {
    private static NEWSApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
    }
    public static NEWSApplication getInstance(){
        return instance;
    }
}

package com.example.mynews.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/3/8.
 * 当前类注释:全局Application类,作为全局数据的配置以及相关参数数据初始化工作
 */

public class NEWSApplication extends Application {
    private static NEWSApplication instance = null;
    private HashMap<String, Object> tempMap = new HashMap<String, Object>();

    public HashMap<String, Object> getTempMap() {
        return tempMap;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initImage();
    }

    public static NEWSApplication getInstance() {
        return instance;
    }

    private void initImage() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}

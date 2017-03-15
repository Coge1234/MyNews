package com.example.mynews.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mynews.R;
import com.example.mynews.adapter.LeftItemAdapter;
import com.example.mynews.application.NEWSApplication;
import com.example.mynews.common.DefineView;
import com.example.mynews.common.DeliverConsts;
import com.example.mynews.common.RequestURL;
import com.example.mynews.ui.base.BaseActivity;
import com.example.mynews.update.UpdateInfoModel;
import com.example.mynews.update.UpdateReceiver;
import com.example.mynews.utils.OkhttpManager;
import com.example.mynews.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Request;

public class MainActivity extends BaseActivity implements DefineView {

    private DragLayout drag_layout;
    private ImageView top_bar_icon;
    private ListView lv_left_menu;
    private UpdateReceiver mUpdateReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter mIntentFilter;
    private HashMap<String, Object> tmpMap = NEWSApplication.getInstance()
            .getTempMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadcast();// 注册广播
        setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();
    }

    public DragLayout getDrag_layout() {
        return drag_layout;
    }

    public void initView() {
        drag_layout = (DragLayout) findViewById(R.id.drag_layout);
        top_bar_icon = (ImageView) findViewById(R.id.top_bar_icon);
        lv_left_menu = (ListView) findViewById(R.id.lv_left_main);
    }

    @Override
    public void initValidata() {
        lv_left_menu.setAdapter(new LeftItemAdapter());

//        OkhttpManager.getAsync(RequestURL.UPDATE_URL, new OkhttpManager.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void requestSuccess(String result) {
//                try {
//                    JSONObject object = new JSONObject(result);
//                    UpdateInfoModel model = new UpdateInfoModel();
//                    model.setAppname(object.getString("appname"));
//                    model.setLastForce(object.getString("lastForce"));
//                    model.setServerFlag(object.getString("serverFlag"));
//                    model.setServerVersion(object.getString("serverVersion"));
//                    model.setUpdateurl(object.getString("updateurl"));
//                    model.setUpgradeinfo(object.getString("upgradeinfo"));
//                    tmpMap.put(DeliverConsts.KEY_APP_UPDATE, model);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                localBroadcastManager.sendBroadcast(new Intent(UpdateReceiver.UPDATE_ACTION));
//            }
//        });
    }

    @Override
    public void initListener() {
        drag_layout.setDragListener(new CustomDragListener());
        top_bar_icon.setOnClickListener(new CustomOnClickListener());

        lv_left_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:  //关注
                        break;
                    case 2: //收藏
                        break;
                    case 3: //意见反馈
                        openActivity(SuggestActivity.class);
                        break;
                    case 4: //设置
                        break;
                    case 5: //关于我们
                        openActivity(AboutActivity.class);
                        break;
                }
            }
        });

    }

    @Override
    public void bindData() {

    }

    class CustomDragListener implements DragLayout.DragListener {

        /**
         * 界面打开
         */
        @Override
        public void onOpen() {

        }

        /**
         * 界面关闭
         */
        @Override
        public void onClose() {

        }

        /**
         * 界面进行滑动
         *
         * @param percent
         */
        @Override
        public void onDrag(float percent) {
            ViewHelper.setAlpha(top_bar_icon, 1 - percent);
        }
    }

    class CustomOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            drag_layout.open();
        }
    }

    /**
     * 广播注册
     */
    private void registerBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        mUpdateReceiver = new UpdateReceiver(false);
        mIntentFilter = new IntentFilter(UpdateReceiver.UPDATE_ACTION);
        localBroadcastManager.registerReceiver(mUpdateReceiver, mIntentFilter);
    }

    /**
     * 广播卸载
     */
    private void unRegisterBroadcast() {
        try {
            localBroadcastManager.unregisterReceiver(mUpdateReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            unRegisterBroadcast();
        } catch (Exception e) {
        }
    }

}

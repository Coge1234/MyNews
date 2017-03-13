package com.example.mynews.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynews.R;
import com.example.mynews.application.NEWSApplication;
import com.example.mynews.common.DefineView;
import com.example.mynews.entity.ITEM;
import com.example.mynews.ui.base.BaseActivity;
import com.example.mynews.widget.RoundAngleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailsActivity extends BaseActivity implements DefineView {
    private Button btn_back, btn_share, btn_font, btn_night;
//    private TextView details_title, details_name, details_time;
//    private RoundAngleImageView details_avatar;
    private ImageView details_ad;
    private WebView details_content;
    private FrameLayout home_framelayout;
    private LinearLayout loading, empty, error;
    private ITEM mITEM;
    private String titleUrl,titleId;
    private ImageLoader mImageLoader;
    private RelativeLayout relative_content;
    private ProgressDialog dialog;
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            bindData();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        setStatusBar();
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {
        btn_back = (Button) this.findViewById(R.id.btn_back);
        btn_share = (Button) this.findViewById(R.id.btn_share);
        btn_font = (Button) this.findViewById(R.id.btn_font);
        btn_night = (Button) this.findViewById(R.id.btn_night);
//        details_title = (TextView) this.findViewById(R.id.details_title);
//        details_name = (TextView) this.findViewById(R.id.details_name);
//        details_time = (TextView) this.findViewById(R.id.details_time);
//        details_avatar = (RoundAngleImageView) this.findViewById(R.id.details_avatar);
//        details_ad = (ImageView) this.findViewById(R.id.details_ad);

        details_content = (WebView) this.findViewById(R.id.details_content);
        home_framelayout = (FrameLayout) this.findViewById(R.id.prompt_framelayout);
        loading = (LinearLayout) this.findViewById(R.id.loading);
        empty = (LinearLayout) this.findViewById(R.id.empty);
        error = (LinearLayout) this.findViewById(R.id.error);

        relative_content = (RelativeLayout) this.findViewById(R.id.relative_content);

    }

    @Override
    public void initValidata() {
        mImageLoader=ImageLoader.getInstance();
        Intent mIntent = getIntent();
        mITEM = (ITEM) mIntent.getSerializableExtra("news_item");
        relative_content.setVisibility(View.GONE);
        home_framelayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        if (mITEM == null){
            relative_content.setVisibility(View.GONE);
            home_framelayout.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        }else{
            bindData();
        }
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        btn_share.setOnClickListener(new CustomOnClickListener());
        btn_font.setOnClickListener(new CustomOnClickListener());
        btn_night.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {
        relative_content.setVisibility(View.VISIBLE);
        home_framelayout.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

        //WebView加载本地资源
        details_content.loadUrl("http://36kr.com/p/"+mITEM.id+".html");
        details_content.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开
                view.loadUrl(url);
                return true;
            }
            //WebViewClient帮助WebView去处理一些页面控制和请求通知
        });
        //启用支持Javascript
        WebSettings settings = details_content.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载
        details_content.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //页面加载完成，关闭ProgressDialog
                    closeDialog();
                } else {
                    //网页正在加载，打开ProgressDialog
                    openDialog(newProgress);
                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(DetailsActivity.this);
                    dialog.setTitle("正在加载");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.setCancelable(true);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);
                }
            }

            private void closeDialog() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
//        details_title.setText(mITEM.title);
//        mImageLoader.displayImage(mITEM.user.avatar_url,details_avatar);
//        details_name.setText(mITEM.user.name);
//        details_time.setText(" 发表于:"+mITEM.updated_at);
//        mImageLoader.displayImage(mITEM.cover,details_ad);
    }

    class CustomOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    DetailsActivity.this.finish();
                    break;
                case R.id.btn_share:
                    Toast.makeText(NEWSApplication.getInstance(),"点击了分享按钮", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_night:
                    Toast.makeText(NEWSApplication.getInstance(),"点击了白天/黑夜切换按钮",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.btn_font:
                    Toast.makeText(NEWSApplication.getInstance(),"点击了字体按钮",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (details_content.canGoBack()) {
                details_content.goBack();   //返回上一页面
                return true;
            } else {
                finish();     //退出页面
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}

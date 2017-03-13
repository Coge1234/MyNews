package com.example.mynews.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mynews.R;
import com.example.mynews.adapter.LeftItemAdapter;
import com.example.mynews.common.DefineView;
import com.example.mynews.ui.base.BaseActivity;
import com.example.mynews.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends BaseActivity implements DefineView {

    private DragLayout drag_layout;
    private ImageView top_bar_icon;
    private ListView lv_left_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();
    }

    public DragLayout getDrag_layout(){
        return drag_layout;
    }

    public void initView() {
        drag_layout = (DragLayout) findViewById(R.id.drag_layout);
        top_bar_icon = (ImageView) findViewById(R.id.top_bar_icon);
        lv_left_menu =(ListView)findViewById(R.id.lv_left_main);
    }
    @Override
    public void initValidata() {
        lv_left_menu.setAdapter(new LeftItemAdapter());
    }
    @Override
    public void initListener() {
        drag_layout.setDragListener(new CustomDragListener());
        top_bar_icon.setOnClickListener(new CustomOnClickListener());

        lv_left_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
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
    class CustomDragListener implements DragLayout.DragListener{

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
         * @param percent
         */
        @Override
        public void onDrag(float percent) {
            ViewHelper.setAlpha(top_bar_icon,1-percent);
        }
    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View arg0) {
            drag_layout.open();
        }
    }
}

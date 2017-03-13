package com.example.mynews.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/12.
 */

public class ITEM implements Serializable {

    public String id;//文章的ID
    public String title;//文章的标题
    public String summary;//文章的概括
    public String cover;//文章的图片地址
    public String updated_at;//文章的更新时间
    public COLUMN column;//标签
    public USER user;//作者信息

    public class COLUMN implements Serializable{
        public String name;//标签的名字
        public String bg_color;//标签名字的颜色
    }

    public class USER implements Serializable{
        public String id;//作者的ID
        public String name;//作者的名字
        public String avatar_url;//作者的头像地址
    }
}

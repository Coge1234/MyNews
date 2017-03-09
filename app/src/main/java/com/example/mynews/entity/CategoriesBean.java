package com.example.mynews.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/9.
 */

public class CategoriesBean implements Serializable {
    private String title;//分类Tab名称
    private String href;//分类点击类型
    private String data_type;//分类类型

    public CategoriesBean(String title, String href, String data_type) {
        this.title = title;
        this.href = href;
        this.data_type = data_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    @Override
    public String toString() {
        return "CategoriesBean{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", data_type='" + data_type + '\'' +
                '}';
    }
}

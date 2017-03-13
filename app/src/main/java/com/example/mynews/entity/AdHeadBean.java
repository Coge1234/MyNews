package com.example.mynews.entity;

/**
 * 首页顶部广告条信息实体类
 *
 * @author jiangqq
 */
public class AdHeadBean {
    private String id; // 文章Id
    private String title; // 标题
    private String imgurl; // 图片地址
    private String mask; // 文章类型

    public AdHeadBean(String id, String title, String imgurl, String mask) {
        this.id = id;
        this.title = title;
        this.imgurl = imgurl;
        this.mask = mask;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public String toString() {
        return "AdHeadBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", mask='" + mask + '\'' +
                '}';
    }
}

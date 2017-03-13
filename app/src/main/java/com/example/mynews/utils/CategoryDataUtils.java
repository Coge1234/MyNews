package com.example.mynews.utils;

import com.example.mynews.entity.CategoriesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */

public class CategoryDataUtils {
    public static List<CategoriesBean> getCategoryBeans(){
        List<CategoriesBean> beans = new ArrayList<>();
        beans.add(new CategoriesBean("最新文章","http://36kr.com/api/post?&b_id=&per_page=20","LatestArticle"));
        beans.add(new CategoriesBean("明星公司","http://36kr.com/api/post?column_id=23&b_id=&per_page=20","StarCompany"));
        beans.add(new CategoriesBean("早期项目","http://36kr.com/api/post?column_id=67&b_id=&per_page=20","EarlyProject"));
        beans.add(new CategoriesBean("深度报道","http://36kr.com/api/post?column_id=70&b_id=&per_page=20","IndepthReporting"));
        beans.add(new CategoriesBean("行业研究","http://36kr.com/api/post?column_id=71&b_id=&per_page=20","IndustryResearch"));
        return beans;
    }
}

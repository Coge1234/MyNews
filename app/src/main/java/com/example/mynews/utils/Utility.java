package com.example.mynews.utils;

import com.example.mynews.entity.DATA;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/11.
 */

public class Utility {
    /**
     * 将返回的JSON数据解析成DATA实体类
     */
    public static DATA handleDATAResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsondata = jsonObject.getJSONObject("data");
            String dataContent = jsondata.toString();
            return new Gson().fromJson(dataContent,DATA.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

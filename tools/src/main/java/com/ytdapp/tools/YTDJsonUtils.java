package com.ytdapp.tools;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ytdapp.tools.log.YTDLog;

import java.lang.reflect.Type;

/**
 * json解析
 */
public class YTDJsonUtils {

    /**
     * json字符串转实体类
     * @param jsonStr
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T>T strToModel(String jsonStr, Class<T> classOfT){
        if (TextUtils.isEmpty(jsonStr)){
            return null;
        }

        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonStr, classOfT);
        } catch (JsonSyntaxException e) {
            YTDLog.log(e);
            return null;
        }
    }

    /**
     * json字符串转泛型
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     */
    public static <T>T strToType(String jsonStr, Type type){
        if (TextUtils.isEmpty(jsonStr)){
            return null;
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            YTDLog.log(e);
            return null;
        }
    }

    /**
     * json对象 转 实体类
     * @param object
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T>T objectToModel(Object object, Class<T> classOfT){
        if (object == null){
            return null;
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(gson.toJson(object), classOfT);
        } catch (JsonSyntaxException e) {
            YTDLog.log(e);
            return null;
        }
    }

    /**
     * 对象转泛型对象
     * @param object model 或 json
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T>T objectToType(Object object, Type typeOfT){
        if(object == null){
            return null;
        }

        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(object), typeOfT);
        } catch (JsonSyntaxException e) {
            YTDLog.log(e);
            return null;
        }
    }

    public static String objectToJsonStr(Object object){
        if (object == null) {
            return null;
        }
        Gson gson = new Gson();
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            YTDLog.log(e);
            return null;
        }
    }
}

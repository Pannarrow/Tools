package com.ytdapp.tools;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ytdapp.tools.log.YTDLog;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class YTDMapUtils {
    public static boolean isNoEmpty(Map<?,?> map){
        return (map != null && !map.isEmpty());
    }

    public static boolean isEmpty(Map<?,?>  map){
        return (map == null || map.isEmpty());
    }

    public static int valueOf(Map<?,?> map,Object obj){
        if (YTDMapUtils.isEmpty(map)){
            return 0;
        }

        Object value = map.get(obj);
        if (value == null){
            return 0;
        }

        return YTDParseUtil.parseInt(String.valueOf(value));
    }

    /**
     * 获取map中第一个数据值
     *
     * @param map 数据源
     * @return 第一个值
     */
    public static Object getFirstOrNull(Map<?,?> map) {
        Object obj = null;
        for (Map.Entry<?,?> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return  obj;
    }

    public static <T> T mapToObject(Map<?,?> map,Class<T> classOfT){
        if (YTDMapUtils.isEmpty(map)){
            return null;
        }

        Gson gson = new Gson();
        try {
            return gson.fromJson(gson.toJson(map), classOfT);
        } catch (JsonSyntaxException e) {
            YTDLog.log(e);
            return null;
        }
    }

    public static <T> T mapToObject(Map<?,?> map,Type type){
        if (YTDMapUtils.isEmpty(map)){
            return null;
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(gson.toJson(map), type);
        } catch (JsonSyntaxException e) {
            YTDLog.log(e);
            return null;
        }
    }

    public static Map<String, Object> objectToMap(Object object){
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        return  gson.fromJson(jsonString, Map.class);
    }

    /**
     * 从map中取出map
     * @param map
     * @param key
     * @return
     */
    public static Map<?, ?> optMapValue(Map<?, ?> map, Object key) {
        if (map != null && map.containsKey(key)) {
            Object o = map.get(key);
            if (o instanceof Map) {
                return (Map)o;
            }
        }
        return null;
    }

    /**
     * 从map中取出list
     * @param map
     * @param key
     * @return
     */
    public static List<?> optListValue(Map<?, ?> map, Object key) {
        if (map != null && map.containsKey(key)) {
            Object o = map.get(key);
            if (o instanceof List) {
                return (List)o;
            }
        }
        return null;
    }

    /**
     * json字符串转map
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> stringToMap(String jsonStr) {
        Map<String, Object> res = null;
        try {
            Gson gson = new Gson();
            res = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
        }
        return res;
    }
}

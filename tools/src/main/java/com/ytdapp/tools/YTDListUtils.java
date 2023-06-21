package com.ytdapp.tools;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ytdapp.tools.log.YTDLog;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class YTDListUtils {
    public static boolean isNoEmpty(Collection<?> list){
        return (list != null && !list.isEmpty());
    }

    public static boolean isEmpty(Collection<?> list){
        return (list == null || list.isEmpty());
    }

    public static <T> T listToObjects(List<?> list, Type typeOfT){
        if(isEmpty(list)){
            return null;
        }

        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(list),typeOfT);
        } catch (JsonSyntaxException e) {
            YTDLog.log(e);
            return null;
        }
    }
}

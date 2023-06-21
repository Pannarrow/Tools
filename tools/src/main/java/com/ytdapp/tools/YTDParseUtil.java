package com.ytdapp.tools;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用解析字符串工具
 */
public class YTDParseUtil {

    public static int parseInt(String value, int defaultValue){
        if (TextUtils.isEmpty(value)){
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static int parseInt(String value){
        if (TextUtils.isEmpty(value)){
            return 0;
        }
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            return 0;
        }
    }

    public static float parseFloat(String value, float defaultValue){
        if (TextUtils.isEmpty(value)){
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static float parseFloat(String value){
        if (TextUtils.isEmpty(value)){
            return 0F;
        }
        try {
            return Float.parseFloat(value);
        }catch (Exception e){
            return 0F;
        }
    }

    public static long parseLong(String value, long defaultValue){
        if (TextUtils.isEmpty(value)){
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static long parseLong(String value){
        if (TextUtils.isEmpty(value)){
            return 0L;
        }
        try {
            return Long.parseLong(value);
        }catch (Exception e){
            return 0L;
        }
    }

    public static double parseDouble(String value, int defaultValue){
        if (TextUtils.isEmpty(value)){
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static double parseDouble(String value){
        if (TextUtils.isEmpty(value)){
            return 0;
        }
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            return 0;
        }
    }

    public static boolean parseBoolean(String value, boolean defaultValue){
        if (TextUtils.isEmpty(value)){
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static boolean parseBoolean(String value){
        if (TextUtils.isEmpty(value)){
            return false;
        }
        try {
            return Boolean.parseBoolean(value);
        }catch (Exception e){
            return false;
        }
    }

    public static int parseColor(String colorValue, int defaultValue) {
        if (TextUtils.isEmpty(colorValue)){
            return defaultValue;
        }
        try {
            return Color.parseColor(colorValue);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static int parseColor(String colorValue) {
        if (TextUtils.isEmpty(colorValue)){
            return Color.WHITE;
        }
        try {
            return Color.parseColor(colorValue);
        }catch (Exception e){
            return Color.WHITE;
        }
    }

    public static Map<String, String> parseBundle(Bundle bundle) {
        Map<String,String> query = new HashMap<>();
        if (bundle != null) {
            for(String key : bundle.keySet()){
                if (bundle.containsKey(key)) {
                    query.put(key, String.valueOf(bundle.get(key)));
                }
            }
        }
        return query;
    }
}

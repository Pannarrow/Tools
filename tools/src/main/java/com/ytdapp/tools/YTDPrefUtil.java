package com.ytdapp.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 用于本地数据（少量 频繁）存储
 */
public class YTDPrefUtil {
    private static final String SharedPreferencesKey = "YTDSharedPreferences";

    /**
     * 环境切换baseUrl存储key
     */
    private static final String HTTP_BASE_URL_EVN_KEY = "HTTP_BASE_URL_EVN_KEY";

    /**
     * 搜索记录存储key
     * @return
     */
    private static final String SEARCH_HISTORY_KEY = "SEARCH_HISTORY_KEY";

    /**
     * 搜索订单记录存储key
     * @return
     */
    private static final String SEARCH_ORDER_HISTORY_KEY = "SEARCH_ORDER_HISTORY_KEY";

    /**
     * 多语言存储key
     */
    private static final String SAVED_LANGUAGE_KEY = "SAVED_LANGUAGE_KEY";

    /**
     * 记录设备唯一标识
     */
    private static final String KEY_DEVICE_ID = "KEY_DEVICE_ID";

    private static SharedPreferences getBase() {
        Context context = ContextUtil.getContext();
        return context.getSharedPreferences(SharedPreferencesKey, Context.MODE_PRIVATE);
    }

    public static void setValue(String key, String value) {
        if (getBase() == null) {
            return;
        }
        SharedPreferences.Editor editor = getBase().edit().putString(key, value);
        editor.apply();
    }

    public static void setValue(String key, boolean value) {
        if (getBase() == null) {
            return;
        }
        SharedPreferences.Editor editor = getBase().edit().putBoolean(key, value);
        editor.apply();
    }

    public static void setValue(String key, int value) {
        if (getBase() == null) {
            return;
        }
        SharedPreferences.Editor editor = getBase().edit().putInt(key, value);
        editor.apply();
    }

    public static void setValue(String key, long value) {
        if (getBase() == null) {
            return;
        }
        SharedPreferences.Editor editor = getBase().edit().putLong(key, value);
        editor.apply();
    }

    public static void setValue(String key, Set<String> value) {
        if (getBase() == null) {
            return;
        }
        SharedPreferences.Editor editor = getBase().edit().putStringSet(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defValue) {
        if (getBase() == null) {
            return defValue;
        }
        return getBase().getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        if (getBase() == null) {
            return defValue;
        }
        return getBase().getLong(key, defValue);
    }

    public static String getString(String key) {
        if (getBase() == null) {
            return "";
        }
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        if (getBase() == null) {
            return defValue;
        }
        return getBase().getString(key, defValue);
    }

    public static boolean getBoolean(String key) {
        if (getBase() == null) {
            return false;
        }
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (getBase() == null) {
            return defValue;
        }
        return getBase().getBoolean(key, defValue);
    }

    public static void removeValue(String key) {
        if (getBase() == null) {
            return;
        }
        SharedPreferences.Editor editor = getBase().edit().remove(key);
        editor.apply();
    }

    public static void setBaseURL(String baseURL) {
        setValue(HTTP_BASE_URL_EVN_KEY, baseURL);
    }

    public static String getBaseURL() {
        return getString(HTTP_BASE_URL_EVN_KEY, "");
    }

    public static void addSearchHistory(String searchValue) {
        addSearchHistory(SEARCH_HISTORY_KEY, searchValue, 5);
    }

    public static List<String> getSearchHistory() {
        return getSearchHistory(SEARCH_HISTORY_KEY);
    }

    public static void addSearchOrderHistory(String searchValue) {
        addSearchHistory(SEARCH_ORDER_HISTORY_KEY, searchValue, 10);
    }

    public static List<String> getSearchOrderHistory() {
        return getSearchHistory(SEARCH_ORDER_HISTORY_KEY);
    }

    public static void clearSearchOrderHistory() {
        removeValue(SEARCH_ORDER_HISTORY_KEY);
    }

    /**
     * 添加历史记录
     * @param key 历史记录类型
     * @param searchValue 搜索内容
     * @param maxLength 允许保存的最大个数
     */
    public static void addSearchHistory(String key, String searchValue, int maxLength) {
        if (TextUtils.isEmpty(searchValue)) {
            return;
        }
        String searchHistory = getString(key, "");
        if (TextUtils.isEmpty(searchHistory)) {
            setValue(key, searchValue);
        } else {
            String value;
            String split = "=&=";
            if (searchHistory.contains(split)) {
                if (searchHistory.contains(split + searchValue + split)
                        || searchHistory.startsWith(searchValue + split)
                        || searchHistory.endsWith(split + searchValue)) {
                    return;
                }
                if (searchHistory.split(split).length >= maxLength) {
                    value = searchValue + split + searchHistory.substring(0, searchHistory.lastIndexOf(split));
                } else {
                    value = searchValue + split + searchHistory;
                }
            } else {
                if (searchHistory.equals(searchValue)) {
                    return;
                }
                value = searchValue + split + searchHistory;
            }
            setValue(key, value);
        }
    }

    public static List<String> getSearchHistory(String key) {
        String value = getString(key, "");
        String split = "=&=";
        if (TextUtils.isEmpty(value)) {
            return new ArrayList<>();
        } else if (value.contains(split)) {
            return Arrays.asList(value.split(split));
        } else {
            List<String> values = new ArrayList<>();
            values.add(value);
            return values;
        }
    }

    public static void setLanguage(String language) {
        setValue(SAVED_LANGUAGE_KEY, language);
    }

    public static String getLanguage() {
        return getString(SAVED_LANGUAGE_KEY, "");
    }

    public static String getDeviceID() {
        return getString("");
    }

    public static void setDeviceID(String deviceID) {
        setValue("", deviceID);
    }
}

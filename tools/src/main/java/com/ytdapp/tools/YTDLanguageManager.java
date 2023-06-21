package com.ytdapp.tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

import java.util.Locale;

import androidx.appcompat.view.ContextThemeWrapper;

/**
 * 切换多语言
 */
public class YTDLanguageManager {

    /**
     * 懒汉式单例
     */
    private static volatile YTDLanguageManager instance;

    public static YTDLanguageManager getInstance() {
        // 双向检查
        if (instance == null) {
            synchronized (YTDLanguageManager.class) {
                if (instance == null) {
                    instance = new YTDLanguageManager();
                }
            }
        }
        return instance;
    }

    public YTDLanguageManager() {
    }

    private String languageValue; // 设置的语言key值

    public boolean isNeedUpdateLanguage() {
        return !TextUtils.isEmpty(getLanguageValue());
    }

    private Locale getLocale() {
        String languageValue = getLanguageValue();
        if ("zh".equals(languageValue)) {
            return Locale.SIMPLIFIED_CHINESE; // 简体中文
        } else if ("en".equals(languageValue)){
            return Locale.ENGLISH;//其它都使用英文
        } else {
            return Locale.getDefault();
        }
    }

    public boolean isChinese() {
        return getLocale().getLanguage().equals("zh");
    }

    public String getLanguageValue() {
        if (TextUtils.isEmpty(languageValue)) {
            languageValue = YTDPrefUtil.getLanguage();
        }
        return languageValue;
    }

    public void setLanguageValue(String languageValue) {
        this.languageValue = languageValue;
        YTDPrefUtil.setLanguage(languageValue);
    }

    /**
     * 多语言BaseContext
     * @param context
     * @return
     */
    public Context getContextWrapper(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        Configuration config = getConfiguration(resources);
        config.fontScale = 1;
        Context configurationContext = context.createConfigurationContext(config);

        // 修复appcompat 1.2+版本导致多语言切换失败，传自定义的ContextThemeWrapper
        return new ContextThemeWrapper(new ContextWrapper(configurationContext),
                R.style.Theme_AppCompat_Empty) {
            @Override
            public void applyOverrideConfiguration(Configuration overrideConfiguration) {
                if (overrideConfiguration != null) {
                    overrideConfiguration.setTo(config);
                }
                super.applyOverrideConfiguration(overrideConfiguration);
            }
        };
    }

    /**
     * 更新app资源配置，不仅是多语言，横竖屏也需要更新
     * @param context
     */
    public void updateConfiguration(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        Configuration config = getConfiguration(resources);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    /**
     * 获取app多语言配置
     * @param resources
     */
    private Configuration getConfiguration(Resources resources) {
        Configuration config = resources.getConfiguration();
        Locale locale = getLocale();
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        return config;
    }
}

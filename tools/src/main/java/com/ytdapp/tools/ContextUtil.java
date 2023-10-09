package com.ytdapp.tools;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;

public class ContextUtil {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    /**
     * 保存上下文
     * @param c 上下文
     * @return
     */
    public static void init(Context c){
        context = c.getApplicationContext();
    }

    /**
     * 获取颜色值
     * @param colorId
     * @return
     */
    @ColorInt
    public static int getColor(@ColorRes int colorId) {
        return context.getColor(colorId);
    }

    /**
     * 获取mipmap资源
     * @param resName
     * @return
     */
    public static int getMipmapId(String resName) {
        return context.getResources().getIdentifier(resName, "mipmap", context.getPackageName());
    }

    /**
     * 获取Dimens资源
     * @param dimenId
     * @return
     */
    public static float getDimension(@DimenRes int dimenId) {
        return context.getResources().getDimension(dimenId);
    }
}

package com.ytdapp.tools;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

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
     * 检查权限
     * @param permission 权限名
     * @return
     */
    public static boolean checkSelfPermission(@NonNull String permission){
        if (context == null) {
            return false;
        }

        if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
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

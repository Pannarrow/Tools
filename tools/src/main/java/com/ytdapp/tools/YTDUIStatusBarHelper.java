package com.ytdapp.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX;

import androidx.fragment.app.FragmentActivity;

public class YTDUIStatusBarHelper {
    /**
     * 设置状态栏黑色字体图标
     *
     * @param activity 需要被处理的 Activity
     */
    public static void setStatusBarLightMode(FragmentActivity activity) {
        UltimateBarX.getStatusBar(activity).transparent().light(true).apply();
    }

    /**
     * 设置状态栏白色字体图标
     */
    public static void setStatusBarDarkMode(FragmentActivity activity) {
        UltimateBarX.getStatusBar(activity).transparent().light(false).apply();
    }

    /**
     * 获取状态栏的高度。
     */
    public static int getStatusbarHeight(Context context) {
        return UltimateBarX.getStatusBarHeight();
    }

    /**
     * 设置透明状态栏
     * @param activity
     */
    public static void setTranslucentStatus(FragmentActivity activity) {
        UltimateBarX.statusBar(activity).transparent().apply();
    }

    public static void setFitWindow(FragmentActivity activity, boolean isFitWindow) {
        UltimateBarX.getStatusBar(activity).fitWindow(isFitWindow).apply();
    }

    /**
     * 隐藏状态栏
     * @param activity
     */
    public static void hideStatusBar(Activity activity) {
        if (activity == null) return;
        Window window = activity.getWindow();
        if (window == null) return;
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        WindowManager.LayoutParams lp = window.getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setAttributes(lp);
    }
}

package com.ytdapp.tools;

import android.app.Application;

import com.hjq.toast.Toaster;

/**
 * Toast工具
 */
public class YTDToast {

    public static void init(Application context) {
        Toaster.init(context);
    }

    public static void toast(String message) {
        Toaster.show(message);
    }

    /**
     * 页面销毁时如果有
     */
    public static void cancel() {
        Toaster.cancel();
    }
}

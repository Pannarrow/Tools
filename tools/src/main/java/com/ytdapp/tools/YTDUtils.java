package com.ytdapp.tools;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.ytdapp.tools.log.YTDLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class YTDUtils {

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机信息
     *
     * @return  手机信息
     */
    public static String getPhoneModel() {
        return getSystemModel() + getSystemVersion();
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static String getIMEIDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                }else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        return deviceId;
    }


    /**
     * 获取自己应用内部的版本名
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * 获取自己应用内部的版本号
     */
    public static String getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取自己应用内部的Build号
     */
    public static String getBuildVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取自己应用名称
     */
    public static String getAppName(Context context) {
        String name = "";
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
            name = (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getTextFileContent(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(inputStreamReader);
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr + '\n');
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     * @author SHANHY
     * @date   2015年10月28日
     */
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public  static boolean evalJavaScript(String string){
        if (TextUtils.isEmpty(string)){
            return false;
        }
        boolean result = false;
        string = string.replace(" ","");
        if (string.length() == 0){
            return result;
        }

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            return (boolean) engine.eval(string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static float[] cornerRadii(float leftTop,float leftBottom,float rightTop,float rightBottom){
        return new float[]{dip2px(ContextUtil.getContext(),leftTop),dip2px(ContextUtil.getContext(),leftTop),
                dip2px(ContextUtil.getContext(),rightTop),dip2px(ContextUtil.getContext(),rightTop),
                dip2px(ContextUtil.getContext(),rightBottom),dip2px(ContextUtil.getContext(),rightBottom),
                dip2px(ContextUtil.getContext(),leftBottom),dip2px(ContextUtil.getContext(),leftBottom)};
    }

    public static float[] cornerRadiiDp(int leftTop,int leftBottom,int rightTop,int rightBottom){
        return new float[]{leftTop,leftTop, rightTop,rightTop, rightBottom,rightBottom, leftBottom,leftBottom};
    }

    /**
     * 是否是主进程
     * @return 如果是返回true,不是返回false
     */
    public static boolean isMainProcess(Context context) {
        String processName = getProcessName(context, android.os.Process.myPid());
        String currentProcess = context.getApplicationInfo().processName;
        return processName != null && processName.equals(currentProcess);
    }

    public static String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "";
    }

    /**
     * 关闭键盘
     */
    public static void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    //关闭系统键盘
    public static void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //显示系统键盘
    public static void openKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 比较版本号
     * @param version1
     * @param version2
     * @return 1 : version1 > version2, 0 : version1 = version2, -1 : version1 < version2
     */
    public static int versionCompareTo(String version1, String version2) {
        version1 = version1 == null ? "" : version1.replaceAll("[^\\d\\.]+", "");
        version2 = version2 == null ? "" : version2.replaceAll("[^\\d\\.]+", "");
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        List<Integer> version1List = new ArrayList<Integer>();
        List<Integer> version2List = new ArrayList<Integer>();
        for (String s : version1Array) {
            version1List.add(Integer.parseInt(s));
        }
        for (String s : version2Array) {
            version2List.add(Integer.parseInt(s));
        }
        int size = Math.max(version1List.size(), version2List.size());
        while (version1List.size() < size) {
            version1List.add(0);
        }
        while (version2List.size() < size) {
            version2List.add(0);
        }
        for (int i = 0; i < size; i++) {
            if (version1List.get(i) > version2List.get(i)) {
                return 1;
            }
            if (version1List.get(i) < version2List.get(i)) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * 拨打电话
     * @param context
     * @param phoneNumber
     */
    public static void phone(Context context, String phoneNumber) {
        try {
            if (phoneNumber.contains("#")) {
                String encode = java.net.URLEncoder.encode("#", "utf-8");
                phoneNumber = phoneNumber.replaceAll("#", encode);
            }
            Uri parse = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_DIAL, parse);
            if (context != null) {
                context.startActivity(intent);
            }
        } catch (UnsupportedEncodingException e) {
            YTDLog.log(e);
        }
    }
}

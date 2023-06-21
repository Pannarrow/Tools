package com.ytdapp.tools.log;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.format.Formatter;
import android.view.WindowManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class YTDCrashHandler implements Thread.UncaughtExceptionHandler {
    private static String PATH ;
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".txt";
    public static final String TAG = "CrashHandler";

    private static class holder{
        private static final YTDCrashHandler INSTANCE = new YTDCrashHandler();
    }

    private Context mContext;
    //    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public boolean initialized = false;
    public static boolean RECORD_LOG = true;


    private YTDCrashHandler() {
    }

    public static YTDCrashHandler getInstance() {
        return holder.INSTANCE;
    }

    //crashhandler初始化
    public void init(Context ctx) {
        mContext = ctx.getApplicationContext();
        PATH = ctx.getExternalCacheDir().getAbsolutePath() + "/crash/";
        Thread.setDefaultUncaughtExceptionHandler(this);
        initialized = true;
        YTDLog.i(TAG, "CrashHandler initialized...");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (YTDLog.DEBUG){
            dumpExceptionToSDCard(ex);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    public void dumpExceptionToSDCard(Throwable ex){
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date(current));
        // 以当前时间创建log文件
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        if (!RECORD_LOG) {
            return;
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            // 导出发生异常的时间
            pw.println(time);

            // 导出手机信息
            dumpPhoneInfo(pw);

            pw.println();
            // 导出异常的调用栈信息
            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            YTDLog.e(TAG, "dump crash info failed");
        } finally {
            if (pw != null){
                pw.close();
            }
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        pw.println();

        pw.print("手机型号 : " + android.os.Build.MODEL);
        pw.println();
        pw.print("系统信息 : " + android.os.Build.VERSION.SDK + "," + android.os.Build.VERSION.RELEASE);
        pw.println();

        pw.print("宽*高 : ");
        pw.print(getHeightAndWidth());
        pw.println();

        pw.print("可用内存大小  : ");
        pw.print(getAvailMemory());
        pw.println();
    }

    /**
     * 获取android当前可用内存大小
     */
    private String getAvailMemory() {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(mContext, mi.availMem);// 将获取的内存大小规格化
    }

    /**
     * 获得手机屏幕宽高
     *
     * @return
     */
    public String getHeightAndWidth() {
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = mWindowManager.getDefaultDisplay().getWidth();
        int height = mWindowManager.getDefaultDisplay().getHeight();
        return width + "*" + height;
    }
}

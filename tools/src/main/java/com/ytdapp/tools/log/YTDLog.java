package com.ytdapp.tools.log;
import android.net.TrafficStats;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.ytdapp.tools.ContextUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class YTDLog {
    private static String PATH;
    public static boolean DEBUG = true;
    public static int LEVEL = 0;
    public static String FILTERS;

    public final static int V = 0;
    public final static int D = 1;
    public final static int I = 2;
    public final static int W = 3;
    public final static int E = 4;

    public static void v(String tag, String message) {
        if (DEBUG && LEVEL <= V) {
            Log.v("YTDLog-" + tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG && LEVEL <= D) {
            Log.d("YTDLog-" + tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG && LEVEL <= I) {
            Log.i("YTDLog-" + tag, message);
        }
    }

    public static void logToSD(String tag, String message) {
        i(tag, message);
        if (DEBUG ) {
            try {
                writeToFile('I', tag, message);
            } catch (Exception e) {
                log(e);
            }
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG && LEVEL <= W) {
            Log.w("YTDLog-" + tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG && LEVEL <= E) {
            if (message != null) {
                Log.e("YTDLog-" + tag, message);
            }
        }
    }

    public static void log(Throwable e) {
        log(e, 1);
    }

    public static void log(Throwable e, int priority) {
        if (DEBUG){
            e.printStackTrace();
        }
        CrashReport.postCatchedException(e);
    }

    /**
     * 将log信息写入文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void writeToFile(char type, String tag, String msg) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);//日期格式;
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);//日期格式;

        Date date = new Date();
        PATH = ContextUtil.getContext().getExternalCacheDir().getAbsolutePath() + "/log/";
        String fileName = PATH + "/log" + dateFormat.format(date) + ".log";//log日志名，使用时间命名，保证不重复
        String log = dateFormat2.format(date) + " " + type + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
        try {
            if ("request".equals(tag)) {
                long mrb = TrafficStats.getMobileRxBytes();
                long mtb = TrafficStats.getMobileTxBytes();   //
                long tMrb = TrafficStats.getTotalRxBytes();   //
                long tMtb = TrafficStats.getTotalTxBytes();   //
                log += "\t手机接收的字节数,非WiFi状态:" + mrb + "\n";
                log += "\t手机发送的字节数，非WiFi状态:" + mtb + "\n";
                log += "\t全部接收的字节数:" + tMrb + "\n";
                log += "\t全部发送的字节数:" + tMtb + "\n";
            }
        } catch (Exception e) {
            log(e);
        }
        //如果父路径不存在
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {

            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (FileNotFoundException e) {
            log(e);
        } catch (IOException e) {
            log(e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                log(e);
            }
        }

    }
}

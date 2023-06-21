package com.ytdapp.tools.log;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.ytdapp.tools.ContextUtil;
import com.ytdapp.tools.YTDKeyConstant;
import com.ytdapp.tools.YTDUtils;

public class YTDCrashReport {

    /**
     * Bugly初始化
     * @param context
     * @param isDebug
     */
    public static void init(Context context, boolean isDebug) {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(YTDUtils.isMainProcess(context));
        strategy.setDeviceModel(YTDUtils.getPhoneModel());
        strategy.setAppVersion(YTDUtils.getVersionName(context));
        strategy.setEnableCatchAnrTrace(true);
        strategy.setEnableRecordAnrMainStack(true);
        CrashReport.initCrashReport(context, YTDKeyConstant.APP_ID_BUGLY, isDebug, strategy);
    }

    /**
     * 设置userId
     * @param userId
     */
    public static void setReportUserId(String userId) {
        CrashReport.setUserId(ContextUtil.getContext(), userId);
    }

    /**
     * 上报接口异常
     * @param apiName
     * @param requestParam
     * @param message
     * @param costTime
     */
    public static void postApiException(String apiName, String requestParam, String message, long costTime) {
        String msg = "apiName:" + apiName + ",message:" + message + ",costTime:" + costTime + ",requestParam:" + requestParam;
        CrashReport.postCatchedException(new YTDApiException(msg));
        YTDLog.logToSD("YTDHttp", msg);
    }

    private static class YTDApiException extends Exception {
        public YTDApiException(String message) {
            super(message);
        }
    }
}

package com.ytdapp.tools;

import android.content.Context;
import android.media.MediaDrm;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.ytdapp.tools.log.YTDLog;

import java.util.UUID;

public class YTDDeviceUtil {
    private static String sDeviceId = "";     //设备ID
    private static final String INVALID_ANDROID_ID = "9774d56d682e549c"; // 无效的androidID

    /**
     * 获取唯一码
     */
    public static String getUniqueIdCode(Context context) {
        if (!TextUtils.isEmpty(sDeviceId)) {
            return sDeviceId;
        }

        String deviceId = YTDPrefUtil.getDeviceID();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        // androidId
        deviceId = getAndroidId(context);
        if (TextUtils.isEmpty(deviceId)) {
            // mediaDrmID
            deviceId = getMediaDrmID();
        }

        String uuid;
        if (!TextUtils.isEmpty(deviceId)){
            uuid = UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
        } else {
            uuid = UUID.randomUUID().toString();
        }
        uuid = uuid.replaceAll("-", "").substring(0, 16);
        YTDPrefUtil.setDeviceID(uuid);
        sDeviceId = uuid;
        return uuid;
    }

    /**
     * 获取Media Drm ID
     * 数字音频用于追踪，保护版权所需的唯一设备ID
     * @return
     */
    public static String getMediaDrmID(){
        try {
            UUID uuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
            MediaDrm drm = new MediaDrm(uuid);
            byte[] array = drm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                drm.close();
            }
            return new String(array);
        } catch (Exception e) {
            YTDLog.log(e);
        }
        return null;
    }

    /**
     * 获得设备的AndroidId
     * * 通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置。并且，如果某个
     * * Andorid手机被Root过的话，这个ID也可以被任意改变。无需任何许可。
     *
     * @param context 上下文
     * @return 设备的AndroidId
     */
    private static String getAndroidId(Context context) {
        String androidId = "";
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            //ignore
        }
        if (INVALID_ANDROID_ID.equalsIgnoreCase(androidId)) {
            androidId = "";
        }
        return androidId;
    }
}

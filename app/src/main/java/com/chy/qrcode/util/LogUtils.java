package com.chy.qrcode.util;

import android.util.Log;


/**
 * 类名称：com.yundong.wifi.utils
 * 类描述：
 * 创建人：yd_10
 * 创建时间：2016/10/11 17:13
 * 修改人：
 * 修改时间：2016/10/11 17:13
 * 修改备注：
 */
public class LogUtils {
    private static final String TAG = "===================";
    private static boolean isLog = true;

    public static void e(String tag, String message) {
        if (isLog)
            Log.e(TAG + tag, message);
    }

    public static void e(String message) {
        if (isLog)
            Log.e(TAG, message);
    }

    public static void d(String tag, String message) {
        if (isLog)
            Log.d(TAG + tag, message);
    }

    public static void d(String message) {
        if (isLog)
            Log.d(TAG, message);
    }

    public static void i(String tag, String message) {
        if (isLog)
            Log.i(TAG + tag, message);
    }

    public static void i(String message) {
        if (isLog)
            Log.i(TAG, message);
    }
}

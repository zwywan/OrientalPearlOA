package com.geely.app.geelyapprove.common.utils;

import android.util.Log;

/**
 * Created on 2016/5/23.
 */
public class L {
    //日志开关，在应用测试完毕发布版本之前要置为false
    private static boolean isDebug = true;

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void d(Object obj, String msg) {
        if (isDebug) {
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }

    public static void e(Object obj, String msg) {
        if (isDebug) {
            Log.e(obj.getClass().getSimpleName(), msg);
        }
    }

    /**
     * 判断是否为空
     *
     * @param name
     * @param object
     */
    public static void n(String name, Object object) {
        if (isDebug) {
            Log.e(name, object == null ? "is null" : "not null");
        }
    }
}


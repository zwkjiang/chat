package com.bawei6.common;

import android.util.Log;

public class LogUtils {
    private static final String TAG="zylog";
    public static void d(String log){
        Log.d(TAG,log);
    }
    public static void i(String log){
        Log.i(TAG,log);
    }
    public static void w(String log){
        Log.w(TAG,log);
    }
    public static void e(String log){
        Log.e(TAG,log);
    }
    public static void v(String log){
        Log.v(TAG,log);
    }
}

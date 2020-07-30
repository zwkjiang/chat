package com.example.common.utils;

import android.util.Log;

public class LogUtils {
    private static LogUtils logUtils;
    public static LogUtils getInstance(){
        if (logUtils==null){
            synchronized (LogUtils.class){
                if (logUtils==null){
                    logUtils = new LogUtils();
                }
            }
        }
        return logUtils;
    }

    private final String TAG="snake";
    private final boolean isDebug = true;

    public void d(String tag,String log){
        if(isDebug){
            if (tag==""){
                Log.d(tag,log);
            }else{
                Log.d(TAG,log);
            }
        }
    }
    public void e(String tag,String log){
        if(isDebug){
            if (tag==""){
                Log.e(tag,log);
            }else{
                Log.e(TAG,log);
            }
        }
    }
    public void i(String tag,String log){
        if(isDebug){
            if (tag==""){
                Log.i(tag,log);
            }else{
                Log.i(TAG,log);
            }
        }
    }
    public void w(String tag,String log){
        if(isDebug){
            if (tag==""){
                Log.w(tag,log);
            }else{
                Log.w(TAG,log);
            }
        }
    }
    public void v(String tag,String log){
        if(isDebug){
            if (tag==""){
                Log.v(tag,log);
            }else{
                Log.v(TAG,log);
            }
        }
    }
}

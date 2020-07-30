package com.example.common;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.telecom.Call;

import com.alibaba.android.arouter.launcher.ARouter;

public abstract class BaseApplication extends Application {
    private static Application context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initOtherConfig();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    protected abstract void initOtherConfig();
    public static Context getApplication(){
        return context;
    }
}

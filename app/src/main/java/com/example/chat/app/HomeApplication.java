package com.example.chat.app;

import android.util.Log;

import androidx.multidex.MultiDex;

import com.example.arouter.RouterManager;
import com.example.common.BaseApplication;
import com.facebook.drawee.backends.pipeline.Fresco;

public class HomeApplication extends BaseApplication {
    @Override
    protected void initOtherConfig() {
        RouterManager.getInstance().init(this,true);
        Fresco.initialize(this);
        MultiDex.install(this);
    }
}

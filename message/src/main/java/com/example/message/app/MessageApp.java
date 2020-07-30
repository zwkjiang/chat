package com.example.message.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouter.RouterManager;
import com.example.common.BaseApplication;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MessageApp extends BaseApplication {

    @Override
    protected void initOtherConfig() {
        RouterManager.getInstance().init(this,true);
        Fresco.initialize(this);
    }
}

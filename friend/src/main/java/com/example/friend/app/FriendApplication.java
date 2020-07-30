package com.example.friend.app;

import com.example.arouter.RouterManager;
import com.example.common.BaseApplication;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.ZApplication;

public class FriendApplication extends BaseApplication {
    @Override
    protected void initOtherConfig() {
        RouterManager.getInstance().init(this,true);
        Fresco.initialize(this);
    }
}

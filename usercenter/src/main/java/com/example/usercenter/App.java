package com.example.usercenter;

import android.util.Log;

import com.baweigame.xmpplibrary.XmppManager;
import com.example.arouter.RouterManager;
import com.example.common.BaseApplication;

public class App extends BaseApplication {
    @Override
    protected void initOtherConfig() {
        Log.d("zwk","di");
        RouterManager.getInstance().init(this,true);
    }
}

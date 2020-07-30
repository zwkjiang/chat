package com.example.common.utils;

import android.content.Context;
import android.widget.Toast;

public class MsgUtils {
    private static MsgUtils msgUtils;
    public static MsgUtils getInstance(){
        if (msgUtils==null){
            synchronized (MsgUtils.class){
                if (msgUtils==null){
                    msgUtils = new MsgUtils();
                }
            }
        }
        return msgUtils;
    }

    public void show(Context context,String mes){
        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
    }
}

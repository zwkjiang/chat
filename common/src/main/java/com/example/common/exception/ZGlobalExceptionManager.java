package com.example.common.exception;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.common.utils.LogUtils;

public class ZGlobalExceptionManager implements Thread.UncaughtExceptionHandler{
    private Context context;
    private static ZGlobalExceptionManager zGlobalExceptionManager;
    private Thread.UncaughtExceptionHandler exceptionHandler;
    public static ZGlobalExceptionManager getInstance(){
        if (zGlobalExceptionManager==null){
            synchronized (ZGlobalExceptionManager.class){
                if (zGlobalExceptionManager==null){
                    zGlobalExceptionManager = new ZGlobalExceptionManager();
                }
            }
        }
        return zGlobalExceptionManager;
    }

    private ZGlobalExceptionManager() {
    }

    public void init(Context context){
        this.context = context;
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        if (!handlerException(thread,throwable)){
            exceptionHandler.uncaughtException(thread,throwable);
        }else{
            SystemClock.sleep(3000);
            Process.killProcess(Process.myPid());
            System.exit(10);

        }
    }

    private boolean handlerException(Thread thread,Throwable throwable){
        if(thread==null&&throwable==null){
            return false;
        }
        final String message = throwable.getMessage();
        String name = thread.getName();
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Toast.makeText(context, "哎呀程序挂了", Toast.LENGTH_SHORT).show();
                LogUtils.getInstance().e("",message);
                Looper.loop();
            }
        }.start();
        return true;
    }

}

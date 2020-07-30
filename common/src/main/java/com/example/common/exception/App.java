package com.example.common.exception;

import android.app.Application;

import androidx.annotation.NonNull;

public class App extends Application implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler exceptionHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {

    }
}

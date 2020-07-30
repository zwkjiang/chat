package com.example.net.calladapter;

import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.net.entity.RequestEntity;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<RequestEntity<R>>> {
    private Type type;

    public LiveDataCallAdapter(Type type) {
        this.type = type;
    }

    @Override
    public Type responseType() {
        return type;
    }

    @Override
    public LiveData<RequestEntity<R>> adapt(Call<R> call) {
        final MutableLiveData<RequestEntity<R>> objectMutableLiveData = new MutableLiveData<>();
        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
                if (Looper.getMainLooper().getThread()==Thread.currentThread()){
                    objectMutableLiveData.setValue((RequestEntity<R>) response.body());
                }else{
                    objectMutableLiveData.postValue((RequestEntity<R>) response.body());
                }
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
                if (Looper.getMainLooper().getThread()==Thread.currentThread()){
                    objectMutableLiveData.setValue(null);
                }else{
                    objectMutableLiveData.postValue(null);
                }
            }
        });
        return objectMutableLiveData;
    }
}

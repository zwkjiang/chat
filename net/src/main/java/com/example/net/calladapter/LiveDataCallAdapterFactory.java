package com.example.net.calladapter;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory{
    public static LiveDataCallAdapterFactory create(){
        return new LiveDataCallAdapterFactory();
    }
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (!(returnType instanceof ParameterizedType)){
            throw new IllegalArgumentException("返回值必须为参数化的类型");
        }
        Class<?> rawType = CallAdapter.Factory.getRawType(returnType);
        if (rawType!= LiveData.class&&rawType!= Call.class){
            throw  new IllegalArgumentException("返回值类型必须为ViewData|Call类型");
        }
        Type t = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        if (rawType==Call.class){
            return new DefaultCallAdapter(t);
        }
        return new LiveDataCallAdapter<>(t);
    }
}

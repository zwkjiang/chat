package com.example.usercenter.model;

import androidx.lifecycle.LiveData;

import com.example.core.model.IModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.net.net.RetrofitFactory;

public class RegisterModel implements IModel {

    private final String TAG = RegisterModel.class.getSimpleName();
    private UserApi userApi;

    public RegisterModel(){
        userApi = RetrofitFactory.getInstance().getRetrofit().create(UserApi.class);
    }
    public LiveData<RequestEntity<UserEntity>> register(UserEntity userEntity){
        LiveData<RequestEntity<UserEntity>> register = userApi.register(userEntity);
        return register;
    }
}

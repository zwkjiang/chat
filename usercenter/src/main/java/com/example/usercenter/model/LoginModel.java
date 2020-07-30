package com.example.usercenter.model;

import androidx.lifecycle.LiveData;

import com.example.core.model.IModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.net.net.RetrofitFactory;
import com.example.usercenter.entity.LoginEntity;

public class LoginModel implements IModel {

    private final String TAG = LoginModel.class.getSimpleName();
    private UserApi userApi;

    public LoginModel(){
        userApi = RetrofitFactory.getInstance().getRetrofit().create(UserApi.class);
    }
    public LiveData<RequestEntity<UserEntity>> login(LoginEntity loginEntity){
        LiveData<RequestEntity<UserEntity>> login = userApi.login(loginEntity);
        return login;
    }

}

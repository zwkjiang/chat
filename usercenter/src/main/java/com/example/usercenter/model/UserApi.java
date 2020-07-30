package com.example.usercenter.model;

import androidx.lifecycle.LiveData;

import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.usercenter.entity.LoginEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("api/User/register")
    LiveData<RequestEntity<UserEntity>> register(@Body UserEntity userEntity);

    @POST("api/User/login")
    LiveData<RequestEntity<UserEntity>> login(@Body LoginEntity loginEntity);
}

package com.example.usercenter.viewmodel;

import androidx.lifecycle.LiveData;

import com.example.core.viewmodel.BaseViewModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.usercenter.repostory.RegisterRepository;

public class RegisterViewModel extends BaseViewModel {
    public UserEntity userEntity;
    private final String TAG =RegisterViewModel.class.getSimpleName();
    public RegisterViewModel() {
        userEntity = new UserEntity();
        registerRepository(TAG,new RegisterRepository());
    }
    public LiveData<RequestEntity<UserEntity>> register(){
        userEntity.setSex("1");
        RegisterRepository repository = getRepository(TAG);
        LiveData<RequestEntity<UserEntity>> register = repository.register(userEntity);
        return register;
    }
}

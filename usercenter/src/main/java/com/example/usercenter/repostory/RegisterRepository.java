package com.example.usercenter.repostory;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.usercenter.model.RegisterModel;

public class RegisterRepository extends Repository<RegisterModel> {
    @Override
    protected RegisterModel createModel() {
        return new RegisterModel();
    }
    public LiveData<RequestEntity<UserEntity>> register(UserEntity userEntity){
        LiveData<RequestEntity<UserEntity>> register = mModel.register(userEntity);
        return register;
    }
}

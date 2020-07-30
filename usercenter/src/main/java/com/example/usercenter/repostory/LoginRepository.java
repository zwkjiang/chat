package com.example.usercenter.repostory;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.usercenter.entity.LoginEntity;
import com.example.usercenter.model.LoginModel;

public class LoginRepository extends Repository<LoginModel> {
    @Override
    protected LoginModel createModel() {
        return new LoginModel();
    }
    public LiveData<RequestEntity<UserEntity>> login(LoginEntity loginEntity){
        LiveData<RequestEntity<UserEntity>> login = mModel.login(loginEntity);
        return login;
    }
}

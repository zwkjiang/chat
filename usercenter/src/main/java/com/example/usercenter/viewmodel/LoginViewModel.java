package com.example.usercenter.viewmodel;

import androidx.lifecycle.LiveData;

import com.example.core.viewmodel.BaseViewModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.usercenter.entity.LoginEntity;
import com.example.usercenter.repostory.LoginRepository;

public class LoginViewModel extends BaseViewModel {
    public LoginEntity loginEntity;
    private final String TAG = LoginViewModel.class.getSimpleName();
    public LoginViewModel() {
        loginEntity = new LoginEntity();
        registerRepository(TAG,new LoginRepository());
    }
    public LiveData<RequestEntity<UserEntity>> login(){
        LoginRepository repository = getRepository(TAG);
        LiveData<RequestEntity<UserEntity>> login = repository.login(loginEntity);
        return login;
    }
}

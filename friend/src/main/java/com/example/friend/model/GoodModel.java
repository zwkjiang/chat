package com.example.friend.model;

import androidx.lifecycle.LiveData;

import com.example.core.model.IModel;
import com.example.friend.model.api.FriendApi;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.net.net.RetrofitFactory;

import java.util.List;

public class GoodModel implements IModel {

    public LiveData<RequestEntity<List<UserEntity>>> getFriend(){
        LiveData<RequestEntity<List<UserEntity>>> friend = RetrofitFactory.getInstance().getRetrofit().create(FriendApi.class)
                .getFriend("107e387803cb400da4ed31ad1905c9c9");
        return friend;
    }
}

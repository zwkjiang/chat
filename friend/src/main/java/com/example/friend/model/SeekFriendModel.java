package com.example.friend.model;

import androidx.lifecycle.LiveData;

import com.example.core.model.IModel;
import com.example.friend.model.api.FriendApi;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.net.net.RetrofitFactory;
import com.uuzuche.lib_zxing.ZApplication;

import java.util.List;

public class SeekFriendModel implements IModel{

    public LiveData<RequestEntity<List<UserEntity>>> seekFriend(String name){
        LiveData<RequestEntity<List<UserEntity>>> friend = RetrofitFactory.getInstance().getRetrofit().create(FriendApi.class)
                .seekFriend(name," ");
        return friend;
    }
    public LiveData<RequestEntity<Boolean>> addFriend(String code){
        LiveData<RequestEntity<Boolean>> requestEntityLiveData = RetrofitFactory.getInstance().getRetrofit().create(FriendApi.class)
                .addFried("107e387803cb400da4ed31ad1905c9c9", code);
        return requestEntityLiveData;
    }
}

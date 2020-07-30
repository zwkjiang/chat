package com.example.friend.repository;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.friend.model.SeekFriendModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;

import java.util.List;

public class SeekFriendRepository extends Repository<SeekFriendModel> {
    @Override
    protected SeekFriendModel createModel() {
        return new SeekFriendModel();
    }
    public LiveData<RequestEntity<List<UserEntity>>> seekFriend(String name){
        LiveData<RequestEntity<List<UserEntity>>> requestEntityLiveData = mModel.seekFriend(name);
        return requestEntityLiveData;
    }
    public LiveData<RequestEntity<Boolean>> addFriend(String code){
        LiveData<RequestEntity<Boolean>> requestEntityLiveData = mModel.addFriend(code);
        return requestEntityLiveData;
    }
}

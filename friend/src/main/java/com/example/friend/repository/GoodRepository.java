package com.example.friend.repository;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.friend.model.GoodModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;

import java.util.List;

public class GoodRepository extends Repository<GoodModel> {

    @Override
    protected GoodModel createModel() {
        return new GoodModel();
    }

    public LiveData<RequestEntity<List<UserEntity>>> getFriend(){
        LiveData<RequestEntity<List<UserEntity>>> friend = mModel.getFriend();
        return friend;
    }
}

package com.example.friend.viewmodel;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.core.viewmodel.BaseViewModel;
import com.example.friend.repository.SeekFriendRepository;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;

import java.util.List;

public class SeekFriendViewModel extends BaseViewModel{
    private final String TAG = SeekFriendViewModel.class.getSimpleName();

    public SeekFriendViewModel() {
        registerRepository(TAG,new SeekFriendRepository());
    }
    public LiveData<RequestEntity<List<UserEntity>>> seekFriend(String name){
        SeekFriendRepository repository = getRepository(TAG);
        return repository.seekFriend(name);
    }
    public LiveData<RequestEntity<Boolean>> addFriend(String code){
        SeekFriendRepository repository = getRepository(TAG);
        return repository.addFriend(code);
    }
}

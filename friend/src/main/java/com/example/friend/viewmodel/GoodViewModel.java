package com.example.friend.viewmodel;

import android.widget.TextView;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.core.viewmodel.BaseViewModel;
import com.example.friend.repository.GoodRepository;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;

import java.util.List;

public class GoodViewModel extends BaseViewModel {
    private final String TAG = GoodViewModel.class.getSimpleName();
    public GoodViewModel() {
        registerRepository(TAG,new GoodRepository());
    }

    public LiveData<RequestEntity<List<UserEntity>>> getFriend(){
        GoodRepository repository = getRepository(TAG);
        return repository.getFriend();
    }
}

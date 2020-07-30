package com.example.message.viewmodel;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.core.viewmodel.BaseViewModel;
import com.example.message.entity.MessageEntity;
import com.example.message.repository.SendMsgRepository;
import com.example.net.entity.RequestEntity;

public class SendMsgViewModel extends BaseViewModel {
    private final String TAG = SendMsgViewModel.class.getSimpleName();
    public SendMsgViewModel() {
        registerRepository(TAG,new SendMsgRepository());
    }
    public LiveData<RequestEntity<Boolean>> sendMessage(MessageEntity messageEntity){
        SendMsgRepository repository = getRepository(TAG);
        return repository.sendMessage(messageEntity);
    }
}

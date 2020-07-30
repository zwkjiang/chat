package com.example.message.repository;

import androidx.lifecycle.LiveData;

import com.example.core.repository.Repository;
import com.example.message.entity.MessageEntity;
import com.example.message.model.SendMsgModel;
import com.example.net.entity.RequestEntity;

public class SendMsgRepository extends Repository<SendMsgModel> {
    @Override
    protected SendMsgModel createModel() {
        return new SendMsgModel();
    }
    public LiveData<RequestEntity<Boolean>> sendMessage(MessageEntity messageEntity){
        LiveData<RequestEntity<Boolean>> isSend = mModel.sendMessage(messageEntity);
        return isSend;
    }
}

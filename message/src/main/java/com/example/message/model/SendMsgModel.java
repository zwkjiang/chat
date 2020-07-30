package com.example.message.model;

import androidx.lifecycle.LiveData;

import com.example.core.model.IModel;
import com.example.message.entity.MessageEntity;
import com.example.message.model.api.MessageApi;
import com.example.net.entity.RequestEntity;
import com.example.net.net.RetrofitFactory;

public class SendMsgModel implements IModel {

    public LiveData<RequestEntity<Boolean>> sendMessage(MessageEntity messageEntity){
        LiveData<RequestEntity<Boolean>> isSend = RetrofitFactory.getInstance().getRetrofit().create(MessageApi.class)
                .sendMessage(messageEntity);
        return isSend;
    }
}

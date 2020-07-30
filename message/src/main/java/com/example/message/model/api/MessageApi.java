package com.example.message.model.api;

import androidx.lifecycle.LiveData;

import com.example.message.entity.MessageEntity;
import com.example.net.entity.RequestEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageApi {

    @POST("api/Chat/addChatMsg")
    LiveData<RequestEntity<Boolean>> sendMessage(@Body MessageEntity messageEntity);
}

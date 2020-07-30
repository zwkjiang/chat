package com.example.friend.model.api;

import androidx.lifecycle.LiveData;

import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FriendApi {

    @GET("api/Friend/getFriends")
    LiveData<RequestEntity<List<UserEntity>>> getFriend(@Query("usercode") String usercode);

    @GET("api/Friend/searchFriend")
    LiveData<RequestEntity<List<UserEntity>>> seekFriend(@Query("username") String username,@Query("nick") String nick);

    @POST("api/Friend/addFriend")
    LiveData<RequestEntity<Boolean>> addFried(@Query("usercode") String usercode,@Query("friendcode") String friendcode);
}

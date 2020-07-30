package com.example.net.net;

import com.example.net.api.TokenApi;
import com.example.net.calladapter.DefaultCallAdapter;
import com.example.net.calladapter.LiveDataCallAdapterFactory;
import com.example.net.common.Config;
import com.example.net.protocol.TokenRequestEntity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static RetrofitFactory retrofitFactory;
    public static RetrofitFactory getInstance(){
        if (retrofitFactory ==null){
            synchronized (RetrofitFactory.class){
                retrofitFactory = new RetrofitFactory();
            }
        }
        return retrofitFactory;
    }
    private String url="http://api.zydeveloper.com:10001/";

    public void setUrl(String url) {
        this.url = url;
    }

    private Gson gson;
    public Gson getGson(){
        if(gson==null){
            gson = new Gson();
        }
        return gson;
    }

    private RetrofitFactory() {
    }
    private Retrofit retrofit;
    public Retrofit getRetrofit(){
        if (retrofit==null){
            createRetrofit();
        }
        return retrofit;
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .client(createClient())
                .build();
    }

    private OkHttpClient createClient() {
        OkHttpClient build = new OkHttpClient.Builder()
                    .writeTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)
                .connectTimeout(3000, TimeUnit.SECONDS)
                .addNetworkInterceptor(createLog())
                .addInterceptor(createToken())
                .build();
        return build;
    }

    private Interceptor createToken() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response proceed = chain.proceed(request);
                int code = proceed.code();
                if (code==401){
                   String token = requestToken();
                    Request.Builder authorization = request.newBuilder().addHeader("Authorization", "bearer " + token);
                    Request build = authorization.build();
                    return chain.proceed(build);
                }
                return proceed;
            }
        };
        return interceptor;
    }

    private String requestToken() {
        Call<TokenRequestEntity> token = create(TokenApi.class).getToken("password", Config.AUTO_CODE, "");
        try {
            retrofit2.Response<TokenRequestEntity> execute = token.execute();
            if (execute!=null&&execute.body()!=null){
                return execute.body().getAccess_token();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Interceptor createLog() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }
}

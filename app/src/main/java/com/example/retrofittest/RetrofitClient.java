package com.example.retrofittest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.137.1/Retrofit/";
    private static RetrofitClient my_client;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static  synchronized RetrofitClient getInstance(){
        if(my_client == null){
            my_client = new RetrofitClient();
        }
        return my_client;
    }

    public APIsInterface getAPI(){
        return retrofit.create(APIsInterface.class);
    }
}

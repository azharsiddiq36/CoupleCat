package com.azhar.couplecat.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;
    public static Retrofit retrofit2 = null;
    public static Retrofit retrofit3 = null;
    public static final String URL_RAJA_API = "https://x.rajaapi.com/";
    public static Retrofit getApiClient(String BASE_URL){
        if(retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getTheCatApiClient(String BASE_URL){
        if(retrofit2==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit2 = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit2;
    }
    public static Retrofit getClient() {
        if (retrofit3 == null) {
            retrofit3 = new Retrofit.Builder()
                    .baseUrl(URL_RAJA_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit3;
    }
}

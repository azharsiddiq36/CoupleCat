package com.azhar.couplecat.Rest;

public class CombineApi {
/*
    public static final String BASE_URL = "http://produk.digtive.id/CoupleCat/api/";
    public static final String img_url = "http://produk.digtive.id/CoupleCat/";
    */
    public static final String BASE_URL = "http://192.168.43.201/anabul/";
    public static final String img_url = "http://192.168.43.201/anabul/assets/img/upload/";
    public static final String THE_CAT_URL = "https://api.thecatapi.com/v1/";
    public static CoupleCatInterface getApiService(){
        return ApiClient.getApiClient(BASE_URL).create(CoupleCatInterface.class);
    }
    public static TheCatApiInterface getTheCatApi(){
        return ApiClient.getTheCatApiClient(THE_CAT_URL).create(TheCatApiInterface.class);
    }
}

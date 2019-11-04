package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.Information;
import com.azhar.couplecat.Model.ResponseInformation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TheCatApiInterface {
    @GET("breeds?X-Api-Key=983e61f8-8451-441c-bc89-2892e634c290")
    Call<ArrayList<Information>> getListTheCatApi();
}

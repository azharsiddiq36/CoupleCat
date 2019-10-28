package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.ResponsePengguna;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CoupleCatInterface {
    @FormUrlEncoded
    @POST("api/login/pengguna")
    Call<ResponsePengguna> loginRequest(@Field("pengguna_username") String username,
                                        @Field("pengguna_password") String password);

    @FormUrlEncoded
    @POST("api/register/pengguna")
    Call<ResponsePengguna> register(@Field("pengguna_nama") String nama,
                                    @Field("pengguna_username") String username,
                                    @Field("pengguna_email") String email,
                                    @Field("pengguna_password") String password);

    @FormUrlEncoded
    @POST("api/forget/pengguna")
    Call<ResponsePengguna> forget(@Field("pengguna_email") String email);
    @FormUrlEncoded
    @POST("api/validasi/pengguna")
    Call<ResponsePengguna> checkValidasi(@Field("pengguna_id") String id);
}

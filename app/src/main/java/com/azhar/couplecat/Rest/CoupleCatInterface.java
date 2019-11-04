package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.Information;
import com.azhar.couplecat.Model.ResponsePengguna;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
                                    @Field("pengguna_password") String password,
                                    @Field("pengguna_nomor") String nomor);

    @FormUrlEncoded
    @POST("api/forget/pengguna")
    Call<ResponsePengguna> forget(@Field("pengguna_email") String email);

    @FormUrlEncoded
    @POST("api/validasi/pengguna")
    Call<ResponsePengguna> checkValidasi(@Field("pengguna_id") String id);

    @FormUrlEncoded
    @POST("api/detailaccount/pengguna")
    Call<ResponsePengguna> detailAccount(@Field("pengguna_id") String id);

    @Multipart
    @POST("api/ktp/pengguna")
    Call<ResponsePengguna> uploadKtp(@Part MultipartBody.Part image,
                                     @Part("pengguna_id") RequestBody pengguna_id);

    @Multipart
    @POST("api/fotodiri/pengguna")
    Call<ResponsePengguna> uploadPersonalPhoto(@Part MultipartBody.Part image,
                                               @Part("pengguna_id") RequestBody pengguna_id);

}

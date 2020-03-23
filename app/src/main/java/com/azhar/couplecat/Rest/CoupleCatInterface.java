package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.Information;
import com.azhar.couplecat.Model.ResponseCouple;
import com.azhar.couplecat.Model.ResponseKontak;
import com.azhar.couplecat.Model.ResponseLastMessage;
import com.azhar.couplecat.Model.ResponseMessage;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.azhar.couplecat.Model.ResponsePasangan;
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

    @FormUrlEncoded
    @POST("api/update1/pengguna")
    Call<ResponsePengguna> updateProfile1(@Field("pengguna_id") String id,
                                          @Field("pengguna_nama") String nama,
                                          @Field("pengguna_nomor") String nomor,
                                          @Field("pengguna_email") String email);

    @Multipart
    @POST("api/update2/pengguna")
    Call<ResponsePengguna> updateProfile2(@Part MultipartBody.Part image,
                                          @Part("pengguna_id") RequestBody pengguna_id,
                                          @Part("pengguna_nomor") RequestBody pengguna_nomor,
                                          @Part("pengguna_email") RequestBody pengguna_email,
                                          @Part("pengguna_nama") RequestBody pengguna_nama);
    @FormUrlEncoded
    @POST("api/kucing/mycat")
    Call<ResponseMyCat> myListCat(@Field("kucing_pengguna_id") String id);
    @Multipart
    @POST("api/kucing/tambah")
    Call<ResponseMyCat> addMyCat(@Part MultipartBody.Part image,
                                          @Part("kucing_pengguna_id") RequestBody pengguna_id,
                                          @Part("kucing_nama") RequestBody kucing_nama,
                                          @Part("kucing_jenis") RequestBody kucing_jenis,
                                          @Part("kucing_jk") RequestBody kucing_jk);
    @FormUrlEncoded
    @POST("api/pasangan/tambah")
    Call<ResponsePasangan> addCouple(@Field("pasangan_kucing_id") String pasangan_kucing_id,
                                     @Field("pasangan_hari") String pasangan_hari,
                                     @Field("pasangan_pengguna_id")String pasangan_pengguna_id);
    @FormUrlEncoded
    @POST("api/updatelocation/pengguna")
    Call<ResponsePasangan> updateLocation(@Field("pengguna_id") String pengguna_id,
                                          @Field("pengguna_provinsi")String pengguna_provinsi,
                                          @Field("pengguna_kabupaten")String pengguna_kabupaten,
                                          @Field("pengguna_kecamatan")String pengguna_kecamatan,
                                          @Field("pengguna_desa")String pengguna_desa,
                                          @Field("pengguna_latitude")String pengguna_latitude,
                                          @Field("pengguna_longitude")String pengguna_longitude);
    @FormUrlEncoded
    @POST("api/pasangan/list")
    Call<ResponseCouple> getListCouple(@Field("pasangan_kucing_jenis") String jenis,
                                       @Field("pasangan_provinsi") String provinsi,
                                       @Field("pasangan_kabupaten")String kabupaten,
                                       @Field("pasangan_kecamatan")String kecamatan,
                                       @Field("pasangan_desa")String desa);
    @FormUrlEncoded
    @POST("api/pesan/getconversation")
    Call<ResponseMessage> getConversation(@Field("chatting_kontak") String chatting_kontak);
    @FormUrlEncoded
    @POST("api/pesan/lastconversation")
    Call<ResponseLastMessage> getLastConversation(@Field("kontak_id") String kontak_id);
    @FormUrlEncoded
    @POST("api/kontak/getkontak")
    Call<ResponseKontak> getKontak(@Field("pengguna_id") String pengguna_id);

}

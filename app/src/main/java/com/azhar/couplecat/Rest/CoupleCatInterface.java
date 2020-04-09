package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.Information;
import com.azhar.couplecat.Model.ResponseCouple;
import com.azhar.couplecat.Model.ResponseKomentar;
import com.azhar.couplecat.Model.ResponseKontak;
import com.azhar.couplecat.Model.ResponseLastMessage;
import com.azhar.couplecat.Model.ResponseMessage;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.azhar.couplecat.Model.ResponsePasangan;
import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.Model.ResponsePostingan;
import com.azhar.couplecat.Model.ResponseStore;
import com.azhar.couplecat.Model.ResponseToko;

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
    @FormUrlEncoded
    @POST("api/toko/get")
    Call<ResponseStore> getToko(@Field("toko_pengguna_id")String pengguna_id);
    @Multipart
    @POST("api/toko/tambah")
    Call<ResponseToko> addToko(@Part MultipartBody.Part image,
                               @Part("toko_pengguna_id")RequestBody pengguna_id,
                               @Part("toko_nama")RequestBody toko_nama,
                               @Part("toko_latitude")RequestBody latitude,
                               @Part("toko_longitude")RequestBody longitude,
                               @Part("toko_provinsi")RequestBody provinsi,
                               @Part("toko_kabupaten")RequestBody kabupaten,
                               @Part("toko_kecamatan")RequestBody kecamatan,
                               @Part("toko_desa")RequestBody desa,
                               @Part("toko_alamat")RequestBody alamat,
                               @Part("toko_deskripsi")RequestBody deskripsi,
                               @Part("toko_nomor")RequestBody nomor);
    @GET("api/toko/getlisttoko")
    Call<ResponseToko>getListToko();
    @Multipart
    @POST("api/toko/update")
    Call<ResponseToko> updateToko(@Part MultipartBody.Part image,
                               @Part("toko_id")RequestBody pengguna_id,
                               @Part("toko_nama")RequestBody toko_nama,
                               @Part("toko_latitude")RequestBody latitude,
                               @Part("toko_longitude")RequestBody longitude,
                               @Part("toko_provinsi")RequestBody provinsi,
                               @Part("toko_kabupaten")RequestBody kabupaten,
                               @Part("toko_kecamatan")RequestBody kecamatan,
                               @Part("toko_desa")RequestBody desa,
                               @Part("toko_alamat")RequestBody alamat,
                               @Part("toko_deskripsi")RequestBody deskripsi,
                               @Part("toko_nomor")RequestBody nomor);
    @FormUrlEncoded
    @POST("api/toko/update1")
    Call<ResponseToko> updateToko1(@Field("toko_id")String pengguna_id,
                                   @Field("toko_nama")String toko_nama,
                                   @Field("toko_latitude")String latitude,
                                   @Field("toko_longitude")String longitude,
                                   @Field("toko_provinsi")String provinsi,
                                   @Field("toko_kabupaten")String kabupaten,
                                   @Field("toko_kecamatan")String kecamatan,
                                   @Field("toko_desa")String desa,
                                   @Field("toko_alamat")String alamat,
                                   @Field("toko_deskripsi")String deskripsi,
                                   @Field("toko_nomor")String nomor);
    @GET("api/postingan/get")
    Call<ResponsePostingan> getPostingan();
    @FormUrlEncoded
    @POST("api/postingan/tambah")
    Call<ResponsePostingan> addPostingan(@Field("postingan_id_pengguna")String id_pengguna,
                                         @Field("postingan_deskripsi")String deskripsi);
    @FormUrlEncoded
    @POST("api/komentar/tambah")
    Call<ResponseKomentar> addKomentar(@Field("komentar_pengguna_id")String pengguna_id,
                                       @Field("komentar_postingan_id")String postingan_id,
                                       @Field("komentar_deskripsi")String deskripsi);
    @FormUrlEncoded
    @POST("api/komentar/get")
    Call<ResponseKomentar> getKomentar(@Field("postingan_id")String postingan_id);
    @FormUrlEncoded
    @POST("api/report/tambah")
    Call<ResponsePostingan> addReport(@Field("report_pengguna_id")String pengguna_id,
                                      @Field("report_alasan")String deskripsi,
                                      @Field("report_postingan_id") String postingan_id);
}

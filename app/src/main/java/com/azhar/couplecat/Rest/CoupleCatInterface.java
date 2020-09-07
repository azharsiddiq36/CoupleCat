package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.ResponseCheckMessage;
import com.azhar.couplecat.Model.ResponseContact;
import com.azhar.couplecat.Model.ResponseContest;
import com.azhar.couplecat.Model.ResponseContestDetails;
import com.azhar.couplecat.Model.ResponseCouple;
import com.azhar.couplecat.Model.ResponseJadwal;
import com.azhar.couplecat.Model.ResponseJenisKontes;
import com.azhar.couplecat.Model.ResponseKomentar;
import com.azhar.couplecat.Model.ResponseKontak;
import com.azhar.couplecat.Model.ResponseLastMessage;
import com.azhar.couplecat.Model.ResponseMessage;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.azhar.couplecat.Model.ResponseOneCat;
import com.azhar.couplecat.Model.ResponsePasangan;
import com.azhar.couplecat.Model.ResponsePembayaran;
import com.azhar.couplecat.Model.ResponsePemesanan;
import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.Model.ResponsePostingan;
import com.azhar.couplecat.Model.ResponseRowPemesanan;
import com.azhar.couplecat.Model.ResponseSaldo;
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
    Call<ResponseMessage> getConversation(@Field("chatting_kontak") String chatting_kontak,
                                          @Field("pengguna_id")String pengguna_id);
    @FormUrlEncoded
    @POST("api/pesan/lastconversation")
    Call<ResponseLastMessage> getLastConversation(@Field("kontak_id") String kontak_id);
    @FormUrlEncoded
    @POST("api/pesan/addconversation")
    Call<ResponseMessage> addMessage(@Field("chatting_kontak") String kontak_id,
                                     @Field("pengguna1") String pengirim,
                                     @Field("pengguna2") String penerima,
                                     @Field("pesan")String pesan);
    @FormUrlEncoded
    @POST("api/pesan/check")
    Call<ResponseCheckMessage> checkMessageChange(@Field("chatting_kontak")String kontak_id);
    @FormUrlEncoded
    @POST("api/kontak/getkontak")
    Call<ResponseKontak> getKontak(@Field("pengguna_id") String pengguna_id);
    @FormUrlEncoded
    @POST("api/kontak/addkontak")
    Call<ResponseContact> addKontak(@Field("pengguna1")String pengguna1,
                                    @Field("pengguna2")String pengguna2);
    @FormUrlEncoded
    @POST("api/kontak/check")
    Call<ResponseKontak> checkKontak(@Field("pengguna1")String pengguna1,
                                   @Field("pengguna2")String pengguna2);

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
    @FormUrlEncoded
    @POST("api/jadwal/tambah")
    Call<ResponseJadwal> addJadwal(@Field("pengguna2")String penerima,
                                   @Field("pengguna1")String pengaju,
                                   @Field("kucing1")String id_kucing,
                                   @Field("kucing2")String kucing2,
                                   @Field("jadwal")String jadwal,
                                   @Field("lokasi")String lokasi);
    @FormUrlEncoded
    @POST("api/jadwal/get")
    Call<ResponseJadwal> getJadwal(@Field("pengguna_id")String id,
                                   @Field("status")String status);
    @FormUrlEncoded
    @POST("api/kucing/getone")
    Call<ResponseOneCat> getCatDetail(@Field("kucing_id")String kucing);
    @FormUrlEncoded
    @POST("api/jadwal/acc")
    Call<ResponseJadwal> accJadwal(@Field("jadwal_id")String id);
    @FormUrlEncoded
    @POST("api/jadwal/reject")
    Call<ResponseJadwal> rejectJadwal(@Field("jadwal_id")String id);

    @GET("api/kontes/getjenis")
    Call<ResponseJenisKontes> getJenisKontes();
    //Kontes
    @GET("api/kontes/get")
    Call<ResponseContest> getListContest();
    @FormUrlEncoded
    @POST("api/kontes/mylist")
    Call<ResponseContest> getMyContest(@Field("pengguna_id")String id,@Field("status") String status);
    @FormUrlEncoded
    @POST("api/kontes/detail")
    Call<ResponseContestDetails> getDetailContest(@Field("id")String id);
    @Multipart
    @POST("api/kontes/add")
    Call<ResponseContest> addContest(@Part MultipartBody.Part image,
                                     @Part("id")RequestBody pengguna_id,
                                     @Part("kontes_nama")RequestBody nama,
                                     @Part("provinsi")RequestBody provinsi,
                                     @Part("kabupaten")RequestBody kabupaten,
                                     @Part("kecamatan")RequestBody kecamatan,
                                     @Part("desa")RequestBody desa,
                                     @Part("kuota")RequestBody kuota,
                                     @Part("durasi")RequestBody durasi,
                                     @Part("biaya")RequestBody biaya,
                                     @Part("lokasi")RequestBody alamat,
                                     @Part("details")RequestBody details,
                                     @Part("description")RequestBody deskripsi,
                                     @Part("tanggal")RequestBody tanggal,
                                     @Part("kontes_jenis")RequestBody jenis,
                                     @Part("nomor")RequestBody nomor);
    @FormUrlEncoded
    @POST("api/pemesanan/add")
    Call<ResponsePemesanan> addPemesanan(@Field("id") String id,
                                         @Field("jumlah")String jumlah,
                                         @Field("total")String total,
                                         @Field("kontes")String kontes);
    @FormUrlEncoded
    @POST("api/pemesanan/getorder")
    Call<ResponsePemesanan> getPemesanan(@Field("id") String id);
    @FormUrlEncoded
    @POST("api/pemesanan/getone")
    Call<ResponseRowPemesanan> getOneOfPemesanan(@Field("id")String id);
    @FormUrlEncoded
    @POST("api/pembayaran/add")
    Call<ResponsePembayaran> addPembayaran(@Field("pemesanan_id")String pemesanan_id,
                                           @Field("pengguna_id")String pengguna_id,
                                           @Field("pembayaran_jumlah")String pembayaran_jumlah,
                                           @Field("pembayaran_jenis")String pembayaran_jenis,
                                           @Field("pembayaran_kontes")String pembayaran_kontes);
    @Multipart
    @POST("api/pembayaran/add")
    Call<ResponsePembayaran> addUploadPembayaran(@Part MultipartBody.Part image,
                                     @Part("pemesanan_id")RequestBody pemesanan,
                                     @Part("pengguna_id")RequestBody pengguna,
                                     @Part("pembayaran_jumlah")RequestBody jumlah,
                                     @Part("pembayaran_jenis")RequestBody jenis,
                                     @Part("pembayaran_kontes")RequestBody kontes);
    @FormUrlEncoded
    @POST("api/pembayaran/getmypayment")
    Call<ResponsePembayaran> getMyPayment(@Field("id") String id);
    @Multipart
    @POST("api/saldo/add")
    Call<ResponseSaldo> addSaldo(@Part MultipartBody.Part image,
                                 @Part("id")RequestBody id,
                                 @Part("jumlah")RequestBody jumlah);
    @FormUrlEncoded
    @POST("api/saldo/tariksaldo")
    Call<ResponseSaldo> tarikSaldo(@Field("id")String id,
                                   @Field("norek")String norek,
                                   @Field("bank") String bank,
                                   @Field("jumlah") String jumlah);
    @FormUrlEncoded
    @POST("api/saldo/transfer")
    Call<ResponseSaldo> transfer(@Field("id") String id,
                                 @Field("jumlah") String jumlah,
                                 @Field("kontes")String kontes);
    @FormUrlEncoded
    @POST("api/saldo/totalsaldokontes")
    Call<ResponseSaldo> saldokontes(@Field("id") String id,
                                    @Field("kontes") String kontes);
    @FormUrlEncoded
    @POST("api/saldo/getmycontest")
    Call<ResponseContest> getlistsaldokontes(@Field("id")String id);

}

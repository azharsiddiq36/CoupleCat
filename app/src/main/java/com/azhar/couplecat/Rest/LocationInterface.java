package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.ApiLocation;
import com.azhar.couplecat.Model.Data;
import com.azhar.couplecat.Model.ResponseLocation;
import com.azhar.couplecat.Model.UniqueCode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//https://docs.rajaapi.com/dokumentasi/wilayah
public interface LocationInterface {

    @GET("poe")
    Call<UniqueCode> getUniqueCode();

    @GET("{code}/m/wilayah/provinsi")
    Call<ResponseLocation> getProvinceList(@Path("code") String code);

    @GET("{code}/m/wilayah/kabupaten")
    Call<ResponseLocation> getKabupatenList(@Path("code") String code, @Query("idpropinsi") long idProv);

    @GET("{code}/m/wilayah/kecamatan")
    Call<ResponseLocation> getKecamatanList(@Path("code") String code, @Query("idkabupaten") long idKab);

    @GET("{code}/m/wilayah/kelurahan")
    Call<ResponseLocation> getKelurahanList(@Path("code") String code, @Query("idkecamatan") long idKec);
}

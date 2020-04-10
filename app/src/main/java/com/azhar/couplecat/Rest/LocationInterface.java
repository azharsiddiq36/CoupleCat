package com.azhar.couplecat.Rest;

import com.azhar.couplecat.Model.ResponseLocation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

//https://docs.rajaapi.com/dokumentasi/wilayah
public interface LocationInterface {
    @GET("Gf9fIRJNY8Vc8gcORVBysIusUTJnHRxOGTeYYGOrbgAYy9XRXD/m/wilayah/provinsi")
    Call<ResponseLocation> getProvinsi();
    @GET("Gf9fIRJNY8Vc8gcORVBysIusUTJnHRxOGTeYYGOrbgAYy9XRXD/m/wilayah/kabupaten?idpropinsi={kabupaten}")
    Call<ResponseLocation> getKabupaten(@Path("kabupaten") String id_kabupaten);
    @GET("Gf9fIRJNY8Vc8gcORVBysIusUTJnHRxOGTeYYGOrbgAYy9XRXD/m/wilayah/kecamatan?idkabupaten={kecamatan}")
    Call<ResponseLocation> getKecamatan(@Path("kecamatan") String id_kecamatan);
    @GET("Gf9fIRJNY8Vc8gcORVBysIusUTJnHRxOGTeYYGOrbgAYy9XRXD/m/wilayah/kelurahan?idkecamatan={desa}")
    Call<ResponseLocation> getDesa(@Path("desa") String id_desa);
}

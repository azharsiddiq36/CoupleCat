package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class JenisKontes {
    @SerializedName("jenis_kontes_id")
    private String jenisKontesId;
    @SerializedName("jenis_kontes_nama")
    private String jenisKontesNama;

    public String getJenisKontesId() {
        return jenisKontesId;
    }

    public void setJenisKontesId(String jenisKontesId) {
        this.jenisKontesId = jenisKontesId;
    }

    public String getJenisKontesNama() {
        return jenisKontesNama;
    }

    public void setJenisKontesNama(String jenisKontesNama) {
        this.jenisKontesNama = jenisKontesNama;
    }
}

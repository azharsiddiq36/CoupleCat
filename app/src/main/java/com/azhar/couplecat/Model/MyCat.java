package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class MyCat {
    @SerializedName("kucing_id")
    private String kucingId;
    @SerializedName("kucing_nama")
    private String kucingNama;
    @SerializedName("kucing_jenis")
    private String kucingJenis;
    @SerializedName("kucing_pengguna_id")
    private String kucingPenggunaId;
    @SerializedName("kucing_jk")
    private String kucingJk;
    @SerializedName("kucing_foto")
    private String kucingFoto;

    public String getKucingId() {
        return kucingId;
    }

    public void setKucingId(String kucingId) {
        this.kucingId = kucingId;
    }

    public String getKucingNama() {
        return kucingNama;
    }

    public void setKucingNama(String kucingNama) {
        this.kucingNama = kucingNama;
    }

    public String getKucingJenis() {
        return kucingJenis;
    }

    public void setKucingJenis(String kucingJenis) {
        this.kucingJenis = kucingJenis;
    }

    public String getKucingPenggunaId() {
        return kucingPenggunaId;
    }

    public void setKucingPenggunaId(String kucingPenggunaId) {
        this.kucingPenggunaId = kucingPenggunaId;
    }

    public String getKucingJk() {
        return kucingJk;
    }

    public void setKucingJk(String kucingJk) {
        this.kucingJk = kucingJk;
    }

    public String getKucingFoto() {
        return kucingFoto;
    }

    public void setKucingFoto(String kucingFoto) {
        this.kucingFoto = kucingFoto;
    }
}

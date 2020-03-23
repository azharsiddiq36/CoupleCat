package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class Kontak {
    @SerializedName("kontak_id")
    private String kontakId;
    @SerializedName("kontak_pengguna_id")
    private String kontakPenggunaId;
    @SerializedName("kontak_pengguna_id2")
    private String kontakPenggunaId2;
    @SerializedName("kontak_tanggal")
    private String kontakTanggal;

    public String getKontakId() {
        return kontakId;
    }

    public void setKontakId(String kontakId) {
        this.kontakId = kontakId;
    }

    public String getKontakPenggunaId() {
        return kontakPenggunaId;
    }

    public void setKontakPenggunaId(String kontakPenggunaId) {
        this.kontakPenggunaId = kontakPenggunaId;
    }

    public String getKontakPenggunaId2() {
        return kontakPenggunaId2;
    }

    public void setKontakPenggunaId2(String kontakPenggunaId2) {
        this.kontakPenggunaId2 = kontakPenggunaId2;
    }

    public String getKontakTanggal() {
        return kontakTanggal;
    }

    public void setKontakTanggal(String kontakTanggal) {
        this.kontakTanggal = kontakTanggal;
    }
}

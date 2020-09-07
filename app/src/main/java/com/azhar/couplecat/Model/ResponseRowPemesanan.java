package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseRowPemesanan {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("foto")
    private String foto;
    @SerializedName("id")
    private String id;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("judul")
    private String judul;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("kontes")
    private String kontes;

    public String getKontes() {
        return kontes;
    }

    public void setKontes(String kontes) {
        this.kontes = kontes;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

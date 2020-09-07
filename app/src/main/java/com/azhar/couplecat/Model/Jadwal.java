package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class Jadwal {
    @SerializedName("penjadwalan_id")
    private String penjadwalanId;
    @SerializedName("penjadwalan_deskripsi")
    private String penjadwalanDeskripsi;
    @SerializedName("penjadwalan_tanggal")
    private String penjadwalanTanggal;
    @SerializedName("penjadwalan_id_pengaju")
    private String penjadwalanIdPengaju;
    @SerializedName("penjadwalan_status")
    private String penjadwalanStatus;
    @SerializedName("penjadwalan_lokasi")
    private String penjadwalanLokasi;
    @SerializedName("penjadwalan_id_penerima")
    private String penjadwalanIdPenerima;
    @SerializedName("penjadwalan_id_kucing_pengaju")
    private String penjadwalanIdKucingPengaju;
    @SerializedName("penjadwalan_id_kucing_penerima")
    private String penjadwalanIdKucingPenerima;
    @SerializedName("penjadwalan_tgl_pengajuan")
    private String penjadwalanTglPengajuan;
    @SerializedName("penjadwalan_tgl_terima")
    private String penjadwalanTglTerima;

    public String getPenjadwalanTglTerima() {
        return penjadwalanTglTerima;
    }

    public void setPenjadwalanTglTerima(String penjadwalanTglTerima) {
        this.penjadwalanTglTerima = penjadwalanTglTerima;
    }

    public String getPenjadwalanId() {
        return penjadwalanId;
    }

    public void setPenjadwalanId(String penjadwalanId) {
        this.penjadwalanId = penjadwalanId;
    }

    public String getPenjadwalanDeskripsi() {
        return penjadwalanDeskripsi;
    }

    public void setPenjadwalanDeskripsi(String penjadwalanDeskripsi) {
        this.penjadwalanDeskripsi = penjadwalanDeskripsi;
    }

    public String getPenjadwalanTanggal() {
        return penjadwalanTanggal;
    }

    public void setPenjadwalanTanggal(String penjadwalanTanggal) {
        this.penjadwalanTanggal = penjadwalanTanggal;
    }

    public String getPenjadwalanIdPengaju() {
        return penjadwalanIdPengaju;
    }

    public void setPenjadwalanIdPengaju(String penjadwalanIdPengaju) {
        this.penjadwalanIdPengaju = penjadwalanIdPengaju;
    }

    public String getPenjadwalanStatus() {
        return penjadwalanStatus;
    }

    public void setPenjadwalanStatus(String penjadwalanStatus) {
        this.penjadwalanStatus = penjadwalanStatus;
    }

    public String getPenjadwalanLokasi() {
        return penjadwalanLokasi;
    }

    public void setPenjadwalanLokasi(String penjadwalanLokasi) {
        this.penjadwalanLokasi = penjadwalanLokasi;
    }

    public String getPenjadwalanIdPenerima() {
        return penjadwalanIdPenerima;
    }

    public void setPenjadwalanIdPenerima(String penjadwalanIdPenerima) {
        this.penjadwalanIdPenerima = penjadwalanIdPenerima;
    }

    public String getPenjadwalanIdKucingPengaju() {
        return penjadwalanIdKucingPengaju;
    }

    public void setPenjadwalanIdKucingPengaju(String penjadwalanIdKucingPengaju) {
        this.penjadwalanIdKucingPengaju = penjadwalanIdKucingPengaju;
    }

    public String getPenjadwalanIdKucingPenerima() {
        return penjadwalanIdKucingPenerima;
    }

    public void setPenjadwalanIdKucingPenerima(String penjadwalanIdKucingPenerima) {
        this.penjadwalanIdKucingPenerima = penjadwalanIdKucingPenerima;
    }

    public String getPenjadwalanTglPengajuan() {
        return penjadwalanTglPengajuan;
    }

    public void setPenjadwalanTglPengajuan(String penjadwalanTglPengajuan) {
        this.penjadwalanTglPengajuan = penjadwalanTglPengajuan;
    }
}

package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pemesanan extends ArrayList {
    @SerializedName("pemesanan_id")
    private String pemesananId;
    @SerializedName("pemesanan_pengguna_id")
    private String pemesananPenggunaId;
    @SerializedName("pemesanan_jumlah")
    private String pemesananJumlah;
    @SerializedName("pemesanan_total")
    private String pemesananTotal;
    @SerializedName("pemesanan_tanggal")
    private String pemesananTanggal;
    @SerializedName("pemesanan_status")
    private String pemesananStatus;
    @SerializedName("pemesanan_kontes_id")
    private String pemesananKontesId;
    @SerializedName("kontes_id")
    private String kontesId;
    @SerializedName("kontes_nama")
    private String kontesNama;
    @SerializedName("kontes_lokasi")
    private String kontesLokasi;
    @SerializedName("kontes_provinsi")
    private String kontesProvinsi;
    @SerializedName("kontes_kabupaten")
    private String kontesKabupaten;
    @SerializedName("kontes_kecamatan")
    private String kontesKecamatan;
    @SerializedName("kontes_desa")
    private String kontesDesa;
    @SerializedName("kontes_description")
    private String kontesDescription;
    @SerializedName("kontes_jenis")
    private String kontesJenis;
    @SerializedName("kontes_details")
    private String kontesDetails;
    @SerializedName("kontes_kuota")
    private String kontesKuota;
    @SerializedName("kontes_tanggal_mulai")
    private String kontesTanggalMulai;
    @SerializedName("kontes_tanggal_selesai")
    private String kontesTanggalSelesai;
    @SerializedName("kontes_status")
    private String kontesStatus;
    @SerializedName("kontes_biaya")
    private String kontesBiaya;
    @SerializedName("kontes_jumlah_pemesan")
    private String kontesJumlahPemesan;
    @SerializedName("kontes_nomor")
    private String kontesNomor;
    @SerializedName("kontes_foto")
    private String kontesFoto;
    @SerializedName("kontes_pengaju_id")
    private String kontesPengajuId;
    @SerializedName("kontes_tanggal_pengajuan")
    private String kontesTanggalPengajuan;

    public String getPemesananId() {
        return pemesananId;
    }

    public void setPemesananId(String pemesananId) {
        this.pemesananId = pemesananId;
    }

    public String getPemesananPenggunaId() {
        return pemesananPenggunaId;
    }

    public void setPemesananPenggunaId(String pemesananPenggunaId) {
        this.pemesananPenggunaId = pemesananPenggunaId;
    }

    public String getPemesananJumlah() {
        return pemesananJumlah;
    }

    public void setPemesananJumlah(String pemesananJumlah) {
        this.pemesananJumlah = pemesananJumlah;
    }

    public String getPemesananTotal() {
        return pemesananTotal;
    }

    public void setPemesananTotal(String pemesananTotal) {
        this.pemesananTotal = pemesananTotal;
    }

    public String getPemesananTanggal() {
        return pemesananTanggal;
    }

    public void setPemesananTanggal(String pemesananTanggal) {
        this.pemesananTanggal = pemesananTanggal;
    }

    public String getPemesananStatus() {
        return pemesananStatus;
    }

    public void setPemesananStatus(String pemesananStatus) {
        this.pemesananStatus = pemesananStatus;
    }

    public String getPemesananKontesId() {
        return pemesananKontesId;
    }

    public void setPemesananKontesId(String pemesananKontesId) {
        this.pemesananKontesId = pemesananKontesId;
    }

    public String getKontesId() {
        return kontesId;
    }

    public void setKontesId(String kontesId) {
        this.kontesId = kontesId;
    }

    public String getKontesNama() {
        return kontesNama;
    }

    public void setKontesNama(String kontesNama) {
        this.kontesNama = kontesNama;
    }

    public String getKontesLokasi() {
        return kontesLokasi;
    }

    public void setKontesLokasi(String kontesLokasi) {
        this.kontesLokasi = kontesLokasi;
    }

    public String getKontesProvinsi() {
        return kontesProvinsi;
    }

    public void setKontesProvinsi(String kontesProvinsi) {
        this.kontesProvinsi = kontesProvinsi;
    }

    public String getKontesKabupaten() {
        return kontesKabupaten;
    }

    public void setKontesKabupaten(String kontesKabupaten) {
        this.kontesKabupaten = kontesKabupaten;
    }

    public String getKontesKecamatan() {
        return kontesKecamatan;
    }

    public void setKontesKecamatan(String kontesKecamatan) {
        this.kontesKecamatan = kontesKecamatan;
    }

    public String getKontesDesa() {
        return kontesDesa;
    }

    public void setKontesDesa(String kontesDesa) {
        this.kontesDesa = kontesDesa;
    }

    public String getKontesDescription() {
        return kontesDescription;
    }

    public void setKontesDescription(String kontesDescription) {
        this.kontesDescription = kontesDescription;
    }

    public String getKontesJenis() {
        return kontesJenis;
    }

    public void setKontesJenis(String kontesJenis) {
        this.kontesJenis = kontesJenis;
    }

    public String getKontesDetails() {
        return kontesDetails;
    }

    public void setKontesDetails(String kontesDetails) {
        this.kontesDetails = kontesDetails;
    }

    public String getKontesKuota() {
        return kontesKuota;
    }

    public void setKontesKuota(String kontesKuota) {
        this.kontesKuota = kontesKuota;
    }

    public String getKontesTanggalMulai() {
        return kontesTanggalMulai;
    }

    public void setKontesTanggalMulai(String kontesTanggalMulai) {
        this.kontesTanggalMulai = kontesTanggalMulai;
    }

    public String getKontesTanggalSelesai() {
        return kontesTanggalSelesai;
    }

    public void setKontesTanggalSelesai(String kontesTanggalSelesai) {
        this.kontesTanggalSelesai = kontesTanggalSelesai;
    }

    public String getKontesStatus() {
        return kontesStatus;
    }

    public void setKontesStatus(String kontesStatus) {
        this.kontesStatus = kontesStatus;
    }

    public String getKontesBiaya() {
        return kontesBiaya;
    }

    public void setKontesBiaya(String kontesBiaya) {
        this.kontesBiaya = kontesBiaya;
    }

    public String getKontesJumlahPemesan() {
        return kontesJumlahPemesan;
    }

    public void setKontesJumlahPemesan(String kontesJumlahPemesan) {
        this.kontesJumlahPemesan = kontesJumlahPemesan;
    }

    public String getKontesNomor() {
        return kontesNomor;
    }

    public void setKontesNomor(String kontesNomor) {
        this.kontesNomor = kontesNomor;
    }

    public String getKontesFoto() {
        return kontesFoto;
    }

    public void setKontesFoto(String kontesFoto) {
        this.kontesFoto = kontesFoto;
    }

    public String getKontesPengajuId() {
        return kontesPengajuId;
    }

    public void setKontesPengajuId(String kontesPengajuId) {
        this.kontesPengajuId = kontesPengajuId;
    }

    public String getKontesTanggalPengajuan() {
        return kontesTanggalPengajuan;
    }

    public void setKontesTanggalPengajuan(String kontesTanggalPengajuan) {
        this.kontesTanggalPengajuan = kontesTanggalPengajuan;
    }
}

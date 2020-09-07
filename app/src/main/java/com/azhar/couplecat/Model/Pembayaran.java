package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class Pembayaran {
    @SerializedName("pembayaran_id")
    private String pembayaranId;
    @SerializedName("pembayaran_pemesanan_id")
    private String pembayaranPemesananId;
    @SerializedName("pembayaran_pengguna_id")
    private String pembayaranPenggunaId;
    @SerializedName("pembayaran_jumlah")
    private String pembayaranJumlah;
    @SerializedName("pembayaran_bukti")
    private Object pembayaranBukti;
    @SerializedName("pembayaran_tanggal")
    private String pembayaranTanggal;
    @SerializedName("pembayaran_status")
    private String pembayaranStatus;
    @SerializedName("pembayaran_kontes_id")
    private String pembayaranKontesId;
    @SerializedName("pembayaran_jenis")
    private String pembayaranJenis;
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
    public String getPembayaranId() {
        return pembayaranId;
    }

    public void setPembayaranId(String pembayaranId) {
        this.pembayaranId = pembayaranId;
    }

    public String getPembayaranPemesananId() {
        return pembayaranPemesananId;
    }

    public void setPembayaranPemesananId(String pembayaranPemesananId) {
        this.pembayaranPemesananId = pembayaranPemesananId;
    }

    public String getPembayaranPenggunaId() {
        return pembayaranPenggunaId;
    }

    public void setPembayaranPenggunaId(String pembayaranPenggunaId) {
        this.pembayaranPenggunaId = pembayaranPenggunaId;
    }

    public String getPembayaranJumlah() {
        return pembayaranJumlah;
    }

    public void setPembayaranJumlah(String pembayaranJumlah) {
        this.pembayaranJumlah = pembayaranJumlah;
    }

    public Object getPembayaranBukti() {
        return pembayaranBukti;
    }

    public void setPembayaranBukti(Object pembayaranBukti) {
        this.pembayaranBukti = pembayaranBukti;
    }

    public String getPembayaranTanggal() {
        return pembayaranTanggal;
    }

    public void setPembayaranTanggal(String pembayaranTanggal) {
        this.pembayaranTanggal = pembayaranTanggal;
    }

    public String getPembayaranStatus() {
        return pembayaranStatus;
    }

    public void setPembayaranStatus(String pembayaranStatus) {
        this.pembayaranStatus = pembayaranStatus;
    }

    public String getPembayaranKontesId() {
        return pembayaranKontesId;
    }

    public void setPembayaranKontesId(String pembayaranKontesId) {
        this.pembayaranKontesId = pembayaranKontesId;
    }

    public String getPembayaranJenis() {
        return pembayaranJenis;
    }

    public void setPembayaranJenis(String pembayaranJenis) {
        this.pembayaranJenis = pembayaranJenis;
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

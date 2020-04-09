package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class Komentar {
    @SerializedName("komentar_id")
    private String komentarId;
    @SerializedName("komentar_pengguna_id")
    private String komentarPenggunaId;
    @SerializedName("komentar_deskripsi")
    private String komentarDeskripsi;
    @SerializedName("komentar_tanggal")
    private String komentarTanggal;
    @SerializedName("komentar_postingan_id")
    private String komentarPostinganId;
    @SerializedName("pengguna_id")
    private String penggunaId;
    @SerializedName("pengguna_nama")
    private String penggunaNama;
    @SerializedName("pengguna_username")
    private String penggunaUsername;
    @SerializedName("pengguna_foto")
    private String penggunaFoto;
    @SerializedName("pengguna_password")
    private String penggunaPassword;
    @SerializedName("pengguna_email")
    private String penggunaEmail;
    @SerializedName("pengguna_hak_akses")
    private String penggunaHakAkses;
    @SerializedName("pengguna_jenis_kelamin")
    private String penggunaJenisKelamin;
    @SerializedName("pengguna_alamat")
    private String penggunaAlamat;
    @SerializedName("pengguna_status")
    private String penggunaStatus;
    @SerializedName("pengguna_provinsi")
    private String penggunaProvinsi;
    @SerializedName("pengguna_kabupaten")
    private String penggunaKabupaten;
    @SerializedName("pengguna_kecamatan")
    private String penggunaKecamatan;
    @SerializedName("pengguna_desa")
    private String penggunaDesa;
    @SerializedName("pengguna_nomor")
    private String penggunaNomor;
    @SerializedName("pengguna_latitude")
    private String penggunaLatitude;
    @SerializedName("pengguna_longitude")
    private String penggunaLongitude;
    @SerializedName("pengguna_saldo")
    private String penggunaSaldo;
    @SerializedName("pengguna_tgl_buat")
    private String penggunaTglBuat;

    public String getKomentarId() {
        return komentarId;
    }

    public void setKomentarId(String komentarId) {
        this.komentarId = komentarId;
    }

    public String getKomentarPenggunaId() {
        return komentarPenggunaId;
    }

    public void setKomentarPenggunaId(String komentarPenggunaId) {
        this.komentarPenggunaId = komentarPenggunaId;
    }

    public String getKomentarDeskripsi() {
        return komentarDeskripsi;
    }

    public void setKomentarDeskripsi(String komentarDeskripsi) {
        this.komentarDeskripsi = komentarDeskripsi;
    }

    public String getKomentarTanggal() {
        return komentarTanggal;
    }

    public void setKomentarTanggal(String komentarTanggal) {
        this.komentarTanggal = komentarTanggal;
    }

    public String getKomentarPostinganId() {
        return komentarPostinganId;
    }

    public void setKomentarPostinganId(String komentarPostinganId) {
        this.komentarPostinganId = komentarPostinganId;
    }

    public String getPenggunaId() {
        return penggunaId;
    }

    public void setPenggunaId(String penggunaId) {
        this.penggunaId = penggunaId;
    }

    public String getPenggunaNama() {
        return penggunaNama;
    }

    public void setPenggunaNama(String penggunaNama) {
        this.penggunaNama = penggunaNama;
    }

    public String getPenggunaUsername() {
        return penggunaUsername;
    }

    public void setPenggunaUsername(String penggunaUsername) {
        this.penggunaUsername = penggunaUsername;
    }

    public String getPenggunaFoto() {
        return penggunaFoto;
    }

    public void setPenggunaFoto(String penggunaFoto) {
        this.penggunaFoto = penggunaFoto;
    }

    public String getPenggunaPassword() {
        return penggunaPassword;
    }

    public void setPenggunaPassword(String penggunaPassword) {
        this.penggunaPassword = penggunaPassword;
    }

    public String getPenggunaEmail() {
        return penggunaEmail;
    }

    public void setPenggunaEmail(String penggunaEmail) {
        this.penggunaEmail = penggunaEmail;
    }

    public String getPenggunaHakAkses() {
        return penggunaHakAkses;
    }

    public void setPenggunaHakAkses(String penggunaHakAkses) {
        this.penggunaHakAkses = penggunaHakAkses;
    }

    public String getPenggunaJenisKelamin() {
        return penggunaJenisKelamin;
    }

    public void setPenggunaJenisKelamin(String penggunaJenisKelamin) {
        this.penggunaJenisKelamin = penggunaJenisKelamin;
    }

    public String getPenggunaAlamat() {
        return penggunaAlamat;
    }

    public void setPenggunaAlamat(String penggunaAlamat) {
        this.penggunaAlamat = penggunaAlamat;
    }

    public String getPenggunaStatus() {
        return penggunaStatus;
    }

    public void setPenggunaStatus(String penggunaStatus) {
        this.penggunaStatus = penggunaStatus;
    }

    public String getPenggunaProvinsi() {
        return penggunaProvinsi;
    }

    public void setPenggunaProvinsi(String penggunaProvinsi) {
        this.penggunaProvinsi = penggunaProvinsi;
    }

    public String getPenggunaKabupaten() {
        return penggunaKabupaten;
    }

    public void setPenggunaKabupaten(String penggunaKabupaten) {
        this.penggunaKabupaten = penggunaKabupaten;
    }

    public String getPenggunaKecamatan() {
        return penggunaKecamatan;
    }

    public void setPenggunaKecamatan(String penggunaKecamatan) {
        this.penggunaKecamatan = penggunaKecamatan;
    }

    public String getPenggunaDesa() {
        return penggunaDesa;
    }

    public void setPenggunaDesa(String penggunaDesa) {
        this.penggunaDesa = penggunaDesa;
    }

    public String getPenggunaNomor() {
        return penggunaNomor;
    }

    public void setPenggunaNomor(String penggunaNomor) {
        this.penggunaNomor = penggunaNomor;
    }

    public String getPenggunaLatitude() {
        return penggunaLatitude;
    }

    public void setPenggunaLatitude(String penggunaLatitude) {
        this.penggunaLatitude = penggunaLatitude;
    }

    public String getPenggunaLongitude() {
        return penggunaLongitude;
    }

    public void setPenggunaLongitude(String penggunaLongitude) {
        this.penggunaLongitude = penggunaLongitude;
    }

    public String getPenggunaSaldo() {
        return penggunaSaldo;
    }

    public void setPenggunaSaldo(String penggunaSaldo) {
        this.penggunaSaldo = penggunaSaldo;
    }

    public String getPenggunaTglBuat() {
        return penggunaTglBuat;
    }

    public void setPenggunaTglBuat(String penggunaTglBuat) {
        this.penggunaTglBuat = penggunaTglBuat;
    }
}

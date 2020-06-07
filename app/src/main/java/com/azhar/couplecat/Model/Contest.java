package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class Contest {
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
    @SerializedName("jenis_kontes_id")
    private String jenisKontesId;
    @SerializedName("jenis_kontes_nama")
    private String jenisKontesNama;
}

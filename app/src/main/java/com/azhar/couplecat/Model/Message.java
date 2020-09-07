package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("chatting_id")
    private String chattingId;
    @SerializedName("chatting_text")
    private String chattingText;
    @SerializedName("chatting_tanggal")
    private String chattingTanggal;
    @SerializedName("chatting_status")
    private String chattingStatus;
    @SerializedName("chatting_kontak_id")
    private String chattingKontakId;
    @SerializedName("chatting_pengguna_id")
    private String chattingPenggunaId;
    @SerializedName("chatting_pengguna_id2")
    private String chattingPenggunaId2;

    public String getChattingId() {
        return chattingId;
    }

    public void setChattingId(String chattingId) {
        this.chattingId = chattingId;
    }

    public String getChattingText() {
        return chattingText;
    }

    public void setChattingText(String chattingText) {
        this.chattingText = chattingText;
    }

    public String getChattingTanggal() {
        return chattingTanggal;
    }

    public void setChattingTanggal(String chattingTanggal) {
        this.chattingTanggal = chattingTanggal;
    }

    public String getChattingStatus() {
        return chattingStatus;
    }

    public void setChattingStatus(String chattingStatus) {
        this.chattingStatus = chattingStatus;
    }

    public String getChattingKontakId() {
        return chattingKontakId;
    }

    public void setChattingKontakId(String chattingKontakId) {
        this.chattingKontakId = chattingKontakId;
    }

    public String getChattingPenggunaId() {
        return chattingPenggunaId;
    }

    public void setChattingPenggunaId(String chattingPenggunaId) {
        this.chattingPenggunaId = chattingPenggunaId;
    }

    public String getChattingPenggunaId2() {
        return chattingPenggunaId2;
    }

    public void setChattingPenggunaId2(String chattingPenggunaId2) {
        this.chattingPenggunaId2 = chattingPenggunaId2;
    }
}

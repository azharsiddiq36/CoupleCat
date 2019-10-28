package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class ResponsePengguna {
    @SerializedName("status")
    private Integer status;
    @SerializedName("data")
    private Pengguna data;
    @SerializedName("message")
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Pengguna getData() {
        return data;
    }

    public void setData(Pengguna data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

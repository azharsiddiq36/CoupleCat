package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseContact {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Kontak data;

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

    public Kontak getData() {
        return data;
    }

    public void setData(Kontak data) {
        this.data = data;
    }
}

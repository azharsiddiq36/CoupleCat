package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseToko {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Toko> data = null;
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

    public List<Toko> getData() {
        return data;
    }

    public void setData(List<Toko> data) {
        this.data = data;
    }
}

package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKontak {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Kontak> data = null;

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

    public List<Kontak> getData() {
        return data;
    }

    public void setData(List<Kontak> data) {
        this.data = data;
    }
}

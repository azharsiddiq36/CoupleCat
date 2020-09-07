package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePostingan {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Postingan> data = null;

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

    public List<Postingan> getData() {
        return data;
    }

    public void setData(List<Postingan> data) {
        this.data = data;
    }
}

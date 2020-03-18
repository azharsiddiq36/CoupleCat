package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMyCat {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;

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

    public List<MyCat> getData() {
        return data;
    }

    public void setData(List<MyCat> data) {
        this.data = data;
    }

    @SerializedName("data")

    private List<MyCat> data = null;
}

package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMessage {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Message> data = null;

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

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}

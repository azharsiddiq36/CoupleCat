package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseContestDetails {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Contest data;

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

    public Contest getData() {
        return data;
    }

    public void setData(Contest data) {
        this.data = data;
    }
}

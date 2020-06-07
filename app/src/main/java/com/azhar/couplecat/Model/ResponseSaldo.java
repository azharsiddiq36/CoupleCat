package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseSaldo {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("total")
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

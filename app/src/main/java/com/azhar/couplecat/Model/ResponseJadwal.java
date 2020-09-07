package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJadwal {
    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<Jadwal> data = null;
    @SerializedName("total")
    int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Jadwal> getData() {
        return data;
    }

    public void setData(List<Jadwal> data) {
        this.data = data;
    }
}

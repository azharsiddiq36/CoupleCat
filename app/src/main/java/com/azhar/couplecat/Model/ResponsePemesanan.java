package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePemesanan {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public List<Pemesanan> getData() {
        return data;
    }
    @SerializedName("id_list")
    private String id_list;

    public String getId_list() {
        return id_list;
    }

    public void setId_list(String id_list) {
        this.id_list = id_list;
    }

    @SerializedName("data")

    private List<Pemesanan> data = null;

    public void setData(List<Pemesanan> data) {
        this.data = data;
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

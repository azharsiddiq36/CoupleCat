package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLocation {
    @SerializedName("code")
    private Integer code;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private List<ApiLocation> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ApiLocation> getData() {
        return data;
    }

    public void setData(List<ApiLocation> data) {
        this.data = data;
    }
}

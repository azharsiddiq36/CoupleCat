package com.azhar.couplecat.Model;

import com.google.gson.annotations.SerializedName;

public class Weight {
    @SerializedName("imperial")
    private String imperial;
    @SerializedName("metric")
    private String metric;

    public String getImperial() {
        return imperial;
    }

    public void setImperial(String imperial) {
        this.imperial = imperial;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
}

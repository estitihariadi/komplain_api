package com.iqbalfahrul.com.administrasi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetKomplain {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    @Expose
    private List<Komplain> result = null;
    @SerializedName("message")
    @Expose
    private Boolean message;
    public GetKomplain() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Komplain> getResult() {
        return result;
    }

    public void setResult(List<Komplain> result) {
        this.result = result;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }
}

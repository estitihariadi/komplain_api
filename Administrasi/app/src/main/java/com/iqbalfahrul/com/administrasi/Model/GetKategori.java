package com.iqbalfahrul.com.administrasi.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetKategori {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<Kategori> result = null;
    @SerializedName("message")
    @Expose
    private Boolean message;
    public GetKategori() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Kategori> getResult() {
        return result;
    }

    public void setResult(List<Kategori> result) {
        this.result = result;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

}
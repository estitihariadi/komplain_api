package com.iqbalfahrulclient.com.komplain.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMahasiswa {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<Mahasiswa> result = null;
    @SerializedName("message")
    @Expose
    private Boolean message;
    public GetMahasiswa() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Mahasiswa> getResult() {
        return result;
    }

    public void setResult(List<Mahasiswa> result) {
        this.result = result;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

}

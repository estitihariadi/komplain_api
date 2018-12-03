package com.iqbalfahrul.com.administrasi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPegawai {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<Pegawai> result = null;
    @SerializedName("message")
    @Expose
    private Boolean message;
    public GetPegawai() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Pegawai> getResult() {
        return result;
    }

    public void setResult(List<Pegawai> result) {
        this.result = result;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

}

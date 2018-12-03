package com.iqbalfahrul.com.administrasi.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPegawai {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<Pegawai> result = null;

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

}
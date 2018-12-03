package com.iqbalfahrul.com.administrasi.Model;

import com.google.gson.annotations.SerializedName;

public class Pegawai {
    @SerializedName("id_pegawai")
    private String id_pegawai;
    @SerializedName("nama_pegawai")
    private String nama_pegawai;
    private String action;

    public Pegawai(String id_pegawai, String nama_pegawai, String action){
        this.id_pegawai = id_pegawai;
        this.nama_pegawai = nama_pegawai;
        this.action = action;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setId_pegawai(String id_pegawai){

        this.id_pegawai = id_pegawai;
    }
    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

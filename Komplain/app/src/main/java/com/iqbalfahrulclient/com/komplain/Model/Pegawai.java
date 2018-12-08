package com.iqbalfahrulclient.com.komplain.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pegawai {

    @SerializedName("id_pegawai")
    @Expose
    private String idPegawai;
    @SerializedName("nama_pegawai")
    @Expose
    private String namaPegawai;

    public Pegawai(String id_pegawai,String nama_kategori){
        this.idPegawai = id_pegawai;
        this.namaPegawai = nama_kategori;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

}
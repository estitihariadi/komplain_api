package com.iqbalfahrulclient.com.komplain.Model;

import com.google.gson.annotations.SerializedName;

public class Komplain {
    @SerializedName("id_komplain")
    private String id_komplain;
    @SerializedName("nim")
    private String nim;
    @SerializedName("id_pegawai")
    private String id_pegawai;
    @SerializedName("judul")
    private String judul;
    @SerializedName("id_kategori")
    private String id_kategori;
    @SerializedName("foto")
    private String foto;
    @SerializedName("foto_after")
    private String foto_after;
    @SerializedName("keluhan")
    private String keluhan;
    @SerializedName("lokasi")
    private String lokasi;
    @SerializedName("tgl_komplain")
    private String tgl_komplain;
    @SerializedName("status")
    private String status;
    private String action;

    public Komplain(String id_komplain, String nim, String id_pegawai, String judul, String id_kategori, String foto, String foto_after, String keluhan, String lokasi, String tgl_komplain, String status, String action) {
        this.id_komplain = id_komplain;
        this.nim = nim;
        this.id_pegawai = id_pegawai;
        this.judul = judul;
        this.id_kategori = id_kategori;
        this.foto = foto;
        this.foto_after = foto_after;
        this.keluhan = keluhan;
        this.lokasi = lokasi;
        this.tgl_komplain = tgl_komplain;
        this.status = status;
        this.action = action;
    }

    public String getId_komplain() {
        return id_komplain;
    }

    public void setId_komplain(String id_komplain) {
        this.id_komplain = id_komplain;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto_after() {
        return foto_after;
    }

    public void setFoto_after(String foto_after) {
        this.foto_after = foto_after;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTgl_komplain() {
        return tgl_komplain;
    }

    public void setTgl_komplain(String tgl_komplain) {
        this.tgl_komplain = tgl_komplain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

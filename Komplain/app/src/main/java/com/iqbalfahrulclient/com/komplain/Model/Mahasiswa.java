

package com.iqbalfahrulclient.com.komplain.Model; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mahasiswa {

    @SerializedName("nim")
    @Expose
    private String nim;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;
    @SerializedName("kelas")
    @Expose
    private String kelas;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("password")
    @Expose
    private String password;
    private String action;

    public Mahasiswa(String nim, String nama, String tglLahir, String kelas, String alamat, String password, String action) {
        this.nim = nim;
        this.nama = nama;
        this.tglLahir = tglLahir;
        this.kelas = kelas;
        this.alamat = alamat;
        this.password = password;
        this.action = action;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
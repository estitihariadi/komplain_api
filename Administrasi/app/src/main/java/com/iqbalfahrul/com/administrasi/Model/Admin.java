package com.iqbalfahrul.com.administrasi.Model;

import com.google.gson.annotations.SerializedName;

public class Admin {
    @SerializedName("id_admin")
    private String id_admin;
    @SerializedName("nama_admin")
    private String nama_admin;
    @SerializedName("username_admin")
    private String username_admin;
    @SerializedName("password_admin")
    private String password_admin;
    private String action;

    public Admin(String id_admin,String nama_admin,String username_admin,String password_admin,String action){
        this.id_admin = id_admin;
        this.nama_admin = nama_admin;
        this.username_admin = username_admin;
        this.password_admin = password_admin;
        this.action = action;
    }

    public String getId_admin() {
        return id_admin;
    }

    public String getUsername_admin() {
        return username_admin;
    }

    public void setUsername_admin(String username_admin) {
        this.username_admin = username_admin;
    }

    public String getPassword_admin() {
        return password_admin;
    }

    public void setPassword_admin(String password_admin) {
        this.password_admin = password_admin;
    }

    public void setId_admin(String id_admin){

        this.id_admin = id_admin;
    }

    public String getNama_admin() {
        return nama_admin;
    }

    public void setNama_admin(String nama_admin) {
        this.nama_admin = nama_admin;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

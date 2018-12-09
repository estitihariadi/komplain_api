package com.iqbalfahrulclient.com.komplain.Rest;


import com.iqbalfahrulclient.com.komplain.Model.GetAdmin;
import com.iqbalfahrulclient.com.komplain.Model.GetKategori;
import com.iqbalfahrulclient.com.komplain.Model.GetKomplain;
import com.iqbalfahrulclient.com.komplain.Model.GetMahasiswa;
import com.iqbalfahrulclient.com.komplain.Model.GetPegawai;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @GET("kategori/all")
    Call<GetKategori> getKategori();

    @Multipart
    @POST("kategori/all")
    Call<GetKategori> postKategori(
            @Part("id_kategori") RequestBody id_kategori,
            @Part("nama_kategori") RequestBody nama_kategori,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("kategori/all")
    Call<GetKategori> putKategori(
            @Part("id_kategori") RequestBody id_kategori,
            @Part("nama_kategori") RequestBody nama_kategori,
            @Part("action") RequestBody action
    );


    @GET("pegawai/all")
    Call<GetPegawai> getPegawai();

    @Multipart
    @POST("kategori/all")
    Call<GetKategori> deleteKategori(
            @Part("id_kategori") RequestBody id_kategori,
            @Part("action") RequestBody action);

    @GET("admin")
    Call<GetAdmin> getAdmin();

    @Multipart
    @POST("admin")
    Call<GetAdmin> postAdmin(
            @Part("nama_admin") RequestBody nama_admin,
            @Part("username_admin") RequestBody username_admin,
            @Part("password_admin") RequestBody password_admin,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("Login/login")
    Call<GetMahasiswa> postLoginMahasiswa(
            @Part("nim") RequestBody nim,
            @Part("password") RequestBody password
    );

    @Multipart
    @POST("admin")
    Call<GetAdmin> putAdmin(
            @Part("id_admin") RequestBody id_admin,
            @Part("nama_admin") RequestBody nama_admin,
            @Part("username_admin") RequestBody username_admin,
            @Part("password_admin") RequestBody password_admin,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("admin")
    Call<GetAdmin> deleteAdmin(
            @Part("id_admin") RequestBody id_admin,
            @Part("action") RequestBody action);

    @GET("komplain")
    Call<GetKomplain> getKomplain();

    @Multipart
    @POST("komplain/bynim")
    Call<GetKomplain> PostKomplain(
            @Part("nim") RequestBody nim
    );

    @Multipart
    @POST("komplain")
    Call<GetKomplain> postInsertKomplain(
            @Part MultipartBody.Part file,
            @Part("nim") RequestBody nim,
            @Part("judul") RequestBody judul,
            @Part("keluhan") RequestBody keluhan,
            @Part("lokasi") RequestBody lokasi,
            @Part("id_kategori") RequestBody id_kategori,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("komplain")
    Call<GetKomplain> putKomplain(
            @Part MultipartBody.Part file,
            @Part("id_komplain") RequestBody id_komplain,
            @Part("status") RequestBody status,
            @Part("id_pegawai") RequestBody idPegawai,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("komplain")
    Call<GetKomplain> deleteKomplain(
            @Part("id_komplain") RequestBody id_komplain,
            @Part("action") RequestBody action);

}

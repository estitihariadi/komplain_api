package com.iqbalfahrul.com.administrasi.Rest;

import com.iqbalfahrul.com.administrasi.Model.GetAdmin;
import com.iqbalfahrul.com.administrasi.Model.GetKategori;
import com.iqbalfahrul.com.administrasi.Model.GetKomplain;
import com.iqbalfahrul.com.administrasi.Model.GetPegawai;

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
    @POST("komplain")
    Call<GetKomplain> putKomplain(
            @Part MultipartBody.Part file,
            @Part("id_komplain") RequestBody id_komplain,
            @Part("status") RequestBody status,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("komplain")
    Call<GetKomplain> deleteKomplain(
            @Part("id_komplain") RequestBody id_komplain,
            @Part("action") RequestBody action);

    @GET("Pegawai/all")
    Call<GetPegawai> getPegawai();
}

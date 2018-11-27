package com.iqbalfahrul.com.administrasi.Rest;

import com.iqbalfahrul.com.administrasi.Model.GetKategori;

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
}

package com.iqbalfahrul.com.administrasi.Rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Mahasiswa {
    public static final String BASE_URL_pegawai = "http://192.168.1.7/komplain_api/mahasiswaserver/index.php/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_pegawai)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

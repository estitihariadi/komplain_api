package com.iqbalfahrulclient.com.komplain.Rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Mahasiswa {
    public static final String BASE_URL_pegawai = "http://10.215.43.165/komplain_api/mhsserver/index.php/";
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

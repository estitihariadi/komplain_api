package com.iqbalfahrul.com.administrasi.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL_komplain = "http://192.168.137.199/komplain_api/komplainserver/index.php/";
    public static final String BASE_URL_pegawai = "http://192.168.137.199/komplain_api/pegawaiserver/index.php/";
    public static final String LOAD_URL = "http://192.168.137.199/komplain_api/komplainserver/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_komplain)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

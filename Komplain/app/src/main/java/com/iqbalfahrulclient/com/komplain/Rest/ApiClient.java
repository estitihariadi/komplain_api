package com.iqbalfahrulclient.com.komplain.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL_komplain = "http://10.215.43.165/komplain_api/komplainserver/index.php/";
    public static final String LOAD_URL = "http://10.215.43.165/komplain_api/komplainserver/";
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

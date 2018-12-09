package com.iqbalfahrulclient.com.komplain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.iqbalfahrulclient.com.komplain.Adapter.KomplainAdapter;
import com.iqbalfahrulclient.com.komplain.Model.GetKomplain;
import com.iqbalfahrulclient.com.komplain.Model.Komplain;
import com.iqbalfahrulclient.com.komplain.Rest.ApiClient;
import com.iqbalfahrulclient.com.komplain.Rest.ApiInterface;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKomplain extends OpsiMenu {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    Button btGet,btAddData;

//    boolean doubleBackToExitPressedOnce = false; //untuk exit dengan press back 2x

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_komplain);

        mContext = this.getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btGet = (Button) findViewById(R.id.btGet);


        // langsung tampilkan data
        TampilData();

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // refresh data
                TampilData();
            }
        });
    }

    public void TampilData(){
        SharedPreferences handler = getSharedPreferences("Login",MODE_PRIVATE);
        String nim= handler.getString("username","");
        RequestBody reqNim = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (nim.isEmpty())?"":nim);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetKomplain> mKomplainCall = mApiInterface.PostKomplain(reqNim);

        (mKomplainCall).enqueue(new Callback<GetKomplain>() {
            @Override
            public void onResponse(Call<GetKomplain> call, Response<GetKomplain> response) {
                Log.d("Get Komplain",response.body().getStatus());
                List<Komplain> listKomplain = response.body().getResult();
                mAdapter = new KomplainAdapter(listKomplain,ListKomplain.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetKomplain> call, Throwable t) {
                Log.d("Get Komplain",t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (doubleBackToExitPressedOnce) {
//            finish();
//            System.exit(0);
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
    }
}

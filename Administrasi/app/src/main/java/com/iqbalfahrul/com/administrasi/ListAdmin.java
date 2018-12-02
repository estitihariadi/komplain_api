package com.iqbalfahrul.com.administrasi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iqbalfahrul.com.administrasi.Adapter.AdminAdapter;
import com.iqbalfahrul.com.administrasi.Model.GetAdmin;
import com.iqbalfahrul.com.administrasi.Model.Admin;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdmin extends OpsiMenu {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    Button btGet,btAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admin);

        mContext = this.getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btGet = (Button) findViewById(R.id.btGet);
        btAddData = (Button) findViewById(R.id.btAddData);

        // langsung tampilkan data
        TampilData();

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // refresh data
                TampilData();
            }
        });
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InsertAdmin.class);
                startActivity(intent);
            }
        });
    }

    public void TampilData(){
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAdmin> mAdminCall = mApiInterface.getAdmin();
        (mAdminCall).enqueue(new Callback<GetAdmin>() {
            @Override
            public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                Log.d("Get Admin",response.body().getStatus());
                List<Admin> listAdmin = response.body().getResult();
                mAdapter = new AdminAdapter(listAdmin,ListAdmin.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetAdmin> call, Throwable t) {
                Log.d("Get Admin",t.getMessage());
            }
        });
    }
}

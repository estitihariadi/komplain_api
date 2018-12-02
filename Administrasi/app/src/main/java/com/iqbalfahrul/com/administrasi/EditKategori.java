package com.iqbalfahrul.com.administrasi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iqbalfahrul.com.administrasi.Model.GetKategori;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKategori extends OpsiMenu {

    Context mContext;
    Button btEditBack, btEditData;
    EditText edtEditIdKategori, edtEditNamaKategori;
    TextView tvEditMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kategori);

        mContext = getApplicationContext();

        edtEditIdKategori = (EditText) findViewById(R.id.edtEditIdKategori);
        edtEditNamaKategori = (EditText) findViewById(R.id.edtEditNamaKategori);

        btEditData = (Button) findViewById(R.id.btEditData);
        btEditBack = (Button) findViewById(R.id.btEditBack);
        tvEditMessage = (TextView) findViewById(R.id.tvEditMessage);

        Intent i = getIntent();
        edtEditIdKategori.setText(i.getStringExtra("id_kategori"));
        edtEditNamaKategori.setText(i.getStringExtra("nama_kategori"));

        btEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                RequestBody reqIdKategori = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtEditIdKategori.getText().toString().isEmpty())?"":edtEditIdKategori.getText().toString());
                RequestBody reqNamaKategori = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtEditNamaKategori.getText().toString().isEmpty())?"":edtEditNamaKategori.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "update");

                Call<GetKategori> mKategoriCall = mApiInterface.putKategori(reqIdKategori,
                        reqNamaKategori, reqAction );
                mKategoriCall.enqueue(new Callback<GetKategori>() {
                    @Override
                    public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                        Log.d("Edit Kategori",response.body().getMessage().toString());
                        if (response.body().getStatus().equals("failed")){
                            tvEditMessage.setText("Edit Kategori \n Status = "+response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "ID = "+response.body().getResult().get(0).getId_kategori()+"\n"+
                                    "nama kategori = "+response.body().getResult().get(0).getNama_kategori()+"\n";
                            tvEditMessage.setText("Edit Kategori \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKategori> call, Throwable t) {
                        Log.d("Edit Kategori", t.getMessage());
                        tvEditMessage.setText("Edit Kategori Failure \n Status = "+ t.getMessage
                                ());
                    }
                });
            }
        });
        btEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListKategori.class);
                startActivity(intent);
            }
        });
    }
}

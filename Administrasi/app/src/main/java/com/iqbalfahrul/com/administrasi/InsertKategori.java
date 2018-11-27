package com.iqbalfahrul.com.administrasi;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.iqbalfahrul.com.administrasi.Model.GetKategori;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertKategori extends AppCompatActivity {

    Context mContext;
    ImageView mImageView;
    Button btAddPhotoId, btAddBack, btAddData;
    EditText edtAddIdKategori, edtAddNamaKategori, edtAddAlamatPembeli;
    EditText edtAddTelpPembeli;
    TextView tvAddMessage;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_kategori);

        mContext = getApplicationContext();

        edtAddIdKategori = (EditText) findViewById(R.id.edtAddIdKategori);
        edtAddNamaKategori = (EditText) findViewById(R.id.edtAddNamaKategori);


        btAddData = (Button) findViewById(R.id.btAddData);
        btAddBack = (Button) findViewById(R.id.btAddBack);
        tvAddMessage = (TextView) findViewById(R.id.tvAddMessage);

        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);


                RequestBody reqIdKategori = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddIdKategori.getText().toString().isEmpty())?"":edtAddIdKategori.getText().toString());
                RequestBody reqNamaKategori = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddNamaKategori.getText().toString().isEmpty())?"":edtAddNamaKategori.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                Call<GetKategori> mPembeliCall = mApiInterface.postKategori(reqIdKategori,  reqNamaKategori, reqAction );
                mPembeliCall.enqueue(new Callback<GetKategori>() {
                    @Override
                    public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
//                      Log.d("Insert Retrofit",response.body().getMessage());
                        if (response.body().getStatus().equals("failed")){
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "id_pembeli = "+response.body().getResult().get(0).getId_kategori() +"\n"+
                                    "nama = "+response.body().getResult().get(0).getNama_kategori()+"\n";
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKategori> call, Throwable t) {
//                      Log.d("Insert Retrofit", t.getMessage());
                        tvAddMessage.setText("Retrofit Insert Failure \n Status = "+ t.getMessage
                                ());
                    }
                });
            }
        });
        btAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListKategori.class);
                startActivity(intent);
            }
        });

    }
    }

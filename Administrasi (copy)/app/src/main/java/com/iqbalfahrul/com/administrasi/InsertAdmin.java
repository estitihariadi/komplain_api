package com.iqbalfahrul.com.administrasi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iqbalfahrul.com.administrasi.Model.GetAdmin;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertAdmin extends OpsiMenu {

    Context mContext;
    Button btAddBack, btAddData;
    EditText edtAddIdAdmin, edtAddNamaAdmin, edtAddUsername, edtAddPassword;
    TextView tvAddMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_admin);

        mContext = getApplicationContext();

//        edtAddIdAdmin = (EditText) findViewById(R.id.edtAddIdAdmin);
        edtAddNamaAdmin = (EditText) findViewById(R.id.edtAddNamaAdmin);
        edtAddUsername = (EditText) findViewById(R.id.edtAddUsernameAdmin);
        edtAddPassword = (EditText) findViewById(R.id.edtAddPasswordAdmin);

        btAddData = (Button) findViewById(R.id.btAddData);
        btAddBack = (Button) findViewById(R.id.btAddBack);
        tvAddMessage = (TextView) findViewById(R.id.tvAddMessage);

        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

//                RequestBody reqIdAdmin = MultipartBody.create(MediaType.parse("multipart/form-data"),
//                        (edtAddIdAdmin.getText().toString().isEmpty())?"":edtAddIdAdmin.getText().toString());
                RequestBody reqNamaAdmin = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddNamaAdmin.getText().toString().isEmpty())?"":edtAddNamaAdmin.getText().toString());
                RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddUsername.getText().toString().isEmpty())?"":edtAddUsername.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddPassword.getText().toString().isEmpty())?"":edtAddPassword.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                Call<GetAdmin> mAdminCall = mApiInterface.postAdmin( reqNamaAdmin,
                        reqUsername, reqPassword, reqAction );
                mAdminCall.enqueue(new Callback<GetAdmin>() {
                    @Override
                    public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
//                      Log.d("Insert Retrofit",response.body().getMessage());
                        if (response.body().getStatus().equals("failed")){
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "nama = "+response.body().getResult().get(0).getNama_admin()+"\n"+
                                    "username = "+response.body().getResult().get(0).getUsername_admin()+"\n"+
                                    "password = "+response.body().getResult().get(0).getPassword_admin()+"\n";
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAdmin> call, Throwable t) {
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
                Intent intent = new Intent(mContext, ListAdmin.class);
                startActivity(intent);
            }
        });

    }
}

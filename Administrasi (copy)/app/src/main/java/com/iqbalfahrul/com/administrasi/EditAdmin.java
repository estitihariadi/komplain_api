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

import com.iqbalfahrul.com.administrasi.Model.GetAdmin;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAdmin extends OpsiMenu {

    Context mContext;
    Button btEditBack, btEditData;
    EditText edtEditIdAdmin, edtEditNamaAdmin, edtEditUsername, edtEditPassword;
    TextView tvEditMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);

        mContext = getApplicationContext();

        edtEditIdAdmin = (EditText) findViewById(R.id.edtEditIdAdmin);
        edtEditNamaAdmin = (EditText) findViewById(R.id.edtEditNamaAdmin);
        edtEditUsername = (EditText) findViewById(R.id.edtEditUsernameAdmin);
        edtEditPassword = (EditText) findViewById(R.id.edtEditPasswordAdmin);

        btEditData = (Button) findViewById(R.id.btEditData);
        btEditBack = (Button) findViewById(R.id.btEditBack);
        tvEditMessage = (TextView) findViewById(R.id.tvEditMessage);

        Intent i = getIntent();
        edtEditIdAdmin.setText(i.getStringExtra("id_admin"));
        edtEditNamaAdmin.setText(i.getStringExtra("nama_admin"));
        edtEditUsername.setText(i.getStringExtra("username_admin"));

        btEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                if (edtEditPassword.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Password Harus Diisi", Toast.LENGTH_LONG).show();
                    return;
                }

                RequestBody reqIdAdmin = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtEditIdAdmin.getText().toString().isEmpty())?"":edtEditIdAdmin.getText().toString());
                RequestBody reqNamaAdmin = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtEditNamaAdmin.getText().toString().isEmpty())?"":edtEditNamaAdmin.getText().toString());
                RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtEditUsername.getText().toString().isEmpty())?"":edtEditUsername.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtEditPassword.getText().toString().isEmpty())?"":edtEditPassword.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "update");
                Call<GetAdmin> mAdminCall = mApiInterface.putAdmin(reqIdAdmin, reqNamaAdmin,
                        reqUsername, reqPassword, reqAction );
                mAdminCall.enqueue(new Callback<GetAdmin>() {
                    @Override
                    public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                        Log.d("Edit Admin",response.body().getMessage().toString());
                        if (response.body().getStatus().equals("failed")){
                            tvEditMessage.setText("Edit Admin \n Status = "+response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "ID = "+response.body().getResult().get(0).getId_admin()+"\n"+
                                    "nama = "+response.body().getResult().get(0).getNama_admin()+"\n"+
                                    "username = "+response.body().getResult().get(0).getUsername_admin()+"\n"+
                                    "password = "+response.body().getResult().get(0).getPassword_admin()+"\n";
                            tvEditMessage.setText("Edit Admin \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAdmin> call, Throwable t) {
                        Log.d("Edit Admin", t.getMessage());
                        tvEditMessage.setText("Edit Admin Failure \n Status = "+ t.getMessage
                                ());
                    }
                });
            }
        });
        btEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListAdmin.class);
                startActivity(intent);
            }
        });
    }
}

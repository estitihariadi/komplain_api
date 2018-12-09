package com.iqbalfahrul.com.administrasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iqbalfahrul.com.administrasi.Adapter.AdminAdapter;
import com.iqbalfahrul.com.administrasi.Model.Admin;
import com.iqbalfahrul.com.administrasi.Model.GetAdmin;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;
    ProgressDialog loading;
    ApiInterface mApiInterface;
    ArrayList<String> list;
    Context mContext;
    String husername,hpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        list = new ArrayList<>();
        mContext = getApplicationContext();
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

      checkSavedCredentials();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (etEmail.getText().toString().isEmpty())?"":etEmail.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (etPassword.getText().toString().isEmpty())?"":etPassword.getText().toString());
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetAdmin> mAdminCall = mApiInterface.postLoginAdmin(reqUsername, reqPassword);
                mAdminCall.enqueue(new Callback<GetAdmin>() {
                    @Override
                    public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                        if (response.body().getStatus().equals("error")){
                            Log.d("",response.body().getStatus());
                            Toast.makeText(mContext, "Invalid username and/or password",Toast.LENGTH_SHORT).show();
                        }else  if (response.body().getStatus().equals("success")){
                            String username = etEmail.getText().toString();
                            saveCredentials();
                            openHome(username);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAdmin> call, Throwable t) {
                        Log.e("Login Error", t.getMessage());
                        Toast.makeText(mContext, "Login Gagal : "+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }



    private void saveCredentials(){
        SharedPreferences handler = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = handler.edit();
        editor.putString("username",this.etEmail.getText().toString());
        editor.putString("password",this.etPassword.getText().toString());
        editor.apply();
    }

    private void openHome(String username){
        Toast.makeText(mContext, "Login Berhasil",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this.getApplicationContext(),MainActivity.class);
        i.putExtra("username",username);
        this.startActivity(i);
    }

    private void checkSavedCredentials(){
        SharedPreferences handler = getSharedPreferences("Login",MODE_PRIVATE);
        husername= handler.getString("username","");
        hpassword= handler.getString("password","");
        RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (husername.isEmpty())?"":husername.toString());
        RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (hpassword.isEmpty())?"":hpassword.toString());
        ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAdmin> mAdminCall = mApiInterface.postLoginAdmin(reqUsername, reqPassword);
        mAdminCall.enqueue(new Callback<GetAdmin>() {
            @Override
            public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                if (response.body().getStatus().equals("error")){
                    Log.d("",response.body().getStatus());
                    Toast.makeText(mContext, "Invalid username and/or password",Toast.LENGTH_SHORT).show();
                }else  if (response.body().getStatus().equals("success")){
                    openHome(hpassword);
                }
            }

            @Override
            public void onFailure(Call<GetAdmin> call, Throwable t) {
                Log.d("Login Error", t.getMessage());
            }
        });
    }
}

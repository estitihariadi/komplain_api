package com.iqbalfahrulclient.com.komplain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iqbalfahrulclient.com.komplain.Model.GetMahasiswa;
import com.iqbalfahrulclient.com.komplain.Rest.ApiInterface;
import com.iqbalfahrulclient.com.komplain.Rest.Mahasiswa;

import java.util.ArrayList;

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
        mApiInterface = Mahasiswa.getClient().create(ApiInterface.class);

       checkSavedCredentials();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (etEmail.getText().toString().isEmpty())?"":etEmail.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (etPassword.getText().toString().isEmpty())?"":etPassword.getText().toString());

                ApiInterface mApiInterface = Mahasiswa.getClient().create(ApiInterface.class);
                Call<GetMahasiswa>  mmhsCall = mApiInterface.postLoginMahasiswa(reqUsername,reqPassword);
                mmhsCall.enqueue(new Callback<GetMahasiswa>() {
                    @Override
                    public void onResponse(Call<GetMahasiswa> call, Response<GetMahasiswa> response) {
                        if (response.body().getStatus().equals("error")){
                            Log.e("",response.body().getStatus());
                            Toast.makeText(mContext, "Invalid NIM and/or password",Toast.LENGTH_SHORT).show();
                        }else  if (response.body().getStatus().equals("success")){
                            saveCredentials();
                            openHome(etEmail.getText().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMahasiswa> call, Throwable t) {
                        Log.e("Login Error", t.getMessage());
                        Toast.makeText(mContext, "Login error : "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }

    private void saveCredentials(){
        SharedPreferences handler = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = handler.edit();
        editor.putString("username", this.etEmail.getText().toString());
        editor.putString("password", this.etPassword.getText().toString());
        editor.apply();
    }

    private void openHome(String username) {
        Toast.makeText(mContext, "Login Berhasil",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this.getApplicationContext(), MainActivity.class);
        i.putExtra("username", username);
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

        ApiInterface mApiInterface = Mahasiswa.getClient().create(ApiInterface.class);
        Call<GetMahasiswa> mmhsCall = mApiInterface.postLoginMahasiswa(reqUsername, reqPassword);
        mmhsCall.enqueue(new Callback<GetMahasiswa>() {
            @Override
            public void onResponse(Call<GetMahasiswa> call, Response<GetMahasiswa> response) {
                if (response.body().getStatus().equals("error")){
                    Log.d("",response.body().getStatus());
                    Toast.makeText(mContext, "Invalid NIM and/or password",Toast.LENGTH_SHORT).show();
                }else  if (response.body().getStatus().equals("success")){
                    openHome(husername);
                }
            }

            @Override
            public void onFailure(Call<GetMahasiswa> call, Throwable t) {
                Log.e("Login Error", t.getMessage());
                Toast.makeText(mContext, "Login error : "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

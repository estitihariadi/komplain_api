package com.iqbalfahrul.com.administrasi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iqbalfahrul.com.administrasi.Adapter.AdminAdapter;
import com.iqbalfahrul.com.administrasi.Model.Admin;
import com.iqbalfahrul.com.administrasi.Model.GetAdmin;
import com.iqbalfahrul.com.administrasi.Model.GetKomplain;
import com.iqbalfahrul.com.administrasi.Model.GetPegawai;
import com.iqbalfahrul.com.administrasi.Model.Pegawai;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;
import com.iqbalfahrul.com.administrasi.Rest.PegawaiClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKomplain extends OpsiMenu {

    Context mContext;
    Button btEditBack, btEditData, btphotourl;
    ImageView ivFoto, ivFotoAfter;
    EditText edtEditIdKomplain,  edtEditIdKategori, edtEditNim, edtEditJudul,
            edtEditKeluhan, edtEditLokasi, edtEditTglKomplain;
    Spinner spinnerStatus,edtEditIdPegawai;
    String pathImage="";
    TextView tvEditMessage;
    ApiInterface mApiPegawai;
    String idpegawai;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_komplain);

        mContext = getApplicationContext();

        edtEditIdKomplain = (EditText) findViewById(R.id.edtEditIdKomplain);
        edtEditIdPegawai = (Spinner) findViewById(R.id.edtEditIdPegawai);
        edtEditIdKategori = (EditText) findViewById(R.id.edtEditIdKategori);
        edtEditNim = (EditText) findViewById(R.id.edtEditNim);
        edtEditJudul = (EditText) findViewById(R.id.edtEditJudul);
        edtEditKeluhan = (EditText) findViewById(R.id.edtEditKeluhan);
        edtEditLokasi = (EditText) findViewById(R.id.edtEditLokasi);
        edtEditTglKomplain = (EditText) findViewById(R.id.edtEditTglKomplain);

        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);

        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        ivFotoAfter = (ImageView) findViewById(R.id.ivFotoAfter);

        btphotourl = (Button) findViewById(R.id.btPhotoId);
        btEditData = (Button) findViewById(R.id.btEditData);
        btEditBack = (Button) findViewById(R.id.btEditBack);
        tvEditMessage = (TextView) findViewById(R.id.tvEditMessage);

       i = getIntent();
        initPegawai();
        edtEditIdKomplain.setText(i.getStringExtra("id_komplain"));
        edtEditIdKategori.setText(i.getStringExtra("id_kategori"));
        edtEditNim.setText(i.getStringExtra("nim"));
        edtEditJudul.setText(i.getStringExtra("judul"));
        edtEditKeluhan.setText(i.getStringExtra("keluhan"));
        edtEditLokasi.setText(i.getStringExtra("lokasi"));
        edtEditTglKomplain.setText(i.getStringExtra("tgl_komplain"));
        idpegawai = "0";
        edtEditIdPegawai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
//                requestDetailDosen(selectedName);
                Toast.makeText(mContext, "Kamu memilih Pegawai " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (i.getStringExtra("foto").isEmpty() ||
                i.getStringExtra("foto") == null) {
            Picasso.with(mContext).load(R.drawable.avatarr).into(ivFoto);
        } else {
            Picasso.with(mContext).load(ApiClient.LOAD_URL + i.getStringExtra("foto")).into(ivFoto);
        }

        if (i.getStringExtra("foto_after").isEmpty() ||
                i.getStringExtra("foto_after") == null) {
            Picasso.with(mContext).load(R.drawable.mwam).into(ivFotoAfter);
        } else {
            Picasso.with(mContext).load(ApiClient.LOAD_URL + i.getStringExtra("foto_after")).into(ivFotoAfter);
        }

        final int spinnerSelection;
        switch (i.getStringExtra("status")) {
            case "Belum":
                spinnerSelection = 0;
                break;
            case "Diproses":
                spinnerSelection = 1;
                break;
            case "Ditolak":
                spinnerSelection = 2;
                break;
            case "Selesai":
                spinnerSelection = 3;
                break;
            default:
                spinnerSelection = 0;
                break;
        }
        spinnerStatus.setSelection(spinnerSelection);
        setListener();
    }

    private void initPegawai(){
        mApiPegawai = PegawaiClient.getClient().create(ApiInterface.class);
        Call<GetPegawai> mpegawaiCall = mApiPegawai.getPegawai();
        (mpegawaiCall).enqueue(new Callback<GetPegawai>() {
            @Override
            public void onResponse(Call<GetPegawai> call, Response<GetPegawai> response) {
                if (response.body().getStatus().equals("failed")){
                    tvEditMessage.setText("Retrofit Update \n Status = " + response.body().getStatus()+"\n"+ "Message = "+response.body().getStatus()+"\n");
                }else {
                    List<Pegawai> semuapegawaiItems = response.body().getResult();

                    Pegawai object[] = new  Pegawai[semuapegawaiItems.size()];

                    for (int i = 0; i < semuapegawaiItems.size(); i++){
                       object[i] = new Pegawai(semuapegawaiItems.get(i).getIdPegawai(), semuapegawaiItems.get(i).getNamaPegawai());
                    }

                    MySpinnerAdapter adapter2 =
                            new MySpinnerAdapter(DetailKomplain.this,
                                    android.R.layout.simple_spinner_item,object);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    edtEditIdPegawai.setAdapter(adapter2);
                    edtEditIdPegawai.setSelection(Integer.parseInt(i.getStringExtra("id_pegawai"))-1);
                    edtEditIdPegawai.setOnItemSelectedListener(onItemSelectedListener2);
                }
            }

            @Override
            public void onFailure(Call<GetPegawai> call, Throwable t) {
                Log.d("Get Pegawai",t.getMessage());
            }
        });


    }

    AdapterView.OnItemSelectedListener onItemSelectedListener2 =
            new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Pegawai obj = (Pegawai) (parent.getItemAtPosition(position));
                   idpegawai = String.valueOf(obj.getIdPegawai());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}

            };

    private void setListener() {
        final ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            btEditData.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    MultipartBody.Part body = null;
                    //dicek apakah image sama dengan yang ada di server atau berubah
                    //jika sama dikirim lagi jika berbeda akan dikirim ke server
                    if ((!pathImage.contains("upload/")) && (!pathImage.isEmpty())){
                        //File creating from selected URL
                        File file = new File(pathImage);

                        // create RequestBody instance from file
                        RequestBody requestFile = RequestBody.create(
                                MediaType.parse("multipart/form-data"), file);

                        // MultipartBody.Part is used to send also the actual file name
                        body = MultipartBody.Part.createFormData("foto_after", file.getName(),
                                requestFile);
                    }

                RequestBody reqIdKomplain =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtEditIdKomplain.getText().toString().isEmpty()) ?
                                        "" : edtEditIdKomplain.getText().toString());

                RequestBody reqStatus =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (spinnerStatus.getSelectedItem().toString().isEmpty()) ?
                                        "" : spinnerStatus.getSelectedItem().toString());
                    RequestBody reqIdPegawai =
                            MultipartBody.create(MediaType.parse("multipart/form-data"),
                                    (idpegawai.isEmpty()) ?
                                            "" : idpegawai);

                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"), "update");

                Call<GetKomplain> callUpdate = mApiInterface.putKomplain(body, reqIdKomplain, reqStatus, reqIdPegawai, reqAction);

                    callUpdate.enqueue(new Callback<GetKomplain>() {
                        @Override
                        public void onResponse(Call<GetKomplain> call, Response<GetKomplain> response) {
                            //Log.d("Update Retrofit ", response.body().getStatus());
                            if (response.body().getStatus().equals("failed")){
                               tvEditMessage.setText("Retrofit Update \n Status = " + response.body().getStatus()+"\n"+ "Message = "+response.body().getMessage()+"\n");
                            }else{
                                String detail = "\n"+
                                        "id_komplain = "+response.body().getResult().get(0).getId_komplain()+"\n"+
                                        "status = "+response.body().getResult().get(0).getStatus()+"\n"+
                                        "photo_url = "+response.body().getResult().get(0).getFoto_after()
                                        +"\n";
                               tvEditMessage.setText("Retrofit Update \n Status = "+response.body().getStatus()+"\n"+ "Message = "+response.body().getMessage()+detail);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetKomplain> call, Throwable t) {
                            //Log.d("Update Retrofit ", t.getMessage());
                            tvEditMessage.setText("Retrofit Update \n Status = "+ t.getMessage());
                        }
                    });


                }
            });


            btEditBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ListKomplain.class);
                    startActivity(intent);
                }
            });

            btphotourl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_PICK);
                    Intent intentChoose = Intent.createChooser(galleryIntent, "Pilih foto untuk " +
                            "di-upload");
                    startActivityForResult(intentChoose, 10);
                }
            });

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10) {
            if (data == null) {
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                pathImage = cursor.getString(columnIndex);

                //Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
                Glide.with(mContext).load(new File(pathImage)).into(ivFotoAfter);
                cursor.close();
            } else {
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
            }
        }
    }
}

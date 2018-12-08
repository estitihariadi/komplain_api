package com.iqbalfahrulclient.com.komplain;


import android.os.Bundle;


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

import com.iqbalfahrulclient.com.komplain.Model.GetPegawai;
import com.iqbalfahrulclient.com.komplain.Model.Pegawai;
import com.iqbalfahrulclient.com.komplain.Rest.ApiClient;
import com.iqbalfahrulclient.com.komplain.Rest.ApiInterface;
import com.iqbalfahrulclient.com.komplain.Rest.PegawaiClient;
import com.squareup.picasso.Picasso;


public class DetailKomplain extends OpsiMenu {

    Context mContext;
    Button btEditBack;
    ImageView ivFoto, ivFotoAfter;
    EditText edtEditIdKomplain,  edtEditIdKategori, edtEditNim, edtEditJudul,
            edtEditKeluhan, edtEditLokasi,spinnerStatus;
    String pathImage="";
    TextView edtEditTglKomplain;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_komplain);

        mContext = getApplicationContext();

        edtEditJudul = (EditText) findViewById(R.id.edtEditJudul);
        edtEditKeluhan = (EditText) findViewById(R.id.edtEditKeluhan);
        edtEditLokasi = (EditText) findViewById(R.id.edtEditLokasi);
        edtEditTglKomplain = (TextView) findViewById(R.id.tvEditTglKomplain);
        spinnerStatus = (EditText) findViewById(R.id.spinnerStatus);
        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        ivFotoAfter = (ImageView) findViewById(R.id.ivFotoAfter);
        btEditBack = (Button) findViewById(R.id.btEditBack);


        i = getIntent();

        edtEditJudul.setText(i.getStringExtra("judul"));
        edtEditKeluhan.setText(i.getStringExtra("keluhan"));
        edtEditLokasi.setText(i.getStringExtra("lokasi"));
        edtEditTglKomplain.setText(i.getStringExtra("tgl_komplain"));
        spinnerStatus.setText(i.getStringExtra("status"));

        if (i.getStringExtra("foto").isEmpty() ||
                i.getStringExtra("foto") == null) {
            Picasso.with(mContext).load(R.drawable.avatarr).into(ivFoto);
        } else {
            Picasso.with(mContext).load(ApiClient.LOAD_URL + i.getStringExtra("foto")).into(ivFoto);
        }

        if (i.getStringExtra("foto_after").isEmpty() ||
                i.getStringExtra("foto_after") == null) {
            Picasso.with(mContext).load(R.drawable.avatarr).into(ivFotoAfter);
        } else {
            Picasso.with(mContext).load(ApiClient.LOAD_URL + i.getStringExtra("foto_after")).into(ivFotoAfter);
        }


        setListener();
    }




    private void setListener() {
        btEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListKomplain.class);
                startActivity(intent);
            }
        });

    }


}

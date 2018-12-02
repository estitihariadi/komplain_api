package com.iqbalfahrul.com.administrasi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailKomplain extends OpsiMenu {

    Context mContext;
    Button btEditBack, btEditData;
    ImageView ivFoto, ivFotoAfter;
    EditText edtEditIdKomplain, edtEditIdPegawai, edtEditIdKategori, edtEditNim, edtEditJudul,
            edtEditKeluhan, edtEditLokasi, edtEditTglKomplain;
    Spinner spinnerStatus;
    TextView tvEditMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_komplain);

        mContext = getApplicationContext();

        edtEditIdKomplain = (EditText) findViewById(R.id.edtEditIdKomplain);
        edtEditIdPegawai = (EditText) findViewById(R.id.edtEditIdPegawai);
        edtEditIdKategori = (EditText) findViewById(R.id.edtEditIdKategori);
        edtEditNim = (EditText) findViewById(R.id.edtEditNim);
        edtEditJudul = (EditText) findViewById(R.id.edtEditJudul);
        edtEditKeluhan = (EditText) findViewById(R.id.edtEditKeluhan);
        edtEditLokasi = (EditText) findViewById(R.id.edtEditLokasi);
        edtEditTglKomplain = (EditText) findViewById(R.id.edtEditTglKomplain);

        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);

        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        ivFotoAfter = (ImageView) findViewById(R.id.ivFotoAfter);

        btEditData = (Button) findViewById(R.id.btEditData);
        btEditBack = (Button) findViewById(R.id.btEditBack);
        tvEditMessage = (TextView) findViewById(R.id.tvEditMessage);

        Intent i = getIntent();
        edtEditIdKomplain.setText(i.getStringExtra("id_komplain"));
        edtEditIdPegawai.setText(i.getStringExtra("id_pegawai"));
        edtEditIdKategori.setText(i.getStringExtra("id_kategori"));
        edtEditNim.setText(i.getStringExtra("nim"));
        edtEditJudul.setText(i.getStringExtra("judul"));
        edtEditKeluhan.setText(i.getStringExtra("keluhan"));
        edtEditLokasi.setText(i.getStringExtra("lokasi"));
        edtEditTglKomplain.setText(i.getStringExtra("tgl_komplain"));

        if (i.getStringExtra("foto").isEmpty() ||
                i.getStringExtra("foto") == null){
            Picasso.with(mContext).load(R.drawable.avatarr).into(ivFoto);
        } else {
            Picasso.with(mContext).load(i.getStringExtra("foto")).into(ivFoto);
        }

        if (i.getStringExtra("foto_after").isEmpty() ||
                i.getStringExtra("foto_after") == null){
            Picasso.with(mContext).load(R.drawable.mwam).into(ivFotoAfter);
        } else {
            Picasso.with(mContext).load(i.getStringExtra("foto_after")).into(ivFotoAfter);
        }

        int spinnerSelection;
        switch (i.getStringExtra("status")) {
            case "Belum" :
                spinnerSelection = 0;
                break;
            case "Diproses" :
                spinnerSelection = 1;
                break;
            case "Ditolak" :
                spinnerSelection = 2;
                break;
            case "Selesai" :
                spinnerSelection = 3;
                break;
            default:
                spinnerSelection = 0;
                break;
        }
        spinnerStatus.setSelection(spinnerSelection);

        btEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListKomplain.class);
                startActivity(intent);
            }
        });
    }
}

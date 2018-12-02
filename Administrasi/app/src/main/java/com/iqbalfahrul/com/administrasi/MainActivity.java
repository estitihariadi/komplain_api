package com.iqbalfahrul.com.administrasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btListAdmin, btListKategori, btListKomplain;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btListAdmin = findViewById(R.id.btListAdmin);
        btListKategori = findViewById(R.id.btListKategori);
        btListKomplain = findViewById(R.id.btListKomplain);

        btListAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(v.getContext(), ListAdmin.class);
                startActivity(mIntent);
            }
        });

        btListKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(v.getContext(), ListKategori.class);
                startActivity(mIntent);
            }
        });

        btListKomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(v.getContext(), ListKomplain.class);
                startActivity(mIntent);
            }
        });
    }

}

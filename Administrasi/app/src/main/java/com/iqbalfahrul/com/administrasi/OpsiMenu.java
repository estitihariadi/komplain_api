package com.iqbalfahrul.com.administrasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class OpsiMenu extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIntent;
        switch (item.getItemId()) {

            case R.id.menuUtama:
                mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.menuListAdmin:
                mIntent = new Intent(this, ListAdmin.class);
                startActivity(mIntent);
                return true;
            case R.id.menuListKategori:
                mIntent = new Intent(this, ListKategori.class);
                startActivity(mIntent);
                return true;
            case R.id.menuListKomplain:
                mIntent = new Intent(this, ListKomplain.class);
                startActivity(mIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

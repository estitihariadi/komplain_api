package com.iqbalfahrulclient.com.komplain;

import android.content.Intent;
import android.content.SharedPreferences;
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

            case R.id.menuListKomplain:
                mIntent = new Intent(this, ListKomplain.class);
                startActivity(mIntent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetPreferences(){
        SharedPreferences handler = getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor editor = handler.edit();
        editor.clear();
        editor.commit();

    }

    private void backLogin(){
        Intent i = new Intent(this.getApplicationContext(),Login.class);
        this.startActivity(i);
    }
}

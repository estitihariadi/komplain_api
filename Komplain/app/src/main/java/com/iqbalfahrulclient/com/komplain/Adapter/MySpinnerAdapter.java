package com.iqbalfahrulclient.com.komplain.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iqbalfahrulclient.com.komplain.Model.Kategori;

public class MySpinnerAdapter extends ArrayAdapter<Kategori> {

    private Context context;
    private Kategori[] myObjs;

    public MySpinnerAdapter(Context context, int textViewResourceId,
                            Kategori[] myObjs) {
        super(context, textViewResourceId, myObjs);
        this.context = context;
        this.myObjs = myObjs;
    }

    public int getCount(){
        return myObjs.length;
    }

    public Kategori getItem(int position){
        return myObjs[position];
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(myObjs[position].getId_kategori());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(myObjs[position].getNama_kategori());
        return label;
    }
}


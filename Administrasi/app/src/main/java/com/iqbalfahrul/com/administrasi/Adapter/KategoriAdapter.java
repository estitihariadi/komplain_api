package com.iqbalfahrul.com.administrasi.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.iqbalfahrul.com.administrasi.EditKategori;
import com.iqbalfahrul.com.administrasi.ListKategori;
import com.iqbalfahrul.com.administrasi.Model.GetKategori;
import com.iqbalfahrul.com.administrasi.Model.Kategori;
import com.iqbalfahrul.com.administrasi.R;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> {

    List<Kategori> listKategori;

    Context context;

    public KategoriAdapter(List<Kategori> listKategori,Context context) {
        this.listKategori = listKategori;
        this.context = context;
    }

    @Override
    public KategoriAdapter.KategoriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kategori, parent, false);
        KategoriViewHolder mHolder = new KategoriViewHolder((view));
        return mHolder;
    }

    @Override
    public void onBindViewHolder(KategoriAdapter.KategoriViewHolder holder, final int position) {
        final ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        holder.tvNamaKategori.setText(listKategori.get(position).getNama_kategori());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Hapus Pesan");
                alertDialog.setMessage("Anda Yakin Ingin Menghapus ?");
                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RequestBody reqIdKategori =
                                MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (listKategori.get(position).getId_kategori().isEmpty())?
                                                "" : listKategori.get(position).getId_kategori());
                        RequestBody reqAction =
                                MultipartBody.create(MediaType.parse("multipart/form-data"), "delete");

                        Call<GetKategori> callDelete = mApiInterface.deleteKategori(reqIdKategori,reqAction);
                        callDelete.enqueue(new Callback<GetKategori>() {
                            @Override
                                public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                                notifyDataSetChanged();
                                }

                            @Override
                            public void onFailure(Call<GetKategori> call, Throwable t) {

                            }

                        });

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {
        TextView  tvNamaKategori;

        public KategoriViewHolder(View itemView) {
            super(itemView);
            tvNamaKategori = (TextView) itemView.findViewById(R.id.tvNamaKategori);
        }
    }
}

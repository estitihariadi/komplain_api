package com.iqbalfahrul.com.administrasi.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
            public void onClick(final View v) {
                final CharSequence[] dialogitem = {"Edit Data", "Hapus Data"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setTitle("Opsi");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0 :
                                Intent i = new Intent(v.getContext(), EditKategori.class);
                                i.putExtra("id_kategori", listKategori.get(position).getId_kategori());
                                i.putExtra("nama_kategori", listKategori.get(position).getNama_kategori());
                                v.getContext().startActivity(i);
                                break;
                            case 1 :
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
                                                Log.d("Delete Kategori",response.body().getStatus());
                                                if (response.body().getStatus().equals("success")){
                                                    Toast.makeText(v.getContext(),"Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                                                    //refresh kategori adapter
                                                    ((ListKategori)context).TampilData();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GetKategori> call, Throwable t) {
                                                Log.d("Delete Kategori",t.getMessage());
                                            }
                                        });
                                    }
                                });
                                AlertDialog d = alertDialog.create();
                                d.show();
                                break;
                        }
                    }
                });
                builder.create().show();
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

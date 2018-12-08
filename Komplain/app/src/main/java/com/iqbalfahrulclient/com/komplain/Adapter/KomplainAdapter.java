package com.iqbalfahrulclient.com.komplain.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqbalfahrulclient.com.komplain.DetailKomplain;
import com.iqbalfahrulclient.com.komplain.Model.Komplain;
import com.iqbalfahrulclient.com.komplain.R;
import com.iqbalfahrulclient.com.komplain.Rest.ApiClient;
import com.iqbalfahrulclient.com.komplain.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KomplainAdapter extends RecyclerView.Adapter<KomplainAdapter.KomplainViewHolder> {

    List<Komplain> listKomplain;
    Context context;

    public KomplainAdapter(List<Komplain> listKomplain, Context context) {
        this.listKomplain = listKomplain;
        this.context = context;
    }

    @Override
    public KomplainAdapter.KomplainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komplain, parent, false);
        KomplainViewHolder mHolder = new KomplainViewHolder((view));
        return mHolder;
    }

    @Override
    public void onBindViewHolder(KomplainAdapter.KomplainViewHolder holder, final int position) {
        final ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        holder.tvtgl.setText(listKomplain.get(position).getTgl_komplain());
        holder.tvJudulKomplain.setText(listKomplain.get(position).getJudul());
        holder.status.setText(listKomplain.get(position).getStatus());
        Picasso.with(context).load(ApiClient.LOAD_URL + listKomplain.get(position).getFoto()).into(holder.ivfoto2);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(v.getContext(), DetailKomplain.class);
                i.putExtra("id_komplain", listKomplain.get(position).getId_komplain());
                i.putExtra("nim", listKomplain.get(position).getNim());
                i.putExtra("id_pegawai", listKomplain.get(position).getId_pegawai());
                i.putExtra("judul", listKomplain.get(position).getJudul());
                i.putExtra("id_kategori", listKomplain.get(position).getId_kategori());
                i.putExtra("foto", listKomplain.get(position).getFoto());
                i.putExtra("foto_after", listKomplain.get(position).getFoto_after());
                i.putExtra("keluhan", listKomplain.get(position).getKeluhan());
                i.putExtra("lokasi", listKomplain.get(position).getLokasi());
                i.putExtra("tgl_komplain", listKomplain.get(position).getTgl_komplain());
                i.putExtra("status", listKomplain.get(position).getStatus());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKomplain.size();
    }

    public class KomplainViewHolder extends RecyclerView.ViewHolder {
        TextView tvtgl, tvJudulKomplain,status;
        ImageView ivfoto2;

        public KomplainViewHolder(View itemView) {
            super(itemView);
            tvtgl = (TextView) itemView.findViewById(R.id.tvTanggalKomplain);
            tvJudulKomplain= (TextView) itemView.findViewById(R.id.tvJudulKomplain);
            status = (TextView) itemView.findViewById(R.id.tvStatus);
            ivfoto2 = (ImageView) itemView.findViewById(R.id.ivFoto2);

        }
    }
}

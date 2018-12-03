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

import com.iqbalfahrul.com.administrasi.EditAdmin;
import com.iqbalfahrul.com.administrasi.ListAdmin;
import com.iqbalfahrul.com.administrasi.Model.GetAdmin;
import com.iqbalfahrul.com.administrasi.Model.Admin;
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

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    List<Admin> listAdmin;
    Context context;

    public AdminAdapter(List<Admin> listAdmin,Context context) {
        this.listAdmin = listAdmin;
        this.context = context;
    }

    @Override
    public AdminAdapter.AdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_admin, parent, false);
        AdminViewHolder mHolder = new AdminViewHolder((view));
        return mHolder;
    }

    @Override
    public void onBindViewHolder(AdminAdapter.AdminViewHolder holder, final int position) {
        final ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        holder.tvNamaAdmin.setText(listAdmin.get(position).getNama_admin());
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
                                Intent i = new Intent(v.getContext(), EditAdmin.class);
                                i.putExtra("id_admin", listAdmin.get(position).getId_admin());
                                i.putExtra("nama_admin", listAdmin.get(position).getNama_admin());
                                i.putExtra("username_admin", listAdmin.get(position).getUsername_admin());
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
                                        RequestBody reqIdAdmin =
                                                MultipartBody.create(MediaType.parse("multipart/form-data"),
                                                        (listAdmin.get(position).getId_admin().isEmpty())?
                                                                "" : listAdmin.get(position).getId_admin());
                                        RequestBody reqAction =
                                                MultipartBody.create(MediaType.parse("multipart/form-data"), "delete");

                                        Call<GetAdmin> callDelete = mApiInterface.deleteAdmin(reqIdAdmin,reqAction);
                                        callDelete.enqueue(new Callback<GetAdmin>() {
                                            @Override
                                            public void onResponse(Call<GetAdmin> call, Response<GetAdmin> response) {
                                                notifyDataSetChanged();
                                                Log.d("Delete Admin",response.body().getStatus());
                                                if (response.body().getStatus().equals("success")){
                                                    Toast.makeText(v.getContext(),"Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                                                    //refresh admin adapter
                                                    ((ListAdmin)context).TampilData();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GetAdmin> call, Throwable t) {
                                                Log.d("Delete Admin",t.getMessage());
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
        return listAdmin.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaAdmin;

        public AdminViewHolder(View itemView) {
            super(itemView);
            tvNamaAdmin = (TextView) itemView.findViewById(R.id.tvNamaAdmin);
        }
    }
}

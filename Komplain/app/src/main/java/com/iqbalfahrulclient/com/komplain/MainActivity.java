package com.iqbalfahrulclient.com.komplain;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iqbalfahrulclient.com.komplain.Adapter.MySpinnerAdapter;
import com.iqbalfahrulclient.com.komplain.Model.GetKategori;
import com.iqbalfahrulclient.com.komplain.Model.GetKomplain;
import com.iqbalfahrulclient.com.komplain.Model.Kategori;
import com.iqbalfahrulclient.com.komplain.Rest.ApiClient;
import com.iqbalfahrulclient.com.komplain.Rest.ApiInterface;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends OpsiMenu {
    Context mContext;
    ImageView ivFoto;
    EditText etJudul, etKeluhan, etLokasi;
    Button btnKirim, btnLewati, btnFoto;
    ApiInterface mApiKategori;
    Spinner sKategori;
    String id_Kategori;
    Intent i;
    String pathImage="";
    Intent mIntent;
    String nim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mIntent = getIntent();

        nim = mIntent.getStringExtra("username");

        ivFoto = (ImageView) findViewById(R.id.ivFotoKomplain);
        etJudul = (EditText) findViewById(R.id.etJudul);
        etKeluhan = (EditText) findViewById(R.id.etKeluhan);
        etLokasi = (EditText) findViewById(R.id.etLokasi);
        sKategori = (Spinner) findViewById(R.id.spinnerKategori);

        btnFoto = (Button) findViewById(R.id.btnFoto);
        btnKirim = (Button) findViewById(R.id.btnTambahKomplain);
        btnLewati = (Button) findViewById(R.id.btnSkip);

        Glide.with(mContext).load(R.drawable.default_user).into(ivFoto);

        initKategori();

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                MultipartBody.Part body = null;

                if (!pathImage.isEmpty()){
                    // Buat file dari image yang dipilih
                    File file = new File(pathImage);

                    // Buat RequestBody instance dari file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

                    // MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("foto", file.getName(),
                            requestFile);
                }
                RequestBody reqNIM = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (nim.isEmpty())?"":nim);
                RequestBody reqJudul = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (etJudul.getText().toString().isEmpty())?"":etJudul.getText().toString());
                RequestBody reqKeluhan = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (etKeluhan.getText().toString().isEmpty())?"":etKeluhan.getText().toString());
                RequestBody reqLokasi = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (etLokasi.getText().toString().isEmpty())?"":etLokasi.getText().toString());
                RequestBody reqIdKategori = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (id_Kategori.isEmpty())?"":id_Kategori);
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");

                Call<GetKomplain> mKomplainCall = mApiInterface.postInsertKomplain(body, reqNIM,
                        reqJudul, reqKeluhan, reqLokasi, reqIdKategori, reqAction);
                mKomplainCall.enqueue(new Callback<GetKomplain>() {
                    @Override
                    public void onResponse(Call<GetKomplain> call, Response<GetKomplain> response) {
                        Log.d("Insert Komplain", response.body().getMessage().toString());
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(mContext,
                                    "Komplain Insert \n Status = "+response.body().getStatus()+
                                            "\n"+"Message = "+response.body().getMessage()+"\n",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }else{
                            Toast.makeText(mContext,
                                    "Komplain Anda Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                                    .show();
                            openListKategori();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKomplain> call, Throwable t) {
                        Log.e("Insert Komplain", t.getMessage());
                        Toast.makeText(mContext,
                                "Insert Error : "+t.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });

        btnLewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListKategori();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mintaPermissions();
            }
        });
    }

    private void openListKategori(){
        Intent mIntent = new Intent(mContext, ListKomplain.class);
        mContext.startActivity(mIntent);
    }

    private void initKategori(){
        mApiKategori = ApiClient.getClient().create(ApiInterface.class);
        Call<GetKategori> mKategoriCall = mApiKategori.getKategori();
        (mKategoriCall).enqueue(new Callback<GetKategori>() {
            @Override
            public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                if (response.body().getStatus().equals("failed")){
                    Log.e("Spinner Kategori : ", response.message());
                }else {
                    List<Kategori> sKategoriItems = response.body().getResult();

                    Kategori object[] = new  Kategori[sKategoriItems.size()];

                    for (int i = 0; i < sKategoriItems.size(); i++){
                        object[i] = new Kategori(sKategoriItems.get(i).getId_kategori(), sKategoriItems.get(i).getNama_kategori());
                    }

                    MySpinnerAdapter adapter2 =
                            new MySpinnerAdapter(MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,object);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sKategori.setAdapter(adapter2);
                    sKategori.setOnItemSelectedListener(onItemSelectedListener2);
                }
            }

            @Override
            public void onFailure(Call<GetKategori> call, Throwable t) {
                Log.d("Get Kategori",t.getMessage());
            }
        });
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener2 =
            new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Kategori obj = (Kategori) (parent.getItemAtPosition(position));
                    id_Kategori = String.valueOf(obj.getId_kategori());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };

    private void mintaPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // Cek apakah semua permission yang diperlukan sudah diijinkan
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(),
                                    "Semua permissions diijinkan!", Toast.LENGTH_SHORT).show();
                            tampilkanFotoDialog();
                        }

                        // Cek apakah ada permission yang tidak diijinkan
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // Info user untuk mengubah setting permission
                            tampilkanSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(
                            List<com.karumi.dexter.listener.PermissionRequest> permissions,
                            PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Jika tidak ada image yang dipilih maka return
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        // Jika request berasal dari Gallery
        if (requestCode == 13) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    pathImage = simpanImage(bitmap);
                    Toast.makeText(mContext, "Foto berhasil di-load!", Toast.LENGTH_SHORT).show();

                    Glide.with(mContext).load(new File(pathImage)).into(ivFoto);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Foto gagal di-load!", Toast.LENGTH_SHORT).show();
                }
            }

            // Jika request dari Camera
        } else if (requestCode == 16) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            pathImage = simpanImage(thumbnail);
            Toast.makeText(mContext, "Foto berhasil di-load dari Camera!", Toast.LENGTH_SHORT)
                    .show();

            Glide.with(mContext).load(new File(pathImage)).into(ivFoto);
        }
    }

    private void tampilkanFotoDialog(){
        AlertDialog.Builder fotoDialog = new AlertDialog.Builder(this);
        fotoDialog.setTitle("Select Action");

        // Isi opsi dialog
        String[] fotoDialogItems = {
                "Pilih foto dari gallery",
                "Ambil dari kamera" };

        fotoDialog.setItems(fotoDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pilihan) {
                        switch (pilihan) {
                            case 0:
                                pilihDariGallery();
                                break;
                            case 1:
                                ambilDariCamera();
                                break;
                        }
                    }
                });
        fotoDialog.show();
    }

    public void pilihDariGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 13);
    }

    private void ambilDariCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, 16);
    }

    public String simpanImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/praktikum");

        // Buat direktori untuk penyimpanan
        if (!lokasiImage.exists()) {
            lokasiImage.mkdirs();
        }

        try {
            // Untuk penamaan file
            File f = new File(lokasiImage, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();

            // Operasi file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            Log.d("PRAKTIKUM", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            Log.d("PRAKTIKUM", "erroraaaaa");
            e1.printStackTrace();
        }
        return "";
    }

    // Memberi peringatan butuh permission
    private void tampilkanSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Butuh Permission");
        builder.setMessage("Aplikasi ini membutuhkan permission khusus untuk menjalankan aplikasi.");
        builder.setPositiveButton("BUKA SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                bukaSettings();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // Membuka layar Settings Android
    private void bukaSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Toast.makeText(this.mContext,"Tidak Boleh Kembali, silahkan gunakan opsi menu", Toast.LENGTH_LONG).show();
    }
}

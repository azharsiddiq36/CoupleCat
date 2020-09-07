package com.azhar.couplecat.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.azhar.couplecat.Model.ResponsePengguna;
import com.azhar.couplecat.Model.ResponseStore;
import com.azhar.couplecat.Model.ResponseToko;
import com.azhar.couplecat.Model.Toko;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class AddMyStore extends AppCompatActivity {
    SessionManager sessionManager;
    HashMap<String, String> map;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.ivBgStore)
    ImageView ivBgStore;
    @BindView(R.id.etNama)
    EditText etNama;
    @BindView(R.id.etNomor)
    EditText etNomor;
    @BindView(R.id.etDeskripsi)
    EditText etDeskripsi;
    @BindView(R.id.etAlamat)
    EditText etAlamat;
    final int REQUEST_GALLERY = 9544;
    String toko_id, latitude, longitude, provinsi, foto, partimage, kecamatan, desa, kabupaten;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_store);
        ButterKnife.bind(this);
        coupleCatInterface = CombineApi.getApiService();
        sessionManager = new SessionManager(AddMyStore.this);
        map = sessionManager.getDetailsLoggin();
        loadCurrentLocation();
        loadToko();
    }

    private void loadCurrentLocation() {
        LocationManager locationManager;
        String svcName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(svcName);
        String provider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            
            return;
        }
        Location l = locationManager.getLastKnownLocation(provider);
        updateLocation(l);
    }
    private void updateLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(AddMyStore.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            provinsi = addresses.get(0).getAdminArea();
            kabupaten = addresses.get(0).getSubAdminArea();
            kecamatan = addresses.get(0).getLocality();
            desa = addresses.get(0).getSubLocality();
            latitude = String.valueOf(addresses.get(0).getLatitude());
            longitude = String.valueOf(addresses.get(0).getLongitude());
            if (addresses.get(0).getLocality()!=null){

            }

        }catch(Exception e)
        {

        }
    }

    private void loadToko() {
        Call<ResponseStore> responseStoreCall = coupleCatInterface.getToko(map.get(sessionManager.KEY_PENGGUNA_ID));
        responseStoreCall.enqueue(new Callback<ResponseStore>() {
            @Override
            public void onResponse(Call<ResponseStore> call, Response<ResponseStore> response) {
                if (response.body().getStatus() == 200) {
                    foto = response.body().getData().getPenggunaFoto();
                    toko_id = response.body().getData().getTokoId();
                    etNama.setText(response.body().getData().getTokoNama());
                    etAlamat.setText(response.body().getData().getTokoAlamat());
                    etDeskripsi.setText(response.body().getData().getTokoDeskripsi());
                    etNomor.setText(response.body().getData().getTokoNomor());
                    Picasso.get()
                            .load(img_url + response.body().getData().getTokoFoto())
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .error(android.R.drawable.sym_def_app_icon)
                            .into(ivBgStore);
                } else if (response.body().getStatus() == 403) {
                    toko_id = null;
                }
            }

            @Override
            public void onFailure(Call<ResponseStore> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btnSubmit)
    protected void btnSubmit(View view) {
        String TAG = "kambing";
        Log.d(TAG, "btnSubmit: "+toko_id);
        if (toko_id == null){
            doInputData();
        }
        else{
            doUpdateData();
        }

    }
    private void doInputData(){

        int skor = 0;
        String id = map.get(sessionManager.KEY_PENGGUNA_ID);
        String nama = etNama.getText().toString();
        String nomor = etNomor.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        String alamat = etAlamat.getText().toString();
        if (nama.equals("")) {
            skor += 1;
        }
        if (nomor.equals("")) {
            skor += 1;
        }
        if (alamat.equals("")) {
            skor += 1;
        }
        if (deskripsi.equals("")) {
            skor += 1;
        }
        if (partimage.equals("")){
            skor+=1;
        }
        if (skor > 0) {
            Toast.makeText(this, "Harap Periksa Kembali, Masih Ada Form yang Kosong", Toast.LENGTH_SHORT).show();
        } else {

                final File imageFile = new File(partimage);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
                MultipartBody.Part partGambar = MultipartBody.Part.createFormData("foto", imageFile.getName(), requestBody);
                RequestBody pengguna_id = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + toko_id);
                RequestBody pengguna_nama = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + nama);
                RequestBody toko_alamat = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + alamat);
                RequestBody toko_nomor = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + nomor);
                RequestBody toko_deskripsi = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + deskripsi);
                RequestBody toko_kecamatan = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + kecamatan);
                RequestBody toko_desa = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + desa);
                RequestBody toko_kabupaten = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + kabupaten);
                RequestBody toko_provinsi = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + provinsi);
                RequestBody toko_longitude = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + longitude);
                RequestBody toko_latitude = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + latitude);
                coupleCatInterface.addToko(partGambar, pengguna_id, pengguna_nama, toko_latitude, toko_longitude, toko_provinsi, toko_kabupaten, toko_kecamatan, toko_desa, toko_alamat, toko_deskripsi, toko_nomor).enqueue(new Callback<ResponseToko>() {
                    @Override
                    public void onResponse(Call<ResponseToko> call, Response<ResponseToko> response) {
                        Toast.makeText(AddMyStore.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ResponseToko> call, Throwable t) {

                    }
                });
            }
    }

    private void doUpdateData() {
        int skor = 0;
        String id = map.get(sessionManager.KEY_PENGGUNA_ID);
        String nama = etNama.getText().toString();
        String nomor = etNomor.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        String alamat = etAlamat.getText().toString();
        if (nama.equals("")) {
            skor += 1;
        }
        if (nomor.equals("")) {
            skor += 1;
        }
        if (alamat.equals("")) {
            skor += 1;
        }
        if (deskripsi.equals("")) {
            skor += 1;
        }
        if (skor > 0) {
            Toast.makeText(this, "Harap Periksa Kembali, Masih Ada Form yang Kosong", Toast.LENGTH_SHORT).show();
        } else {
            if (partimage != null) {
                final File imageFile = new File(partimage);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
                MultipartBody.Part partGambar = MultipartBody.Part.createFormData("foto", imageFile.getName(), requestBody);
                RequestBody pengguna_id = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + toko_id);
                RequestBody pengguna_nama = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + nama);
                RequestBody toko_alamat = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + alamat);
                RequestBody toko_nomor = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + nomor);
                RequestBody toko_deskripsi = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + deskripsi);
                RequestBody toko_kecamatan = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + kecamatan);
                RequestBody toko_desa = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + desa);
                RequestBody toko_kabupaten = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + kabupaten);
                RequestBody toko_provinsi = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + provinsi);
                RequestBody toko_longitude = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + longitude);
                RequestBody toko_latitude = RequestBody.create(
                        MediaType.parse("text/plain"),
                        "" + latitude);
                coupleCatInterface.updateToko(partGambar, pengguna_id, pengguna_nama, toko_latitude, toko_longitude, toko_provinsi, toko_kabupaten, toko_kecamatan, toko_desa, toko_alamat, toko_deskripsi, toko_nomor).enqueue(new Callback<ResponseToko>() {
                    @Override
                    public void onResponse(Call<ResponseToko> call, Response<ResponseToko> response) {
                        Toast.makeText(AddMyStore.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ResponseToko> call, Throwable t) {

                    }
                });
            } else {
                coupleCatInterface.updateToko1(toko_id, nama, latitude, longitude, provinsi, kabupaten, kecamatan, desa, alamat, deskripsi, nomor).enqueue(new Callback<ResponseToko>() {
                    @Override
                    public void onResponse(Call<ResponseToko> call, Response<ResponseToko> response) {
                        Toast.makeText(AddMyStore.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ResponseToko> call, Throwable t) {

                    }
                });

            }
        }
    }
    

    @OnClick(R.id.tvChangeStorePhoto)
    protected void tvChangeStorePhoto(View view) {
        doUpload();
    }

    private void doUpload() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "open gallery"), REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY) {
                Uri dataImage = data.getData();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataImage, imageprojection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    partimage = cursor.getString(indexImage);
                    cursor.close();
                    if (partimage != null) {
                        File image = new File(partimage);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(partimage);
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        ivBgStore.setImageBitmap(scaled);
                    }
                }
            }
        }
    }

    @OnClick(R.id.lyBack)
    protected void lyBack(View view) {
        onBackPressed();
    }
}

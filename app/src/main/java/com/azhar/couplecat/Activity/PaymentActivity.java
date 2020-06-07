package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Model.Contest;
import com.azhar.couplecat.Model.ResponsePembayaran;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    String TAG= "kambing";
    @BindView(R.id.tvJudul)
    TextView tvJudul;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.ivUpload)
    ImageView ivUpload;
    @BindView(R.id.lyRek)
    LinearLayout lyRek;
    @BindView(R.id.spJenis)
    Spinner spJenis;
    String judul,total,pemesanan, partimage,jenis,kontes;
    SessionManager sessionManager;
    HashMap<String, String> map;
    CoupleCatInterface coupleCatInterface;
    final int REQUEST_GALLERY = 9544;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        ButterKnife.bind(this);
        coupleCatInterface = CombineApi.getApiService();
        sessionManager = new SessionManager(PaymentActivity.this);
        map = sessionManager.getDetailsLoggin();
        kontes = getIntent().getStringExtra("kontes");
        judul = getIntent().getStringExtra("judul");
        total = getIntent().getStringExtra("total");
        pemesanan = getIntent().getStringExtra("id");
        Log.d(TAG, "onCreatesad: "+getIntent().getStringExtra("id"));
        tvJudul.setText(judul);
        tvTotal.setText("Rp. "+total);
    }
    @OnItemSelected(R.id.spJenis)
    void onItemSelected(int position){
        Log.d(TAG, "onItemSelected: "+position);
        if (position == 0){
            ivUpload.setVisibility(View.GONE);
            lyRek.setVisibility(View.GONE);
        }
        else if(position == 1){
            jenis = "saldo";
            ivUpload.setVisibility(View.GONE);
            lyRek.setVisibility(View.VISIBLE);
        }
        else{
            jenis = "resi";
            ivUpload.setVisibility(View.VISIBLE);
            lyRek.setVisibility(View.VISIBLE);
        }
    }
    @OnClick(R.id.btnPesan)
    protected void btnPesan(View view){
        Log.d(TAG, "btnPesan: "+jenis);
        if (jenis.equals("saldo")){
            payWithSaldo();
        }
        else{
            payWithUpload();
        }
    }

    private void payWithUpload() {
        Log.d(TAG, "payWithUpload: masuk");
        final File imageFile = new File(partimage);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
        MultipartBody.Part partGambar = MultipartBody.Part.createFormData("foto",imageFile.getName(),requestBody);
        RequestBody pengguna_id = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+map.get(sessionManager.KEY_PENGGUNA_ID));
        RequestBody knts = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+kontes);
        RequestBody pmsn = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+pemesanan);
        RequestBody jns = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+jenis);
        RequestBody jlh = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+total);
        Call<ResponsePembayaran> responsePembayaranCall = coupleCatInterface.addUploadPembayaran(partGambar,
                pmsn,
                pengguna_id,
                jlh,
                jns,
                knts);
        responsePembayaranCall.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran> response) {

                if (response.body().getStatus() == 200){
                    Toast.makeText(PaymentActivity.this, "Menunggu Persetujuan dari Admin", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PaymentActivity.this,Contest.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(PaymentActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    private void payWithSaldo() {
        Call<ResponsePembayaran> responsePembayaranCall = coupleCatInterface.addPembayaran(pemesanan,
                map.get(sessionManager.KEY_PENGGUNA_ID),
                        total,
                        jenis,
                        kontes);
        responsePembayaranCall.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran> response) {
                if (response.body().getStatus() == 200){
                    Toast.makeText(PaymentActivity.this, "Tiket Berhasil di Beli", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PaymentActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    @OnClick(R.id.ivUpload)
    protected void ivUpload(View view){
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
                String selected = dataImage.toString();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataImage, imageprojection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    partimage = cursor.getString(indexImage);
                    cursor.close();
                    if (partimage != null) {
                        Picasso.get().load(selected).noPlaceholder().centerCrop().fit().into(ivUpload);
//                        File image = new File(partimage);
//                        Picasso.get().load(image).fit().centerCrop().into(ivUpload);
//                        Bitmap bitmapImage = BitmapFactory.decodeFile(partimage);
////                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
//                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, (int) (bitmapImage.getWidth()*0.5), (int) (bitmapImage.getHeight()*0.5), true);
//                        ivUpload.setImageBitmap(scaled);
                    }
                }
            }
        }
    }


}

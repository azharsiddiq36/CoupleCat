package com.azhar.couplecat.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.azhar.couplecat.Model.ResponseSaldo;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSaldoActivity extends AppCompatActivity {
    @BindView(R.id.imageView4)
    ImageView foto;
    @BindView(R.id.etJumlah)
    EditText etJumlah;
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    HashMap<String, String> map;
    final int REQUEST_GALLERY = 9544;
    String partimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saldo);
        ButterKnife.bind(this);
        if (ActivityCompat.checkSelfPermission(AddSaldoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddSaldoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
        }
        sessionManager = new SessionManager(AddSaldoActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();

    }
    @OnClick(R.id.btnSubmit)
    protected void btnSubmit(View view){
        final File imageFile = new File(partimage);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
        MultipartBody.Part partGambar = MultipartBody.Part.createFormData("foto",imageFile.getName(),requestBody);
        RequestBody pengguna_id = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+map.get(sessionManager.KEY_PENGGUNA_ID));
        RequestBody jumlah = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etJumlah.getText().toString());
        Call<ResponseSaldo> responseSaldoCall = coupleCatInterface.addSaldo(partGambar,pengguna_id,jumlah);
        responseSaldoCall.enqueue(new Callback<ResponseSaldo>() {
            @Override
            public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                if (response.body().getStatus().equals("200")){
                    Toast.makeText(AddSaldoActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                }
                else{
                    Toast.makeText(AddSaldoActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSaldo> call, Throwable t) {

            }
        });

    }
    @OnClick(R.id.imageView4)
    protected void imageView4(View view){
        doUpload();
    }

    private void doUpload() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,"open gallery"),REQUEST_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode ==REQUEST_GALLERY){
                Uri dataImage = data.getData();
                String selected = dataImage.toString();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataImage,imageprojection,null,null,null);
                if (cursor !=null ){
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    partimage = cursor.getString(indexImage);
                    cursor.close();
                    if (partimage != null){
                        Picasso.get().load(selected).noPlaceholder().centerCrop().fit().into(foto);
//                        File image = new File(partimage);
//                        Bitmap bitmapImage = BitmapFactory.decodeFile(partimage);
//                        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
//                        ivPrevUpload.setImageBitmap(scaled);
                    }
                }
            }
        }
    }
}

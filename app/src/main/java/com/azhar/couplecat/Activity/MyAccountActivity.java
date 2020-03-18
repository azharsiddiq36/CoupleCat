package com.azhar.couplecat.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.se.omapi.Session;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Model.ResponsePengguna;
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

import static com.azhar.couplecat.Rest.CombineApi.img_url;

public class MyAccountActivity extends AppCompatActivity {
    @BindView(R.id.ivFoto)
    ImageView ivFoto;
    @BindView(R.id.tvChangeFotoProfile)
    TextView tvChangeFotoProfile;
    @BindView(R.id.etNama)
    EditText etNama;
    @BindView(R.id.etNomor)
    EditText etNomor;
    @BindView(R.id.etEmail)
    EditText etEmail;
    SessionManager sessionManager;
    HashMap<String,String> map;
    CoupleCatInterface coupleCatInterface;
    final int REQUEST_GALLERY = 9544;
    String partimage;
    ProgressDialog pd;
    String TAG = "FIX";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        if (ActivityCompat.checkSelfPermission(MyAccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyAccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
        }
        pd = new ProgressDialog(this);
        sessionManager = new SessionManager(getApplicationContext());
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        initData();

    }

    @OnClick(R.id.lyBack)
    public void lyBack(View view){
        onBackPressed();
    }
    @OnClick (R.id.tvChangeFotoProfile)
    public void tvChangeFotoProfile(View view){
        doUpload();
    }

    @OnClick(R.id.btnUpdate)
    public void lyUpdate(View view){
        doUpdate();
    }

    private void doUpdate() {
        int skor = 0;
        String id = map.get(sessionManager.KEY_PENGGUNA_ID);
        String nama = etNama.getText().toString();
        String nomor = etNomor.getText().toString();
        String email = etEmail.getText().toString();
        if (nama.equals("")){
            skor+=1;
        }
        if (nomor.equals("")){
            skor+=1;
        }
        if (email.equals("")){
            skor+=1;
        }
        if (skor > 0){
            Toast.makeText(this, "Harap Periksa Kembali, Masih Ada Form yang Kosong", Toast.LENGTH_SHORT).show();
        }
        else{
            if (partimage.equals(map.get(sessionManager.KEY_PENGGUNA_FOTO))){
                coupleCatInterface.updateProfile1(id,nama,nomor,email).enqueue(new Callback<ResponsePengguna>() {
                    @Override
                    public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                        Toast.makeText(MyAccountActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent gotomain = new Intent(MyAccountActivity.this,MainActivity.class);
                        updateSession();
                        startActivity(gotomain);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponsePengguna> call, Throwable t) {

                    }
                });
            }
            else{
                final File imageFile = new File(partimage);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
                MultipartBody.Part partGambar = MultipartBody.Part.createFormData("foto",imageFile.getName(),requestBody);
                RequestBody pengguna_id = RequestBody.create(
                        MediaType.parse("text/plain"),
                        ""+id);
                RequestBody pengguna_nama = RequestBody.create(
                        MediaType.parse("text/plain"),
                        ""+nama);
                RequestBody pengguna_email = RequestBody.create(
                        MediaType.parse("text/plain"),
                        ""+email);
                RequestBody pengguna_nomor = RequestBody.create(
                        MediaType.parse("text/plain"),
                        ""+nomor);
                coupleCatInterface.updateProfile2(partGambar,pengguna_id,pengguna_nomor,pengguna_email,pengguna_nama).enqueue(new Callback<ResponsePengguna>() {
                    @Override
                    public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                        Toast.makeText(MyAccountActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        updateSession();
                        Intent gotomain = new Intent(MyAccountActivity.this,MainActivity.class);
                        startActivity(gotomain);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponsePengguna> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void initData() {
        coupleCatInterface.detailAccount(map.get(sessionManager.KEY_PENGGUNA_ID)).enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                int status = response.body().getStatus();
                if (status == 200){
                    etNama.setText(response.body().getData().getPenggunaNama());
                    etEmail.setText(response.body().getData().getPenggunaEmail());
                    if (response.body().getData().getPenggunaNomor().equals("belum")){
                        etNomor.setHint("Inputkan Nomor Anda");
                    }
                    else{
                        etNomor.setText(response.body().getData().getPenggunaNomor());
                    }
                    partimage = response.body().getData().getPenggunaFoto();
                    getFoto(response.body().getData().getPenggunaFoto());

                }
                else{
                    Toast.makeText(MyAccountActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {

            }
        });
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
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataImage,imageprojection,null,null,null);
                if (cursor !=null ){
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    partimage = cursor.getString(indexImage);
                    cursor.close();
                    if (partimage != null){
                        File image = new File(partimage);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(partimage);
                        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        ivFoto.setImageBitmap(scaled);
                    }
                }
            }
        }
    }
    private void getFoto(String foto) {
        if (partimage.equals("belum")){
            Picasso.get()
                    .load(R.drawable.bg_kucing_1)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(ivFoto);
        }
        else{
            Picasso.get()
                    .load(img_url+foto)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(ivFoto);
        }

    }
    private void updateSession(){
        String id = map.get(sessionManager.KEY_PENGGUNA_ID);
        String username = map.get(sessionManager.KEY_PENGGUNA_USERNAME);
        String email = etEmail.getText().toString();
        String foto = partimage;
        String jk = map.get(sessionManager.KEY_PENGGUNA_JENIS_KELAMIN);
        String saldo = map.get(sessionManager.KEY_PENGGUNA_SALDO);
        String nama = etNama.getText().toString();
        String hak_akses = map.get(sessionManager.KEY_PENGGUNA_HAK_AKSES);
        String nomor = etNomor.getText().toString();
        sessionManager.saveLogin(id,username,email,foto,jk,saldo,nama,hak_akses,nomor);
    }
}

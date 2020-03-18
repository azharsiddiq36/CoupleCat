package com.azhar.couplecat.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.azhar.couplecat.Adapter.TheCatApiAdapter;
import com.azhar.couplecat.Model.Information;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Rest.TheCatApiInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
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

public class AddMyCatActivity extends AppCompatActivity {
    String TAG = "Kambing";
    @BindView(R.id.ivFoto)
    ImageView ivFoto;
    @BindView(R.id.etNama)
    EditText etNama;
    @BindView(R.id.rbBetina)
    RadioButton rbBetina;
    @BindView(R.id.rbJantan)
    RadioButton rbJantan;
    @BindView(R.id.spCat)
    Spinner spCat;
    String [] spCatList;
    ArrayList<Information> listCat = new ArrayList<>();
    TheCatApiInterface theCatApiInterface;
    CoupleCatInterface coupleCatInterface;
    ArrayList theCatApi = new ArrayList<>();
    SessionManager sessionManager;
    HashMap<String,String> map;
    final int REQUEST_GALLERY = 9544;
    String partimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_cat);
        ButterKnife.bind(this);
        if (ActivityCompat.checkSelfPermission(AddMyCatActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMyCatActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
        }
        sessionManager = new SessionManager(AddMyCatActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        
    }
    @OnClick(R.id.btnLoadPicture)
    public void btnLoadPicture(View view){
        doUpload();
    }
    @OnClick(R.id.lyBack)
    public void lyBack(View view){
        onBackPressed();
    }
    @OnClick(R.id.btnAdd)
    public void btnAdd(View view){
        int skor = 0;
        String id = map.get(sessionManager.KEY_PENGGUNA_ID);
        String nama = etNama.getText().toString();
        String jk = "";
        if (rbBetina.isChecked()){
            jk = "betina";
        }
        else{
            jk = "jantan";
        }
        String jenis = spCat.getSelectedItem().toString();
        if (nama.equals("")){
            skor+=1;
        }
        if (skor > 0){
            Toast.makeText(this, "Harap diisi semua", Toast.LENGTH_SHORT).show();
        }
        else{
            final File imageFile = new File(partimage);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
            MultipartBody.Part partGambar = MultipartBody.Part.createFormData("foto",imageFile.getName(),requestBody);
            RequestBody pengguna_id = RequestBody.create(
                    MediaType.parse("text/plain"),
                    ""+id);
            RequestBody kucing_nama = RequestBody.create(
                    MediaType.parse("text/plain"),
                    ""+nama);
            RequestBody kucing_jenis = RequestBody.create(
                    MediaType.parse("text/plain"),
                    ""+jenis);
            RequestBody kucing_jk = RequestBody.create(
                    MediaType.parse("text/plain"),
                    ""+jk);
            Call<ResponseMyCat> myCatCall = coupleCatInterface.addMyCat(partGambar,
                    pengguna_id,
                    kucing_nama,
                    kucing_jenis,
                    kucing_jk);
            myCatCall.enqueue(new Callback<ResponseMyCat>() {
                @Override
                public void onResponse(Call<ResponseMyCat> call, Response<ResponseMyCat> response) {
                    Toast.makeText(AddMyCatActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent gotomycat = new Intent(AddMyCatActivity.this,MyCatActivity.class);
                    startActivity(gotomycat);
                    finish();
                }
                @Override
                public void onFailure(Call<ResponseMyCat> call, Throwable t) {
                    Toast.makeText(AddMyCatActivity.this, "Maaf Terjadi Kesalahan dari System", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
}

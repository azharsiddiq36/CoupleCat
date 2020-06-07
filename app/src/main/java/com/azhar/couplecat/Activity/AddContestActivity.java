package com.azhar.couplecat.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Model.ApiLocation;
import com.azhar.couplecat.Model.JenisKontes;
import com.azhar.couplecat.Model.ResponseContest;
import com.azhar.couplecat.Model.ResponseJenisKontes;
import com.azhar.couplecat.Model.ResponseLocation;
import com.azhar.couplecat.Model.UniqueCode;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.ApiClient;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Rest.LocationInterface;
import com.azhar.couplecat.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

public class AddContestActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    String date;
    SessionManager sessionManager;
    HashMap<String, String> map;
    final int REQUEST_GALLERY = 9544;
    String partimage;
    String TAG = "Kambing";
    CoupleCatInterface coupleCatInterface;
    HashMap<String,String> policy;
    @BindView(R.id.etJudul)
    EditText etJudul;
    @BindView(R.id.etDeskripsi)
    EditText etDeskripsi;
    @BindView(R.id.etPolicy)
    EditText etPolicy;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDynamic;
    @BindView(R.id.spJenis)
    Spinner spJenis;
    @BindView(R.id.spProvinsi)
    Spinner spProvinsi;
    @BindView(R.id.spKabupaten)
    Spinner spKabupaten;
    @BindView(R.id.spKecamatan)
    Spinner spKecamatan;
    @BindView(R.id.spDesa)
    Spinner spDesa;
    @BindView(R.id.etAlamat)
    EditText etAlamat;
    @BindView(R.id.tvTangal)
    TextView tvTanggal;
    @BindView(R.id.etKuota)
    EditText etKuota;
    @BindView(R.id.etDurasi)
    EditText etDurasi;
    @BindView(R.id.etBiaya)
    EditText etBiaya;
    @BindView(R.id.etNomor)
    EditText etNomor;
    @BindView(R.id.prevUpload)
    ImageView ivPrevUpload;
    HashMap<String,String> mapJenis;
    HashMap<String,String> mapProvinsi;
    HashMap<String,String> mapKabupaten;
    HashMap<String,String> mapDesa;
    HashMap<String,String> mapKecamatan;
    ArrayList<ApiLocation> provinsiArray = new ArrayList<>();
    ArrayList<ApiLocation> kabupatenArray = new ArrayList<>();
    ArrayList<ApiLocation> kecamatanArray = new ArrayList<>();
    ArrayList<ApiLocation> desaArray = new ArrayList<>();
    ArrayList<JenisKontes> jenisArray = new ArrayList<>();
    String [] listprovinsi,listkabupaten,listdesa,listkecamatan,listJenis;
    String kecamatan, kabupaten, provinsi, desa, jenis,token,dtl;
    CombineApi combineApi;
    LocationInterface locationInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contest);
        ButterKnife.bind(this);
        if (ActivityCompat.checkSelfPermission(AddContestActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddContestActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
        }
        sessionManager = new SessionManager(AddContestActivity.this);
        map = sessionManager.getDetailsLoggin();
        mapProvinsi = new HashMap<>();
        mapKabupaten = new HashMap<>();
        mapDesa = new HashMap<>();
        mapKecamatan = new HashMap<>();
        mapJenis = new HashMap<>();
        coupleCatInterface = CombineApi.getApiService();
        loadCodeApi();
        loadJenis();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        spKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadKecamatan(mapKabupaten.get(listkabupaten[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadDesa(mapKecamatan.get(listkecamatan[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadJenis() {
        Call<ResponseJenisKontes> responseJenisKontesCall = coupleCatInterface.getJenisKontes();
        responseJenisKontesCall.enqueue(new Callback<ResponseJenisKontes>() {
            @Override
            public void onResponse(Call<ResponseJenisKontes> call, Response<ResponseJenisKontes> response) {
//                jenisArray = (ArrayList<JenisKontes>) response.body().getData();
                Log.d(TAG, "onResponse"+response.body().getData().get(0).getJenisKontesNama());
                listJenis = new String[response.body().getData().size()];
                for (int i = 0; i<listJenis.length;i++){
                    listJenis[i] = response.body().getData().get(i).getJenisKontesNama();
                    mapJenis.put(response.body().getData().get(i).getJenisKontesNama(),response.body().getData().get(i).getJenisKontesId().toString());
                }
                final ArrayAdapter<String> jenis_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_contest,listJenis);
                spJenis.setAdapter(jenis_adapter);
            }
            @Override
            public void onFailure(Call<ResponseJenisKontes> call, Throwable t) {

            }
        });
    }

    private void loadCodeApi() {
        locationInterface = ApiClient.getClient().create(LocationInterface.class);
        Call<UniqueCode> call = locationInterface.getUniqueCode();
        
        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
                token = "MeP7c5ne"+response.body().getUniqueCode();
                loadProvinsi(token);

            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
    }
    private void loadKabupaten(String s) {
        Call<ResponseLocation> dataCall = locationInterface.getKabupatenList(token,Integer.parseInt(s));
        dataCall.enqueue(new Callback<ResponseLocation>() {
            @Override
            public void onResponse(Call<ResponseLocation> call, Response<ResponseLocation> response) {
                if (response.isSuccessful()){
                    kabupatenArray = (ArrayList<ApiLocation>) response.body().getData();
                    listkabupaten = new String[kabupatenArray.size()];
                    for (int i = 0; i<listkabupaten.length;i++){
                        listkabupaten[i] = kabupatenArray.get(i).getName();
                        mapKabupaten.put(kabupatenArray.get(i).getName(),kabupatenArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> kabupaten_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listkabupaten);
                    spKabupaten.setAdapter(kabupaten_adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    kabupatenArray = (ArrayList<ApiLocation>) response.body().getData();
                    listkabupaten = new String[kabupatenArray.size()];
                    for (int i = 0; i<listkabupaten.length;i++){
                        listkabupaten[i] = kabupatenArray.get(i).getName();
                        mapKabupaten.put(kabupatenArray.get(i).getName(),kabupatenArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> kabupaten_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listkabupaten);
                    spKabupaten.setAdapter(kabupaten_adapter);
                }
                else{

                }

            }

            @Override
            public void onFailure(Call<ResponseLocation> call, Throwable t) {
                Log.d(TAG, "onFailureProv: "+t.toString());
            }
        });
    }
    private void loadKecamatan(String s) {
        Call<ResponseLocation> dataCall = locationInterface.getKecamatanList(token,Integer.parseInt(s));
        dataCall.enqueue(new Callback<ResponseLocation>() {
            @Override
            public void onResponse(Call<ResponseLocation> call, Response<ResponseLocation> response) {
                if (response.isSuccessful()){
                    kecamatanArray = (ArrayList<ApiLocation>) response.body().getData();
                    listkecamatan = new String[kecamatanArray.size()];
                    for (int i = 0; i<listkecamatan.length;i++){
                        listkecamatan[i] = kecamatanArray.get(i).getName();
                        mapKecamatan.put(kecamatanArray.get(i).getName(),kecamatanArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> kecamatan_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listkecamatan);
                    spKecamatan.setAdapter(kecamatan_adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    kecamatanArray = (ArrayList<ApiLocation>) response.body().getData();
                    listkecamatan = new String[kecamatanArray.size()];
                    for (int i = 0; i<listkecamatan.length;i++){
                        listkecamatan[i] = kecamatanArray.get(i).getName();
                        mapKecamatan.put(kecamatanArray.get(i).getName(),kecamatanArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> kecamatan_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listkecamatan);
                    spKecamatan.setAdapter(kecamatan_adapter);
                }
                else{

                }

            }

            @Override
            public void onFailure(Call<ResponseLocation> call, Throwable t) {
                Log.d(TAG, "onFailureProv: "+t.toString());
            }
        });
    }
    private void loadDesa(String s) {

        Call<ResponseLocation> dataCall = locationInterface.getKelurahanList(token,Integer.parseInt(s));
        dataCall.enqueue(new Callback<ResponseLocation>() {
            @Override
            public void onResponse(Call<ResponseLocation> call, Response<ResponseLocation> response) {
                if (response.isSuccessful()){
                    desaArray = (ArrayList<ApiLocation>) response.body().getData();
                    listdesa = new String[desaArray.size()];
                    for (int i = 0; i<listdesa.length;i++){
                        listdesa[i] = desaArray.get(i).getName();
                        mapDesa.put(desaArray.get(i).getName(),desaArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> desa_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listdesa);
                    spDesa.setAdapter(desa_adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    desaArray = (ArrayList<ApiLocation>) response.body().getData();
                    listdesa = new String[desaArray.size()];
                    for (int i = 0; i<listdesa.length;i++){
                        listdesa[i] = desaArray.get(i).getName();
                        mapDesa.put(desaArray.get(i).getName(),desaArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> desa_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listdesa);
                    spDesa.setAdapter(desa_adapter);
                }
                else{

                }

            }

            @Override
            public void onFailure(Call<ResponseLocation> call, Throwable t) {
                Log.d(TAG, "onFailureProv: "+t.toString());
            }
        });
    }
    @OnItemSelected(R.id.spProvinsi)
    void onItemSelected(int position){
        loadKabupaten(mapProvinsi.get(listprovinsi[position]));
    }
    private void loadProvinsi(String s) {
        Call<ResponseLocation> dataCall = locationInterface.getProvinceList(s);
        dataCall.enqueue(new Callback<ResponseLocation>() {
            @Override
            public void onResponse(Call<ResponseLocation> call, Response<ResponseLocation> response) {
                if (response.isSuccessful()){
                    provinsiArray = (ArrayList<ApiLocation>) response.body().getData();
                    listprovinsi = new String[provinsiArray.size()];
                    for (int i = 0; i<listprovinsi.length;i++){
                        listprovinsi[i] = provinsiArray.get(i).getName();
                        mapProvinsi.put(provinsiArray.get(i).getName(),provinsiArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> provinsi_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listprovinsi);
                    spProvinsi.setAdapter(provinsi_adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    provinsiArray = (ArrayList<ApiLocation>) response.body().getData();
                    listprovinsi = new String[provinsiArray.size()];
                    for (int i = 0; i<listprovinsi.length;i++){
                        listprovinsi[i] = provinsiArray.get(i).getName();
                        mapProvinsi.put(provinsiArray.get(i).getName(),provinsiArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> provinsi_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.sp_location,listprovinsi);
                    spProvinsi.setAdapter(provinsi_adapter);
                }
                else{

                }

            }

            @Override
            public void onFailure(Call<ResponseLocation> call, Throwable t) {
                Log.d(TAG, "onFailureProv: "+t.toString());
            }
        });

    }
    @OnClick(R.id.btnDaftar)
    protected void btnDaftar(View view){
        final File imageFile = new File(partimage);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
        MultipartBody.Part partGambar = MultipartBody.Part.createFormData("foto",imageFile.getName(),requestBody);
        RequestBody pengguna_id = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+map.get(sessionManager.KEY_PENGGUNA_ID));
        RequestBody judul = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etJudul.getText().toString());
        RequestBody plcy = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+getPolicy());
        RequestBody by = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etBiaya.getText().toString());
        RequestBody nmr = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etNomor.getText().toString());
        RequestBody drsi = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etDurasi.getText().toString());
        RequestBody kta = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etKuota.getText().toString());
        RequestBody deskr = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etDeskripsi.getText().toString());
        RequestBody almt = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+etAlamat.getText().toString());
        RequestBody jen = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+mapJenis.get(spJenis.getSelectedItem()));
        RequestBody  prov= RequestBody.create(
                MediaType.parse("text/plain"),
                ""+spProvinsi.getSelectedItem());
        RequestBody kec = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+spKecamatan.getSelectedItem());
        RequestBody kab = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+spKabupaten.getSelectedItem());
        RequestBody des  = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+spDesa.getSelectedItem());
        RequestBody tgl  = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+date);
        Call<ResponseContest> responseContestCall = coupleCatInterface.addContest(partGambar,
                pengguna_id,
                judul,
                prov,
                kab,
                kec,
                des,
                kta,
                drsi,
                by,
                almt,
                plcy,
                deskr,
                tgl,
                jen,
                nmr);
        responseContestCall.enqueue(new Callback<ResponseContest>() {
            @Override
            public void onResponse(Call<ResponseContest> call, Response<ResponseContest> response) {
                if (response.body().getStatus() == 200){
                    Toast.makeText(AddContestActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddContestActivity.this,ContestActivity.class);
                    startActivity(i);finish();
                }
                else{
                    Toast.makeText(AddContestActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onResponse: "+response.body().getStatus());
            }

            @Override
            public void onFailure(Call<ResponseContest> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    private String getPolicy() {
        dtl = "";
        int num = 1;
        for (Map.Entry loop : policy.entrySet()){
            dtl += num+". "+loop.getValue()+"\n";
        }
        return dtl;
    }

    @OnClick(R.id.lyBack)
    protected void lyBack(View view){
        onBackPressed();
    }
    @OnClick(R.id.btnPolicy)
    protected void btnPolicy(View view){
        if(etPolicy.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Syarat tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else{

            LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.row_policy, null);
            final TextView textView = (TextView)addView.findViewById(R.id.privacy);
            ImageView remove = (ImageView) addView.findViewById(R.id.btnRemove);
            textView.setText(String.valueOf(etPolicy.getText().toString()));
            policy = new HashMap<>();
            policy.put(textView.getText().toString(),textView.getText().toString());
            Toast.makeText(getApplicationContext(), "Syarat ditambahkan", Toast.LENGTH_SHORT).show();
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LinearLayout)addView.getParent()).removeView(addView);
                    policy.remove(textView.getText().toString());
                   
                }
            });

            lyDynamic.addView(addView);
            etPolicy.setText("");
        }    }
    @OnClick(R.id.ivCalendar)
    protected void ivCalendar(View view){
        showDateDialog();
    }
    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date =  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                String bulan = null;
                switch (String.valueOf(monthOfYear + 1)) {
                    case "1":
                        bulan = "Januari";
                        break;
                    case "2":
                        bulan = "Februari";
                        break;
                    case "3":
                        bulan = "Maret";
                        break;
                    case "4":
                        bulan = "April";
                        break;
                    case "5":
                        bulan = "Mei";
                        break;
                    case "6":
                        bulan = "Juni";
                        break;
                    case "7":
                        bulan = "Juli";
                        break;
                    case "8":
                        bulan = "Agustus";
                        break;
                    case "9":
                        bulan = "September";
                        break;
                    case "10":
                        bulan = "Oktober";
                        break;
                    case "11":
                        bulan = "November";
                        break;
                    case "12":
                        bulan = "Desember";
                        break;
                }
                tvTanggal.setText(dayOfMonth + " " + bulan + " " + year);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    @OnClick(R.id.btnUpload)
    protected void btnUpload(View view){
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
                        ivPrevUpload.setVisibility(View.VISIBLE);
                        Picasso.get().load(selected).noPlaceholder().centerCrop().fit().into(ivPrevUpload);
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

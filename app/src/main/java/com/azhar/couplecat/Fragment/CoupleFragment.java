package com.azhar.couplecat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Activity.AddCoupleActivity;
import com.azhar.couplecat.Activity.MainActivity;
import com.azhar.couplecat.Adapter.CoupleAdapter;
import com.azhar.couplecat.Model.ApiLocation;
import com.azhar.couplecat.Model.Couple;
import com.azhar.couplecat.Model.Data;
import com.azhar.couplecat.Model.Region;
import com.azhar.couplecat.Model.ResponseCouple;
import com.azhar.couplecat.Model.ResponseLocation;
import com.azhar.couplecat.Model.ResponsePasangan;
import com.azhar.couplecat.Model.UniqueCode;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.ApiClient;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Rest.LocationInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoupleFragment extends Fragment {
    SessionManager sessionManager;
    HashMap<String, String> map;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.rvCouple)
    RecyclerView rvCouple;
    @BindView(R.id.lyFilter)
    LinearLayout lyFilter;
    @BindView(R.id.lyOption)
    LinearLayout lyOption;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.spDesa)
    Spinner spDesa;
    @BindView(R.id.spKabupaten)
    Spinner spKabupaten;
    @BindView(R.id.spKecamatan)
    Spinner spKecamatan;
    @BindView(R.id.spProvinsi)
    Spinner spProvinsi;
    @BindView(R.id.spJenis)
    Spinner spJenis;
    LocationManager locationManager;
    String kecamatan, kabupaten, provinsi, desa, latitude, longitude, jenis;
    boolean status = false;
    String TAG = "Kambing";
    LocationInterface locationInterface;
    CoupleAdapter coupleAdapter;
    RecyclerView.LayoutManager layoutManager;
    HashMap<String,String> mapProvinsi;
    HashMap<String,String> mapKabupaten;
    HashMap<String,String> mapDesa;
    HashMap<String,String> mapKecamatan;
    CombineApi combineApi;
    String token;
    public String BASE_URL= "";
    ArrayList<ApiLocation> provinsiArray = new ArrayList<>();
    ArrayList<ApiLocation> kabupatenArray = new ArrayList<>();
    ArrayList<ApiLocation> kecamatanArray = new ArrayList<>();
    ArrayList<ApiLocation> desaArray = new ArrayList<>();

    String [] listprovinsi,listkabupaten,listdesa,listkecamatan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar =
                ((MainActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_overlay_splash));
        actionBar.setTitle("Pasangan");
        View view = inflater.inflate(R.layout.fragment_couple, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());
        coupleCatInterface = CombineApi.getApiService();
        map = sessionManager.getDetailsLoggin();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCouple.setLayoutManager(layoutManager);
        mapProvinsi = new HashMap<>();
        mapKabupaten = new HashMap<>();
        mapDesa = new HashMap<>();
        mapKecamatan = new HashMap<>();
        
        loadCodeApi();
        loadCurrentLocation();
        updateDB();

        loadData();
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

    private void loadCodeApi() {
        locationInterface = ApiClient.getClient().create(LocationInterface.class);
        Call<UniqueCode> call = locationInterface.getUniqueCode();
        Log.d(TAG, "loadCodeApi: tes");
        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
                Log.d(TAG, "onResponse: "+response.body().getUniqueCode());
                token = "MeP7c5ne"+response.body().getUniqueCode();
                loadProvinsi(token);

            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
    }

    private void loadDesa(String s) {
        Log.d(TAG, "loadDesa: "+s);
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
                    final ArrayAdapter<String> desa_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listdesa);
                    spDesa.setAdapter(desa_adapter);
                }
                else{
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    desaArray = (ArrayList<ApiLocation>) response.body().getData();
                    listdesa = new String[desaArray.size()];
                    for (int i = 0; i<listdesa.length;i++){
                        listdesa[i] = desaArray.get(i).getName();
                        mapDesa.put(desaArray.get(i).getName(),desaArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> desa_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listdesa);
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

    private void loadKecamatan(String s) {
        Log.d(TAG, "loadKecamatan: "+s);
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
                    final ArrayAdapter<String> kecamatan_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listkecamatan);
                    spKecamatan.setAdapter(kecamatan_adapter);
                }
                else{
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    kecamatanArray = (ArrayList<ApiLocation>) response.body().getData();
                    listkecamatan = new String[kecamatanArray.size()];
                    for (int i = 0; i<listkecamatan.length;i++){
                        listkecamatan[i] = kecamatanArray.get(i).getName();
                        mapKecamatan.put(kecamatanArray.get(i).getName(),kecamatanArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> kecamatan_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listkecamatan);
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

    @OnItemSelected(R.id.spProvinsi)
    void onItemSelected(int position){
        loadKabupaten(mapProvinsi.get(listprovinsi[position]));
    }

    private void loadKabupaten(String s) {
        Log.d(TAG, "loadKabupaten: "+s);
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
                    final ArrayAdapter<String> kabupaten_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listkabupaten);
                    spKabupaten.setAdapter(kabupaten_adapter);
                }
                else{
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    kabupatenArray = (ArrayList<ApiLocation>) response.body().getData();
                    listkabupaten = new String[kabupatenArray.size()];
                    for (int i = 0; i<listkabupaten.length;i++){
                        listkabupaten[i] = kabupatenArray.get(i).getName();
                        mapKabupaten.put(kabupatenArray.get(i).getName(),kabupatenArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> kabupaten_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listkabupaten);
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


    private void loadProvinsi(String s) {
        Log.d(TAG, "loadProvinsi: "+s);
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
                    final ArrayAdapter<String> provinsi_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listprovinsi);
                    spProvinsi.setAdapter(provinsi_adapter);
                }
                else{
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                if (response.body().getCode() == 200){
                    provinsiArray = (ArrayList<ApiLocation>) response.body().getData();
                    listprovinsi = new String[provinsiArray.size()];
                    for (int i = 0; i<listprovinsi.length;i++){
                        listprovinsi[i] = provinsiArray.get(i).getName();
                        mapProvinsi.put(provinsiArray.get(i).getName(),provinsiArray.get(i).getId().toString());
                    }
                    final ArrayAdapter<String> provinsi_adapter = new ArrayAdapter<>(getActivity(),R.layout.sp_location,listprovinsi);
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

    @OnClick(R.id.lyFilter)
    protected void lyFilter(View view) {
        if (status == false) {
            status = true;
        } else {
            status = false;
        }
        optionStatus();
    }

    @OnClick(R.id.btnTerapkan)
    protected void btnTerapkan(View view) {


        loadData();
        status = false;
        optionStatus();
    }
    @OnClick(R.id.btnCancel)
    protected void btnCancel(View view){
        status = false;
        optionStatus();
        loadData();

    }
    private void optionStatus() {
        if (status == true) {
            lyOption.setVisibility(View.VISIBLE);
        } else {
            lyOption.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        if (status == true){
            provinsi = spProvinsi.getSelectedItem().toString();
            kecamatan = spKecamatan.getSelectedItem().toString();
            kabupaten = spKabupaten.getSelectedItem().toString();
            desa = spDesa.getSelectedItem().toString();
            jenis = spJenis.getSelectedItem().toString();
            tvLocation.setText(desa+", "+kecamatan+", "+kabupaten+", "+provinsi);
        }
        else{
            provinsi = null;
            kecamatan = null;
            kabupaten = null;
            desa = null;
            jenis = null;
        }
        Log.d(TAG, "loadData: "+provinsi+kecamatan+kabupaten+desa+jenis);
        Call<ResponseCouple> responseCoupleCall = coupleCatInterface.getListCouple(jenis, provinsi, kabupaten, kecamatan, desa);
        responseCoupleCall.enqueue(new Callback<ResponseCouple>() {
            @Override
            public void onResponse(Call<ResponseCouple> call, Response<ResponseCouple> response) {
                if (response.body().getStatus() == 200) {
                    coupleAdapter = new CoupleAdapter(getContext(), (ArrayList<Couple>) response.body().getData());
                    rvCouple.setAdapter(coupleAdapter);
                    coupleAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus() == 403) {
                    Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCouple> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDB() {
        Call<ResponsePasangan> responseLocationCall = coupleCatInterface.updateLocation(
                map.get(sessionManager.KEY_PENGGUNA_ID),
                provinsi,
                kabupaten,
                kecamatan,
                desa,
                latitude,
                longitude);

        responseLocationCall.enqueue(new Callback<ResponsePasangan>() {
            @Override
            public void onResponse(Call<ResponsePasangan> call, Response<ResponsePasangan> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Berhasil Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePasangan> call, Throwable t) {

            }
        });
    }

    private void loadCurrentLocation() {
        LocationManager locationManager;
        String svcName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getContext().getSystemService(svcName);
        String provider = LocationManager.GPS_PROVIDER;
        Location l = locationManager.getLastKnownLocation(provider);
        updateLocation(l);
    }

    private void updateLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            provinsi = addresses.get(0).getAdminArea();
            kabupaten = addresses.get(0).getSubAdminArea();
            kecamatan = addresses.get(0).getLocality();
            desa = addresses.get(0).getSubLocality();
            latitude = String.valueOf(addresses.get(0).getLatitude());
            longitude = String.valueOf(addresses.get(0).getLongitude());
            String lokasi = "Lokal : " + addresses.get(0).getLocality() + "\n"
                    + "Sublocal : " + addresses.get(0).getSubLocality() + "\n"
                    + "Country Name : " + addresses.get(0).getCountryName() + "\n"
                    + "Troughfare : " + addresses.get(0).getThoroughfare() + "\n"
                    + "subTroughfare : " + addresses.get(0).getSubThoroughfare() + "\n"
                    + "latitude : " + addresses.get(0).getLatitude() + "\n"
                    + "longitude : " + addresses.get(0).getLongitude() + "\n"
                    + "admin area : " + addresses.get(0).getAdminArea() + "\n"
                    + "sub admin area : " + addresses.get(0).getSubAdminArea() + "\n";
            tvLocation.setText("" + addresses.get(0).getSubLocality() + "," + addresses.get(0).getLocality() + "," + addresses.get(0).getSubAdminArea() + "," + addresses.get(0).getAdminArea());


            if (addresses.get(0).getLocality() != null) {

            }

        } catch (Exception e) {

        }
    }


    @OnClick(R.id.lyBtnCircle)
    public void lyBtnCircle(View view) {
        Intent gotoaddcouple = new Intent(getActivity(), AddCoupleActivity.class);
        startActivity(gotoaddcouple);
    }
}

package com.azhar.couplecat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Activity.AddCoupleActivity;
import com.azhar.couplecat.Activity.MainActivity;
import com.azhar.couplecat.Model.ResponsePasangan;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    LocationManager locationManager;
    String kecamatan;
    String kabupaten;
    String provinsi;
    String desa;
    String latitude;
    String longitude;
    String TAG = "Kambing";
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
        loadCurrentLocation();
        updateDB();
        loadData();
    }

    private void loadData() {

    }

    private void updateDB() {

        Log.d(TAG, "updateDB: "+provinsi);
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
                if (response.isSuccessful()){
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
        locationManager = (LocationManager)getContext().getSystemService(svcName);
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
            String lokasi = "Lokal : "+addresses.get(0).getLocality()+"\n"
                    +"Sublocal : "+addresses.get(0).getSubLocality()+"\n"
                    +"Country Name : "+addresses.get(0).getCountryName()+"\n"
                    +"Troughfare : "+addresses.get(0).getThoroughfare()+"\n"
                    +"subTroughfare : "+addresses.get(0).getSubThoroughfare()+"\n"
                    +"latitude : "+addresses.get(0).getLatitude()+"\n"
                    +"longitude : "+addresses.get(0).getLongitude()+"\n"
                    +"admin area : "+addresses.get(0).getAdminArea()+"\n"
                    +"sub admin area : "+addresses.get(0).getSubAdminArea()+"\n";
            tvLocation.setText(""+addresses.get(0).getSubLocality()+","+addresses.get(0).getLocality()+","+addresses.get(0).getSubAdminArea()+","+addresses.get(0).getAdminArea());




            if (addresses.get(0).getLocality()!=null){

            }

        }catch(Exception e)
        {

        }
    }



    @OnClick(R.id.lyBtnCircle)
    public void lyBtnCircle(View view) {
        Intent gotoaddcouple = new Intent(getActivity(), AddCoupleActivity.class);
        startActivity(gotoaddcouple);
    }
}

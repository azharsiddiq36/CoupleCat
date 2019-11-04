package com.azhar.couplecat.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

import com.azhar.couplecat.Activity.DetailActivity;
import com.azhar.couplecat.Activity.MainActivity;
import com.azhar.couplecat.Adapter.TheCatApiAdapter;
import com.azhar.couplecat.Model.Information;
import com.azhar.couplecat.Model.ResponseInformation;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Rest.TheCatApiInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class InformationFragment extends Fragment {
    String TAG = "FIX";
    @BindView(R.id.rvInformation)
    RecyclerView rvInformation;
    SessionManager sessionManager;
    HashMap<String,String> map;
    CoupleCatInterface coupleCatInterface;
    TheCatApiInterface theCatApiInterface;
    ProgressDialog progressDialog;
    TheCatApiAdapter theCatApiAdapter;
    ArrayList theCatApi = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar =
                ((MainActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_overlay_splash));
        actionBar.setTitle("Information");
//        actionBar.setBackgroundDrawable(new ColorDrawable(R.drawable.bg_overlay_splash));

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());
        map = sessionManager.getDetailsLoggin();
        progressDialog = new ProgressDialog(getContext());
        coupleCatInterface = CombineApi.getApiService();
        theCatApiInterface = CombineApi.getTheCatApi();
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvInformation.setLayoutManager(layoutManager);
        progressDialog.setMessage("Sedang Memuat Data ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        Call<ArrayList<Information>> data = theCatApiInterface.getListTheCatApi();
        data.enqueue(new Callback<ArrayList<Information>>() {
            @Override
            public void onResponse(Call<ArrayList<Information>> call, Response<ArrayList<Information>> response) {
                progressDialog.hide();
                Log.d(TAG, "onResponse: "+response.body().get(1).getName());
//                Log.d(TAG, "onResponse: "+response.raw());
                theCatApi = (ArrayList)response.body();
//                theCatApiAdapter = response.body().getAltNames();
                theCatApiAdapter = new TheCatApiAdapter(getActivity(),theCatApi);
                //TheCatApiAdapter.getFilter().filter("");
                rvInformation.setAdapter(theCatApiAdapter);
                theCatApiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Information>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
            }
        });


    }
//    @OnClick(R.id.lyInformation)
//    public void lyInformation(View view){
//        Intent gotodetail = new Intent(getActivity(),DetailActivity.class);
//        startActivity(gotodetail);
//    }
    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem !=null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView!=null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(TheCatApiAdapter!=null){
                        TheCatApiAdapter.getFilter().filter(newText);
                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu,menuInflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
    */
}
